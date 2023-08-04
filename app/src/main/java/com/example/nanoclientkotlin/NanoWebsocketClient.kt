package com.example.nanoclientkotlin

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import com.example.nanoclientkotlin.consts.ActionValues
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.dataRemote.ParseData
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import fi.iki.elonen.NanoHTTPD
import io.reactivex.rxjava3.disposables.Disposable
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
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


object NanoWebsocketClient{
    private const val serverUrl = "http://127.0.0.1:8081" // Replace with your NanoHTTPD WebSocket server URL
    private var webSocketClient: WebSocket? = null
    private val gson:Gson = Gson()
    const val TAG = "NanoWebsocket"
    private const val MAX_RETRIES = 5
    private const val RETRY_DELAY_MS: Long = 5000 // 1 second
    private val client = OkHttpClient()
    const val packageName = "com.example.nanoclientkotlin"
    // Declare the observable subject
    private val webSocketConnectionSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private var currentMsgCode: String? = null

    var connectionDisposable: Disposable? = null

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(serverUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val webSocketApi: WebSocketApi by lazy {
        retrofit.create(WebSocketApi::class.java)
    }

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
                .build()

//            webSocketClient = webSocketApi.listenForMessages(request.url.toString())
//        }

            webSocketClient = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                    println("WebSocket connected")
                    webSocketConnectionSubject.onNext(true)
                    startSendingRequests()
                }

                override fun onMessage(webSocket: WebSocket, text: String) {

                    try {
                        val jsonElement = JsonParser.parseString(text)
                        if (jsonElement.isJsonObject) {
                            val notification =
                                gson.fromJson(text, ReceivedRequestResponse::class.java)
                            when (notification.param1) {
                                "notification" -> {
                                    Log.d(TAG, "Received notification: $notification")
                                    sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                    //Thread.sleep(500)
                                    //sendMessageList("messageList")
                                    // requestServer("messageList")
                                }

                                "request" -> {
                                    val objectReq = notification.param4
                                    when (notification.param2) {
                                        ConstsCommSvc.REQ_MESSAGE_LIST -> {
                                            val param3Json = notification.param3.toString()
                                            val filterList = ParseData.parseMessageList(param3Json)
                                            Log.d(TAG, "$filterList")
                                            when (filterList.param2) {
                                                true -> ObservableUtil.attachProperty(
                                                    ConstsCommSvc.REQ_MESSAGE_LIST_OUTBOX,
                                                    objectReq
                                                ) // equal fetchOutboxMessages
                                                false -> ObservableUtil.attachProperty(
                                                    ConstsCommSvc.REQ_MESSAGE_LIST_INBOX,
                                                    objectReq
                                                )
                                            }
                                        }

                                        ConstsCommSvc.REQ_MESSAGE_COUNT, ConstsCommSvc.REQ_FORM_LIST,
                                        ConstsCommSvc.REQ_GET_CHECKLIST, ConstsCommSvc.REQ_GET_CURRENT_DATE,
                                        ConstsCommSvc.REQ_GET_MCT_PARAMETERS, ConstsCommSvc.REQ_GET_POSITION_LAST,
                                        ConstsCommSvc.REQ_POSITION_HISTORY_COUNT, ConstsCommSvc.REQ_POSITION_HISTORY_LIST,
                                        ConstsCommSvc.REQ_RESET_DATABASE, ConstsCommSvc.REQ_MESSAGE_DELETE,
                                        ConstsCommSvc.REQ_MESSAGE_SET_AS_READ, ConstsCommSvc.REQ_CONFIG_SERVICE_LOG,
                                        ConstsCommSvc.REQ_FILE_OPERATION
                                        -> {
                                            ObservableUtil.attachProperty(
                                                notification.param2,
                                                objectReq
                                            )
                                        }
                                    }
                                    sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                    Log.d(TAG, "Received ${notification.param2}: $notification")
                                }

                                "parameters" -> {
                                    val objectReq = notification.param3
                                    when (notification.param2) {
                                        in ConstsCommSvc.parametersList.filter { it.startsWith("GET") } -> {
                                            ObservableUtil.attachProperty(
                                                notification.param2!!,
                                                objectReq
                                            )
                                        }

                                        in ConstsCommSvc.parametersList.filter { it.startsWith("SET") } -> {
                                            //TODO: TO BE IMPLEMENTED

                                        }
                                    }
                                    sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                    Log.d(TAG, "Received ${notification.param2}: $notification")
                                }

                                "messageSent" -> {
                                    currentMsgCode = notification.param2
                                    Log.d(TAG, "Received Sent Confirmation: $notification")
                                    sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                }

                                else -> {
                                    // Handle case when notification.param1 is null
                                    Log.d(TAG, "Received message: $text")
                                }
                            }
                        }
                    } catch (e: JsonSyntaxException) {
                        //e.printStackTrace()
                        Log.d(TAG, "Received JsonException: $text")
                    }

                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: okhttp3.Response?
                ) {
                    Log.d(TAG, "WebSocket connection failed: ${t.message}")
                    webSocketConnectionSubject.onNext(false)
                    // Retry connection on failure
//                    retryConnection()
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d(TAG, "WebSocket connection closed: $reason")
                    webSocketConnectionSubject.onNext(false)
                    connectionDisposable!!.dispose()
                }


            })

        }
        }

    }

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

    fun sendMessageFromClient() {
        //val textMessage = "Hello, server!"
        val notificationSubscription =
            SendObject("notification",
                listOf(
                    ActionValues.MESSAGE_STATUS, ActionValues.BAPTISM_STATUS,
                    ActionValues.COMMUNICATION_MODE_CHANGED, ActionValues.DATE_TIME_CHANGED,
                    ActionValues.FILE_OPERATION_STATUS, ActionValues.FORM_DELETED,
                    ActionValues.IGNITION_STATUS, ActionValues.MCT_M0_M9_PARAMS_UPDATED,
                    ActionValues.NETWORK_CONNECTION_STATUS, ActionValues.READY_TO_UPDATE_SOFTWARE,
                    ActionValues.SATELLITE_SIGNAL_CHANGED, ActionValues.SIGN_ON_STATUS,
                    ActionValues.SYSTEM_RESOURCE_REQ_STATUS))

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

    suspend fun sendDbMessage(message: DbMessage, fileUri: Uri?, context: Context) {
        val maxChunkSize = 8192
        val messageJson = gson.toJson(message, DbMessage::class.java)
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        try {
            if(fileUri != null){
                coroutineScope.launch{
                    val inputStream = context.contentResolver.openInputStream(fileUri)
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
                            totalChunks = 0, // Placeholder value, will be updated later
                            chunkNumber = chunkNumber,
                            data = chunkData
                        )

                        // Send the chunk over the WebSocket
                        val myObject = SendObject("messageSend", chunkObject)
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
                        totalChunks = chunkNumber, // Set the totalChunks to the final chunk number (including the end marker)
                        chunkNumber = chunkNumber, // Use a special value to indicate the end marker
                        data = "" // Empty data for the end marker
                    )

                    val myObject = SendObject("messageSend", endMarkerObject)
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

                val myObject = SendObject("messageSend", messageToSend)
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
     * startSendingRequests could be used outside of NanoWebsocketClient because it
     * carries websocket inside, but not its functions.
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

    //    fun requestServer(param: String){
//        val httpClient = NanoHTTPClient()
//        //isForward == false (mensagens de retorno)
//        val objectMessageList = MessageList(param, 0, false, msgStatusNum = 3 )
//        val response = httpClient.sendGetRequest(serverUrl, objectMessageList)
//        Log.i(TAG,"Request GET for $param: $response")
//    }
    fun disconnect() {
        webSocketClient?.close(1000, "Client disconnecting")
    }
    private fun requestAuthorizationToken(): String? {
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS) // Increase the timeout duration as needed
            .build()
        val request = Request.Builder()
            .url("$serverUrl/auth") // Replace with your server's endpoint URL
            .addHeader("User-Agent", System.getProperty("http.agent")!!)
            .addHeader("Package-Name", packageName )
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
            }catch (e:IOException){
                if (retryCount < MAX_RETRIES) {
                    retryCount++
                    e.printStackTrace()
                    Thread.sleep(RETRY_DELAY_MS)
                } else {
                    // Retry limit exceeded, return null or throw an exception
                    return null
                }

            }catch (e: InterruptedException) {
                // Interrupted while waiting, return null or throw an exception
                return null
            } finally {
                // Close the response body if it is not null
                response?.body?.close()
            }
        }
        return null
    }
}



