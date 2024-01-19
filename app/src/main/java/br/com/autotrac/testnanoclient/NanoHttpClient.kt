package br.com.autotrac.testnanoclient

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import br.com.autotrac.testnanoclient.consts.ApiConstants
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.handlers.EndpointsLists
import br.com.autotrac.testnanoclient.handlers.ParseOnMessage
import br.com.autotrac.testnanoclient.handlers.ParseResult
import br.com.autotrac.testnanoclient.logger.AppLogger
import br.com.autotrac.testnanoclient.models.IntegrationMessage
import br.com.autotrac.testnanoclient.requestObjects.ChunkObject
import br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse
import br.com.autotrac.testnanoclient.requestObjects.RequestObject
import br.com.autotrac.testnanoclient.requestObjects.SendObject
import br.com.autotrac.testnanoclient.retrofit.ApiService
import br.com.autotrac.testnanoclient.security.SSLSetup
import br.com.autotrac.testnanoclient.security.SSLSetup.trustAllCertificates
import com.google.gson.Gson
import com.google.gson.JsonParser
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import kotlin.coroutines.resume

/**
 * Cliente de integração para o servidor NanoHTTPD.
 * Utiliza OkHttpClient e Retrofit.
 * @author Melina Minaya
 */
object NanoHttpClient {
    private val gson = Gson()
    private const val serverUrl = ApiConstants.BASE_URL
    val client = OkHttpClient.Builder()
    const val TAG = "NanoHttpClient"
    var webSocket:WebSocket? = null
    val maxChunkSize = 8192

