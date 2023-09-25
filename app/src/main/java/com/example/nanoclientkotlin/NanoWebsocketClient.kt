package com.example.nanoclientkotlin

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.example.nanoclientkotlin.consts.ActionValues
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.ChunkObject
import com.example.nanoclientkotlin.dataRemote.IntegrationMessage
import com.example.nanoclientkotlin.dataRemote.RequestObject
import com.example.nanoclientkotlin.dataRemote.SendObject
import com.example.nanoclientkotlin.handlers.ParseOnMessage
import com.example.nanoclientkotlin.handlers.ParseResult
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

/**
 * Cliente WebSocket baseado em OKHttpClient e Retrofit.
 * @author Melina Minaya.
 */

object NanoWebsocketClient{
    private const val serverUrl =
        "http://127.0.0.1:8081" // Replace with your NanoHTTPD WebSocket server URL
//     private const val serverUrl = "https://127.0.0.1:8081"
    private var webSocketClient: WebSocket? = null
    val gson: Gson = Gson()
    const val TAG = "NanoWebsocket"
    private const val MAX_RETRIES = 5
    private const val RETRY_DELAY_MS: Long = 5000 // 1 second
    private val client = OkHttpClient()
    const val packageName = "com.example.nanoclientkotlin"

    // Declare the observable subject
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

                webSocketClient = client.newWebSocket(request, object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                        println("WebSocket connected")
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
                        webSocketConnectionSubject.onNext(false)
                        // Retry connection on failure
//                    retryConnection()
                        responseListener = t.toString()
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        Log.d(TAG, "WebSocket connection closed: $reason")
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
            } catch (e: InterruptedException) {
                Log.d(TAG, "Connection retry failed: " + e.message)

            }
            retries++
        }
        Log.d(TAG, "Connection retries exhausted")

    }

    /**
     * Envia a lista de endpoint de notificações que desejam ser escutadas.
     * TODO: FORM_RECEIVED
     */
    fun sendMessageFromClient() {
        //val textMessage = "Hello, server!"
        val notificationSubscription =
            SendObject(
                ConstsCommSvc.NOTIFICATION,
                listOf(
                    ActionValues.MESSAGE_STATUS, ActionValues.BAPTISM_STATUS,
                    ActionValues.COMMUNICATION_MODE_CHANGED, ActionValues.DATE_TIME_CHANGED,
                    ActionValues.FILE_OPERATION_STATUS, ActionValues.FORM_DELETED,
                    ActionValues.FORM_RECEIVED,
                    ActionValues.IGNITION_STATUS, ActionValues.MCT_M0_M9_PARAMS_UPDATED,
                    ActionValues.NETWORK_CONNECTION_STATUS, ActionValues.READY_TO_UPDATE_SOFTWARE,
                    ActionValues.SATELLITE_SIGNAL_CHANGED, ActionValues.SIGN_ON_STATUS,
                    ActionValues.SYSTEM_RESOURCE_REQ_STATUS, ActionValues.UPDATE_SERVER_PARAMETER
                )
            )

        val objectJson = gson.toJson(notificationSubscription)
        webSocketClient!!.send(objectJson)
        Log.d(TAG, "Notification List: $objectJson")
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
                        val response = gson.toJson(myObject)
                        if (webSocketClient != null) {
                            webSocketClient!!.send(response)
                        }

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
                    val response = gson.toJson(myObject)
                    if (webSocketClient != null) {
                        webSocketClient!!.send(response)
                    }
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
                val response = gson.toJson(myObject)
                if (webSocketClient != null) {
                    webSocketClient!!.send(response)
                }
            }
        }catch (e:Exception){
            Log.e(TAG, "Exception at sending chunks")
            e.printStackTrace()
//            connect()
//            sendDbMessage(message, fileUri, context)
        }
    }

    fun sendMessageRequest(param: String, param1: Any?, param2: Any?, param3: Any?, param4: Any?) {
        val listOfParams = ConstsCommSvc.parametersList
        val listOfRequests = ConstsCommSvc.requestsList
        try {
            val requestObject = when (param) {
                in listOfParams -> RequestObject(param1, param2, param3, param4)
                in listOfRequests -> RequestObject(param1, param2, param3, param4)
                else -> return // Return early if the parameter is not valid
            }

            val response = gson.toJson(requestObject)

            val sendObject = SendObject(param, response)
            val objectRequestJson = gson.toJson(sendObject)

            webSocketClient?.send(objectRequestJson)

            Log.d(TAG, "request $param: $objectRequestJson")
        } catch (e: Exception) {
            Log.d(TAG, "Exception on $param: $e")
        }
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
        val coroutineScope = CoroutineScope(Dispatchers.Default)

        coroutineScope.launch {
            while (true) {
                // Send your request on the WebSocket
                sendMessageRequest(ConstsCommSvc.REQ_MESSAGE_COUNT, false, 3, null, null)

                delay(10000) // Delay for 5 seconds
            }
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
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS) // Increase the timeout duration as needed
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        //Add en-US if you would like the server to respond in English
        val preferredLanguage = "pt-BR"
        val request = Request.Builder()
            .url("$serverUrl/auth") // Replace with your server's endpoint URL
            .addHeader("user-agent", System.getProperty("http.agent")!!)
            .addHeader("package-name", packageName)
            .addHeader("Accept-Language", preferredLanguage)
            .addHeader("password", "Autotrac@2023")
            .build()
        var response: Response? = null
        var retryCount = 0
        while (response == null || !response.isSuccessful) {
            try {
                response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    // Parse the response body to extract the authorization token
                    val tokenReceived = response.body?.string()
                    // val treatTokenReceived = tokenReceived!!.replace("\n", "")
                    Log.d(TAG, "Received token: $tokenReceived")
                    return tokenReceived
                    //parseAuthorizationToken(responseBody)
                } else {
                    // Handle error response
                    // Retry connection if retry count is within the limit
                    if (retryCount < MAX_RETRIES) {
                        retryCount++
                        Thread.sleep(RETRY_DELAY_MS)
                    } else {
                        // Retry limit exceeded, return null or throw an exception
                        return null
                    }
                }
            } catch (e: IOException) {
                if (retryCount < MAX_RETRIES) {
                    retryCount++
                    e.printStackTrace()
                    Thread.sleep(RETRY_DELAY_MS)
                } else {
                    // Retry limit exceeded, return null or throw an exception
                    return null
                }

            } catch (e: InterruptedException) {
                // Interrupted while waiting, return null or throw an exception
                return null
            } finally {
                // Close the response body if it is not null
                response?.body?.close()
            }
        }
        return null
    }
    fun isWebSocketConnected(): Boolean {
        return webSocketClient?.send("Ping") ?: false
    }

}


