package br.com.autotrac.testnanoclient

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import br.com.autotrac.testnanoclient.consts.ActionValues
import br.com.autotrac.testnanoclient.consts.ApiConstants
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.consts.ResponseObjectReference
import br.com.autotrac.testnanoclient.handlers.EndpointsLists
import br.com.autotrac.testnanoclient.handlers.ParseOnMessage
import br.com.autotrac.testnanoclient.handlers.ParseResult
import br.com.autotrac.testnanoclient.logger.AppLogger
import br.com.autotrac.testnanoclient.models.IntegrationMessage
import br.com.autotrac.testnanoclient.requestObjects.ChunkObject
import br.com.autotrac.testnanoclient.requestObjects.RequestObject
import br.com.autotrac.testnanoclient.requestObjects.SendObject
import br.com.autotrac.testnanoclient.security.SSLSetup
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

/**
 * Cliente WebSocket baseado em OKHttpClient e Retrofit.
 * @author Melina Minaya.
 */

object NanoWebsocketClient{
     private const val serverUrl = ApiConstants.BASE_URL
    private var webSocketClient: WebSocket? = null
    val gson: Gson = Gson()
    const val TAG = "NanoWebsocket"
    private const val MAX_RETRIES = 5
    private const val RETRY_DELAY_MS: Long = 5000 // 1 second
    private val client = OkHttpClient.Builder()
    private const val packageName = "br.com.autotrac.testnanoclient"

    private val webSocketConnectionSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    var currentMsgCode: String? = null

    var connectionDisposable: Disposable? = null
    var responseListener:String = ""