class MessageSenderAccess {
    suspend fun sendMessageToServer(message: DbMessage, context: Context, fileUri: Uri?) {
        NanoWebsocketClient.getInstance().sendDbMessage(message, fileUri, context)
    }
    fun sendRequest(param: String, param1: Any?, param2: Any?, param3: Any?, param4: Any?){
        NanoWebsocketClient.getInstance().sendMessageRequest(param, param1, param2, param3 , param4)

    }

}
data class SendObject(val param1: String?, val param2: Any?)
data class ReceivedRequestResponse(val param1: String?, val param2: String?, val param3: Any?, val param4:Any?)
data class RequestObject(val param1: Any?, val param2: Any?, val param3:Any?, val param4: Any?)
data class ChunkObject(
    val message: String,
    val totalChunks: Int,
    val chunkNumber: Int,
    val data: String // Use String to represent the chunk data
)


class NanoHTTPClient {
    private val gson = Gson()
    fun sendGetRequest(url: String, objectGet: Any): String {
        val jsonToEncode = gson.toJson(objectGet)
        val encodedMessage = URLEncoder.encode(jsonToEncode, "UTF-8")
        val urlWithMessage = "$url?message=$encodedMessage"

        val connection = URL(urlWithMessage).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"


        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
            reader.close()
            val responseData = response.toString()
            // Process the response data as needed
            Log.d(TAG, "Response from GET: $responseData" )
            return response.toString()
        }

        return ""
    }
}

interface WebSocketApi {
    @GET("/")
    fun connect(): Call<ResponseBody>

    @GET
    fun listenForMessages(toString: String): WebSocket
}
