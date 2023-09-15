package com.example.nanoclientkotlin

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.ChunkObject
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.dataRemote.ReceivedRequestResponse
import com.example.nanoclientkotlin.dataRemote.RequestObject
import com.example.nanoclientkotlin.dataRemote.SendObject
import com.example.nanoclientkotlin.di.ApiService
import com.example.nanoclientkotlin.handlers.ParseOnMessage
import com.example.nanoclientkotlin.handlers.ParseResult
import com.google.gson.Gson
import com.google.gson.JsonParser
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
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
import kotlin.coroutines.resume

/**
 * Cliente de integração para o servidor NanoHTTPD.
 * Utiliza OkHttpClient e Retrofit.
 * @author Melina Minaya
 */
object NanoHttpClient {
    private val gson = Gson()
    private const val serverUrl =
        "http://127.0.0.1:8081" // Replace with your NanoHTTPD WebSocket server URL
    val client = OkHttpClient()
    const val TAG = "NanoHttpClient"
    var webSocket:WebSocket? = null
    val maxChunkSize = 8192



    /**
     * Método para envio de todas as requisições, menos a de mensagens longas [ConstsCommSvc.SEND_FILE_MESSAGE].
     * @see sendFileChunksHttp
     */
    suspend fun sendGetRequestHttp( endpoint: String, objectGet: RequestObject): String {
        // ... existing code for sendGetRequest ...
        var token: String? = null
        var responseBody: ResponseBody? = null
        return withContext(Dispatchers.IO) {
            while (token.isNullOrBlank()) {
                token = NanoWebsocketClient.requestAuthorizationToken()
                if (token.isNullOrBlank()) {
                    delay(100) // Wait for 1 second before retrying
                    Log.d("NanoHttpClient", "Token is null ")
                }
            }

            // Construct the URL for the additional request
            val additionalRequestUrl = "$serverUrl/$endpoint/?token=$token"
            val customHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS) // Set your custom timeout here
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

            // Create a new request using the additional URL
            val additionalRequest = Retrofit.Builder()
                .baseUrl(additionalRequestUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(customHttpClient)
                .build()

            val service = additionalRequest.create(ApiService::class.java)
            val headers = "$token"

            val listOfRequests = ConstsCommSvc.requestsList + ConstsCommSvc.parametersList +
                    listOf<String>(ConstsCommSvc.SEND_MESSAGE, ConstsCommSvc.SEND_FILE_MESSAGE)

            val response:Call<ResponseBody> = when (endpoint) {

                in listOfRequests ->{
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
                    return@withContext ""
                }
            }



            // Execute the additional request

            responseBody = response!!.execute().body()
            if (responseBody != null) {
                val responseData = responseBody!!.string()
                println("Response from $endpoint: $responseData")
                return@withContext responseData
            } else {
                println("Response body is null for $endpoint")
                return@withContext ""
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
        message: DbMessage,
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
                    .build()


                var response: String = ""

                webSocket = client.newWebSocket(request, object : WebSocketListener() {

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
                        if (param1 == ConstsCommSvc.SEND_FILE_MESSAGE) {
                            val myObject =
                                SendObject(ConstsCommSvc.SEND_FILE_MESSAGE, notification)
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
    private fun sendFileMessage(message: DbMessage, fileUri: Uri?, context: Context) {
        val messageJson = NanoWebsocketClient.gson.toJson(message, DbMessage::class.java)
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
                        val myObject = SendObject(ConstsCommSvc.SEND_FILE_MESSAGE, chunkObject)
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

                    val myObject = SendObject(ConstsCommSvc.SEND_FILE_MESSAGE, endMarkerObject)
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

                val myObject = SendObject(ConstsCommSvc.SEND_MESSAGE, messageToSend)
                val response = NanoWebsocketClient.gson.toJson(myObject)
                webSocket?.send(response)
            }
        }catch (e:Exception){
            Log.e("OKWebsocket", "Exception at sending chunks")
            e.printStackTrace()
        }
    }
}