    /**
     * Método para envio de todas as requisições, menos a de mensagens longas [ApiEndpoints.SEND_FILE_MESSAGE].
     * @see sendFileChunksHttp
     *
     * O [br.com.autotrac.testnanoclient.requestObjects.RequestObject] é distribuído em queries na requisição HTTP.
     */
    suspend fun sendGetRequestHttp( endpoint: String, objectGet: RequestObject): String {
        val token = NanoWebsocketClient.requestAuthorizationToken()

        if (token.isNullOrBlank()) {
            // Token is not available, don't proceed with the request
            return ""
        }
        var responseBody: ResponseBody?
        return withContext(Dispatchers.IO) {
            try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf<TrustManager>(SSLSetup.trustAllCertificates), null)

                // Construct the URL for the additional request
                val additionalRequestUrl = "$serverUrl/$endpoint/?token=$token"
                val customHttpClient = client
                    .readTimeout(30, TimeUnit.SECONDS) // Set your custom timeout here
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .connectionSpecs(
                        listOf(
                            ConnectionSpec.COMPATIBLE_TLS,
                            ConnectionSpec.CLEARTEXT,
                            ConnectionSpec.MODERN_TLS
                        )
                    )
                    .sslSocketFactory(sslContext.socketFactory, trustAllCertificates)
                    .hostnameVerifier { _, _ -> true } // Bypass hostname verification for self-signed certificates
                    .build()

                // Create a new request using the additional URL
                val additionalRequest = Retrofit.Builder()
                    .baseUrl(additionalRequestUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(customHttpClient)
                    .build()

                val service = additionalRequest.create(ApiService::class.java)
                val headers = "$token"

                val listOfRequests = EndpointsLists.requestsList + EndpointsLists.parametersList +
                        listOf<String>(ApiEndpoints.SEND_MESSAGE, ApiEndpoints.SEND_FILE_MESSAGE)

                val response: Call<ResponseBody> = when (endpoint) {

                    in listOfRequests -> {
                        service.callRequest(
                            headers,
                            endpoint,
                            objectGet.param1,
                            objectGet.param2,
                            objectGet.param3,
                            objectGet.param4,

                            )
                    }

                    else -> {
                        Log.e(TAG, "Endpoint not supported: $endpoint")
                        AppLogger.log("Endpoint not supported: $endpoint")
                        return@withContext ""
                    }
                }

                // Execute the additional request

                responseBody = response.execute().body()
                if (responseBody != null) {
                    val responseData = responseBody!!.string()
                    println("Response from HTTP $endpoint: $responseData")
                    responseData
                } else {
                    println("Response body is null for $endpoint")
                    AppLogger.log("Response body is null for $endpoint")
                    ""
                }
            }catch (e: Exception) {
                // Handle exceptions here
                Log.e(TAG, "Request error", e)
                AppLogger.log("Request error: ${e.message}")
                ""
            }
        }
    }

    /**
     * Quando enviar mensagens longas será necessário utilizar um método com inicialização
     * de um websocket somente para enviar os dados. Após recebimento das mensagens, o websocket
     * poderá ser fechado.
     */
    suspend fun sendFileChunksHttp(
        endpoint: String,
        message: IntegrationMessage,
        maxChunkSize: Int,
        fileUri: Uri,
        context: Context
    ): String {
        return suspendCancellableCoroutine { continuation ->
            val authorizationToken = NanoWebsocketClient.requestAuthorizationToken()
            if (authorizationToken != null) {
                val request = Request.Builder()
                    .url(serverUrl)
                    //.addHeader("Authorization", "Bearer $jwtToken") // Add the JWT token as an Authorization header
                    .addHeader(
                        "authorization",
                        authorizationToken
                    ) // Add the JWT token as an Authorization header
                    .addHeader("user-agent", System.getProperty("http.agent")!!)
                    .build()


                var response: String = ""
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf<TrustManager>(SSLSetup.trustAllCertificates),
                    null)

                webSocket = client
                    .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
                    .sslSocketFactory(sslContext.socketFactory, SSLSetup.trustAllCertificates)
                    .hostnameVerifier { _, _ -> true }
                    .build()
                    .newWebSocket(request, object : WebSocketListener() {

                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        // WebSocket connection is open
                        sendFileMessage(message, fileUri, context)
                    }

                    override fun onMessage(webSocket: WebSocket, text: String) {
                        // Handle text message received from the server
                        val jsonElement = JsonParser.parseString(text)
                        val notification =
                            gson.fromJson(text, ReceivedRequestResponse::class.java)
                        val param1 = notification.param1 ?: return // Exit if param1 is null
                        if (param1 == ApiEndpoints.SEND_FILE_MESSAGE) {
                            val myObject =
                                SendObject(ApiEndpoints.SEND_FILE_MESSAGE, notification)
                            response = gson.toJson(myObject)
//                            continuation.resumeWith(Result.success(response))
                            webSocket.close(1000, "File Sent: Client Disconnecting.")
                            continuation.resume(response)

                        }else {
                            val parseMessage = ParseOnMessage()
                            val response = parseMessage.parseMessage(text)
                            if (response == ParseResult.Ok) {
                                webSocket.send(NanoHTTPD.Response.Status.OK.toString())
                            } else {
                                webSocket.send(NanoHTTPD.Response.Status.INTERNAL_ERROR.toString())
                            }
                        }

                    }


                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        // WebSocket is closed
                        if (!continuation.isCancelled) {
                            // Only resume if the coroutine is not already cancelled
                            continuation.resume("WebSocket closed")
                        }
                    }

                    override fun onFailure(
                        webSocket: WebSocket,
                        t: Throwable,
                        response: Response?
                    ) {
                        // Handle failure
//                        continuation.resumeWith(Result.failure(t))
                    }
                })
            }
        }
    }

    /**
     * Método relativo ao particionamento em chunks idêntico ao do Websocket.
     * TODO: Reduzir o método do websocket para ser reutilizado.
     * @see NanoWebsocketClient.sendDbMessage
     */
    private fun sendFileMessage(message: IntegrationMessage, fileUri: Uri?, context: Context) {
        val messageJson = NanoWebsocketClient.gson.toJson(message, IntegrationMessage::class.java)
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        try {
            if(fileUri != null){
                coroutineScope.launch{
                    val inputStream = context.contentResolver.openInputStream(fileUri)
                    val fileSize = inputStream?.available() ?: 0 // Get the total file size
                    val totalChunks = (fileSize + maxChunkSize - 1) / maxChunkSize // Calculate total chunks

                    val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    var chunkNumber = 0

                    while (true) {
                        // Read the file data into a ByteArray buffer
                        val buffer = ByteArray(maxChunkSize)
                        val bytesRead = inputStream!!.read(buffer)

                        if (bytesRead == -1) {
                            // End of file reached, break the loop
                            break
                        }

                        // Convert the chunk data to Base64 for transmission
                        val chunkData = Base64.encodeToString(buffer.copyOf(bytesRead), Base64.NO_WRAP)

                        // Create the ChunkObject for the current chunk
                        val chunkObject = ChunkObject(
                            message = messageJson,
                            totalChunks = totalChunks, // Placeholder value, will be updated later
                            chunkNumber = chunkNumber,
                            data = chunkData
                        )

                        // Send the chunk over the WebSocket
                        val myObject = SendObject(ApiEndpoints.SEND_FILE_MESSAGE, chunkObject)
                        val response = NanoWebsocketClient.gson.toJson(myObject)
                        webSocket?.send(response)

                        // Increment the chunk number
                        chunkNumber++


                    }
                    // Signal the end of the file transmission with a special end marker
                    val endMarkerObject = ChunkObject(
                        message= messageJson,
                        totalChunks = totalChunks, // Set the totalChunks to the final chunk number (including the end marker)
                        chunkNumber = totalChunks, // Use a special value to indicate the end marker
                        data = "" // Empty data for the end marker
                    )

                    val myObject = SendObject(ApiEndpoints.SEND_FILE_MESSAGE, endMarkerObject)
                    val response = NanoWebsocketClient.gson.toJson(myObject)
                    webSocket?.send(response)
                    // Close the input stream and clean up
                    reader.close()
                    inputStream.close()
                }
            }else{
                val messageToSend = ChunkObject(
                    message= messageJson,
                    totalChunks = 0,
                    chunkNumber = 0,
                    data = ""
                )

                val myObject = SendObject(ApiEndpoints.SEND_MESSAGE, messageToSend)
                val response = NanoWebsocketClient.gson.toJson(myObject)
                webSocket?.send(response)
            }
        }catch (e:Exception){
            Log.e("OKWebsocket", "Exception at sending chunks")
            AppLogger.log("Exception at sending chunks")
            e.printStackTrace()
        }
    }
}