    /**
     * Método para conexão do WebSocket Cliente ao Servidor.
     */
    fun connect() {
        CoroutineScope(Dispatchers.IO).launch {
            val authorizationToken = requestAuthorizationToken()
            if (authorizationToken != null) {
                val request = Request.Builder()
                    .url(serverUrl)
                    //.addHeader("Authorization", "Bearer $jwtToken") // Add the JWT token as an Authorization header
                    .addHeader(
                        "authorization",
                        authorizationToken
                    ) // Add the JWT token as an Authorization header
                    .addHeader("user-agent", System.getProperty("http.agent")!!)
                    .addHeader("package-name", packageName)
                    .build()
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf<TrustManager>(SSLSetup.trustAllCertificates),
                    null)

                webSocketClient = client
                    .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
                    .sslSocketFactory(sslContext.socketFactory, SSLSetup.trustAllCertificates)
                    .hostnameVerifier { _, _ -> true }
                    .build()
                    .newWebSocket(request, object : WebSocketListener() {
                        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                            println("WebSocket connected")
                            AppLogger.log("WebSocket connected")
                            webSocketConnectionSubject.onNext(true)
                        }

                        override fun onMessage(webSocket: WebSocket, text: String) {
                            val parseMessage = ParseOnMessage()
                            val response = parseMessage.parseMessage(text)
                            if (response == ParseResult.Ok) {
                               sendMessage(NanoHTTPD.Response.Status.OK.toString())
                            }else{
                                sendMessage(NanoHTTPD.Response.Status.INTERNAL_ERROR.toString())
                            }
                            responseListener = text
                        }

                        override fun onFailure(
                            webSocket: WebSocket,
                            t: Throwable,
                            response: okhttp3.Response?,
                        ) {
                            Log.d(TAG, "WebSocket connection failed: ${t.message}")
                            AppLogger.log("WebSocket connection failed: ${t.message}")
                            webSocketConnectionSubject.onNext(false)
                            // Retry connection on failure
    //                    retryConnection()
                            responseListener = t.toString()
                        }

                        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                            Log.d(TAG, "WebSocket connection closed: $reason")
                            AppLogger.log( "WebSocket connection closed: $reason")
                            webSocketConnectionSubject.onNext(false)
                            connectionDisposable!!.dispose()
                            responseListener = code.toString()
                        }
                    })
            }
        }
    }

    /**
     * Método para tentativa de reconexão ao Servidor.
     */
    fun retryConnection() {
        var retries = 0

        while (retries < MAX_RETRIES) {
            try {
                Thread.sleep(RETRY_DELAY_MS)
//                connect()
                sendMessageFromClient()
                return
                // Connection successful, exit the retry loop
            } catch (e: IOException) {
                Log.d(TAG, "Connection retry failed: " + e.message)
                AppLogger.log("Connection retry failed: " + e.message)
            } catch (e: InterruptedException) {
                Log.d(TAG, "Connection retry failed: " + e.message)
                AppLogger.log("Connection retry failed: " + e.message)
            }
            retries++
        }
        Log.d(TAG, "Connection retries exhausted")
        AppLogger.log("Connection retries exhausted")

    }

    /**
     * Envia a lista de endpoint de notificações que desejam ser escutadas.
     *
     * [ActionValues.MESSAGE_STATUS] : retorna [ActionValues.MessageStatusValues]
     *
     * [ActionValues.COMMUNICATION_MODE_CHANGED] : [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesNetworkTypes]
     *
     * [ActionValues.FILE_OPERATION_STATUS] : [ActionValues.ValuesFileOperationStatusParam1]
     *
     * [ActionValues.IGNITION_STATUS] : [ActionValues.ValuesIgnitionStatusParam1]
     *
     * [ActionValues.NETWORK_CONNECTION_STATUS] : [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesNetworkTypes] e
     * [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesConnectionStates]
     *
     * [ActionValues.SYSTEM_RESOURCE_REQ_STATUS] : [ActionValues.ValuesSysResourceReqParam1]
     * e [ActionValues.ValuesSysResourceStatusParam2]
     *
     * [ActionValues.FORM_RECEIVED] e [ActionValues.FORM_DELETED] : [br.com.autotrac.testnanoclient.models.IntegrationForm.code]
     */
    fun sendMessageFromClient() {
        val notificationSubscription =
            SendObject(
                ResponseObjectReference.NOTIFICATION,
                listOf(
                    ActionValues.MESSAGE_STATUS, ActionValues.BAPTISM_STATUS,
                    ActionValues.COMMUNICATION_MODE_CHANGED, ActionValues.DATE_TIME_CHANGED,
                    ActionValues.FILE_OPERATION_STATUS,  ActionValues.IGNITION_STATUS,
                    ActionValues.MCT_M0_M9_PARAMS_UPDATED, ActionValues.NETWORK_CONNECTION_STATUS,
                    ActionValues.READY_TO_UPDATE_SOFTWARE, ActionValues.SATELLITE_SIGNAL_CHANGED,
                    ActionValues.SIGN_ON_STATUS, ActionValues.SYSTEM_RESOURCE_REQ_STATUS,
                    ActionValues.FORM_DELETED, ActionValues.FORM_RECEIVED
                )
            )

        val objectJson = gson.toJson(notificationSubscription)
        webSocketClient!!.send(objectJson)
        Log.d(TAG, "Notification List: $objectJson")
        AppLogger.log( "Notification List: $objectJson")
//        sendMessageRequest("messageList", null, null, null)
        // Example: Send a binary message
//        val binaryMessage = "Binary data".encodeUtf8()
//        webSocketClient!!.send(binaryMessage)
    }

    fun getInstance(): NanoWebsocketClient {
        return this
    }

    fun sendMessage(message: String) {
        webSocketClient!!.send(message)
    }

    suspend fun sendDbMessage(message: IntegrationMessage, fileUri: Uri?, context: Context) {
        val maxChunkSize = 8192
//        val maxChunkSize = 1024
        val messageJson = gson.toJson(message, IntegrationMessage::class.java)
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
                        val buffer = ByteArray(maxChunkSize)
                        val bytesRead = inputStream!!.read(buffer)

                        if (bytesRead == -1) {
                            // End of file reached, break the loop
                            break
                        }

                        val chunkData = Base64.encodeToString(buffer.copyOf(bytesRead), Base64.NO_WRAP)

                        val chunkObject = ChunkObject(
                            message = messageJson,
                            totalChunks = totalChunks,
                            chunkNumber = chunkNumber,
                            data = chunkData
                        )

                        val myObject = SendObject(ApiEndpoints.SEND_FILE_MESSAGE, chunkObject)
                        val response = gson.toJson(myObject)
                        if (webSocketClient != null) {
                            webSocketClient!!.send(response)
                            Log.d(TAG, "Sending chunk $chunkNumber of $totalChunks: $response")
                        }

                        chunkNumber++


                    }
                    val endMarkerObject = ChunkObject(
                        message= messageJson,
                        totalChunks = totalChunks,
                        chunkNumber = totalChunks,
                        data = ""
                    )

                    val myObject = SendObject(ApiEndpoints.SEND_FILE_MESSAGE, endMarkerObject)
                    val response = gson.toJson(myObject)
                    if (webSocketClient != null) {
                        webSocketClient!!.send(response)
                        Log.d(TAG, "Sending chunk $chunkNumber of $totalChunks: $response")
                    }
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
                val response = gson.toJson(myObject)
                if (webSocketClient != null) {
                    webSocketClient!!.send(response)
                }
            }
        }catch (e:Exception){
            Log.e(TAG, "Exception at sending chunks")
            AppLogger.log( "Exception at sending chunks: ${e.printStackTrace()}")
            e.printStackTrace()
//            connect()
//            sendDbMessage(message, fileUri, context)
        }
    }

    fun sendMessageRequest(param: String, param1: Any?, param2: Any?, param3: Any?, param4: Any?) {
        val listOfParams = EndpointsLists.parametersList
        val listOfRequests = EndpointsLists.requestsList
        try {
            val requestObject = when (param) {
                in listOfParams -> RequestObject(param1, param2, param3, param4)
                in listOfRequests -> RequestObject(param1, param2, param3, param4)
                else -> return // Return early if the parameter is not valid
            }
            //TODO:Remove this conversion
//            val response = gson.toJson(requestObject)

            val sendObject = SendObject(param, requestObject)
            val objectRequestJson = gson.toJson(sendObject)

            webSocketClient?.send(objectRequestJson)

            Log.d(TAG, "request $param: $objectRequestJson")
            AppLogger.log(  "request $param: $objectRequestJson")
        } catch (e: Exception) {
            Log.d(TAG, "Exception on $param: $e")
            AppLogger.log( "Exception on $param: $e")
        }
    }


    fun disconnect() {
        webSocketClient?.close(1000, "Client disconnecting")
    }

    /**
     * Requisição de autenticação do WebSocket e reusado no HTTP.
     * Insere package name da sua aplicação para ser reconhecido pelo Servidor.
     * Insere linguagem de prefência para respostas do Servidor.
     */
    fun requestAuthorizationToken(): String? {
        val preferredLanguage = "pt-BR"

        try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(SSLSetup.trustAllCertificates),
                null)

            val client = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS) // Increase the timeout duration as needed
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
                .sslSocketFactory(sslContext.socketFactory, SSLSetup.trustAllCertificates)
                .hostnameVerifier { _, _ -> true }
                .build()

            val request = Request.Builder()
            .url("$serverUrl/${ApiConstants.AUTH_ENDPOINT}") // Replace with your server's endpoint URL
            .addHeader("user-agent", System.getProperty("http.agent")!!)
            .addHeader("package-name", packageName)
            .addHeader("Accept-Language", preferredLanguage)
            .addHeader("password", "KkpGEx0kaDJGMxlbNQIGGyYIFRY3WAwKFh8ZEDg/")
            .build()

            var response: Response? = null
            var retryCount = 0
            while (response == null || !response.isSuccessful) {
                try {
                    response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        // Parse the response body to extract the authorization token
//                        val tokenReceived = response.body?.string()
//                        // val treatTokenReceived = tokenReceived!!.replace("\n", "")
//                        Log.d(TAG, "Received token: $tokenReceived")
//                        return tokenReceived
                        // Check if the content type is JSON
                        val contentType = response.header("Content-Type")
                        return if (contentType != null && contentType.contains("application/json")) {
                            // Parse the JSON response body to extract the authorization token and message
                            val responseBody = response.body?.string()
                            val jsonResponse = JSONObject(responseBody)
                            val tokenReceived = jsonResponse.optString("token")
                    //                            val messageReceived = jsonResponse.optString("message")

                            Log.d(TAG, "Received token: $tokenReceived")
                            AppLogger.log("Received token")
                    //                            Log.d(TAG, "Received message: $messageReceived")
                            tokenReceived
                        } else {
                            null
                        }

                    } else {
                        return null
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    return null

                } catch (e: InterruptedException) {
                    // Interrupted while waiting, return null or throw an exception
                    return null
                } finally {
                    response?.body?.close()
                }
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    fun isWebSocketConnected(): Boolean? {
        return webSocketConnectionSubject.value
    }
    /**
     * Método fixo de requesição de mensagens a cada período de tempo.
     *
     * REQ_MESSAGE_COUNT: Contabiliza a quantidade de mensagens no banco de dados de acordo com o filtro especificado.
     * Este método pode ser usado, por exemplo, para contabilizar a quantidade de mensagens a enviar.
     * @param1:Boolean: True para mensagens de saída, False para mensagens de entrada (caixa de entrada).
     * @param2:Int: número do status a ser filtrado.
     */
    fun startSendingRequests() {
        sendMessageRequest(ApiEndpoints.REQ_MESSAGE_COUNT, false, 3, null, null)
    }
}


