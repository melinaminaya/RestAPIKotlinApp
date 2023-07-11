package com.example.nanoclientkotlin

import android.util.Log
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.dataRemote.FilterModel
import com.example.nanoclientkotlin.dataRemote.ParseData
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import fi.iki.elonen.NanoHTTPD
import io.reactivex.rxjava3.core.Observable
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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


object NanoWebsocketClient{
    private const val serverUrl = "http://127.0.0.1:8081" // Replace with your NanoHTTPD WebSocket server URL
    private var webSocketClient: WebSocket? = null
    private val gson:Gson = Gson()
    const val TAG = "NanoWebsocket"
    private const val MAX_RETRIES = 5
    private const val RETRY_DELAY_MS: Long = 5000 // 1 second
    private val client = OkHttpClient()
    val packageName = "com.example.nanoclientkotlin"
    const val CONNECTION_CHECK_INTERVAL_MS = 1000
    private const val WEBSOCKET_TIMEOUT_MS = 30000
    // Declare the observable subject
    private val webSocketConnectionSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    // Expose the observable to observe the WebSocket connection status
    private fun observeWebSocketConnection(): Observable<Boolean> = webSocketConnectionSubject

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
                            val notification = gson.fromJson(text, ReceivedRequestResponse::class.java)
                            when (notification.param1) {
                                "notification" -> {
                                    Log.d(TAG, "Received notification: $notification")
                                    sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                    //Thread.sleep(500)
                                    //sendMessageList("messageList")
                                    // requestServer("messageList")
                                }

                                "request" -> {
                                    when(notification.param2){
                                        "messageList"->{
                                            val messagesList = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received messageList: $notification")
                                            val param3Json = notification.param3.toString()
                                            val filterList = ParseData.parseMessageList(param3Json)
                                            Log.d(TAG, "$filterList")
                                            when(filterList.param2){
                                                true->ObservableUtil.attachProperty(ConstsCommSvc.REQ_MESSAGE_LIST_OUTBOX, messagesList) // equal fetchOutboxMessages
                                               false-> ObservableUtil.attachProperty(ConstsCommSvc.REQ_MESSAGE_LIST_INBOX, messagesList)

                                            }

                                        }
                                        "messageCount" ->{
                                            val count = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received messageCount: $notification")
                                            ObservableUtil.attachProperty("messageCount", count)
                                        }
                                        "messageDelete" ->{
                                            val messageDeleted = notification.param3
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received messageDelete Response: $notification")
                                        }
                                        ConstsCommSvc.REQ_MESSAGE_SET_AS_READ ->{
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_MESSAGE_SET_AS_READ} Response: $notification")
                                        }
                                        ConstsCommSvc.REQ_FORM_LIST ->{
                                            val formList = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_FORM_LIST}: $notification")
                                            ObservableUtil.attachProperty(ConstsCommSvc.REQ_FORM_LIST, formList)
                                        }
                                        ConstsCommSvc.REQ_GET_CHECKLIST ->{
                                            val checkList = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_GET_CHECKLIST}: $notification")
                                            ObservableUtil.attachProperty(ConstsCommSvc.REQ_GET_CHECKLIST, checkList)
                                        }
                                        ConstsCommSvc.REQ_GET_CURRENT_DATE ->{
                                            val objectReq = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_GET_CURRENT_DATE}: $notification")
                                            ObservableUtil.attachProperty(ConstsCommSvc.REQ_GET_CURRENT_DATE, objectReq)
                                        }
                                        ConstsCommSvc.REQ_GET_MCT_PARAMETERS ->{
                                            val objectReq = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_GET_MCT_PARAMETERS}: $notification")
                                            ObservableUtil.attachProperty(ConstsCommSvc.REQ_GET_MCT_PARAMETERS, objectReq)
                                        }
                                        ConstsCommSvc.REQ_GET_POSITION_LAST ->{
                                            val objectReq = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_GET_POSITION_LAST}: $notification")
                                            ObservableUtil.attachProperty(ConstsCommSvc.REQ_GET_POSITION_LAST, objectReq)
                                        }
                                        ConstsCommSvc.REQ_POSITION_HISTORY_COUNT ->{
                                            val objectReq = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_POSITION_HISTORY_COUNT}: $notification")
                                            ObservableUtil.attachProperty(ConstsCommSvc.REQ_POSITION_HISTORY_COUNT, objectReq)
                                        }
                                        ConstsCommSvc.REQ_POSITION_HISTORY_LIST ->{
                                            val objectReq = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_POSITION_HISTORY_LIST}: $notification")
                                            ObservableUtil.attachProperty(ConstsCommSvc.REQ_POSITION_HISTORY_LIST, objectReq)
                                        }
                                        ConstsCommSvc.REQ_RESET_DATABASE ->{
                                            val objectReq = notification.param4
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received ${ConstsCommSvc.REQ_RESET_DATABASE}: $notification")
                                            ObservableUtil.attachProperty(ConstsCommSvc.REQ_RESET_DATABASE, objectReq)
                                        }

                                    }

                                }
                                "messageSent" ->{
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
                    retryConnection()
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d(TAG, "WebSocket connection closed: $reason")
                    webSocketConnectionSubject.onNext(false)
                    connectionDisposable!!.dispose()
                }


            })

        }

    }


    fun retryConnection() {
        var retries = 0

        while (retries < MAX_RETRIES) {
            try {
                Thread.sleep(RETRY_DELAY_MS)
                connect()
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
                listOf(AmcuipValuesActionIds.MESSAGE_STATUS, AmcuipValuesActionIds.BAPTISM_STATUS,
                    AmcuipValuesActionIds.COMMUNICATION_MODE_CHANGED, AmcuipValuesActionIds.DATE_TIME_CHANGED,
                    AmcuipValuesActionIds.FILE_OPERATION_STATUS, AmcuipValuesActionIds.FORM_DELETED,
                    AmcuipValuesActionIds.IGNITION_STATUS, AmcuipValuesActionIds.MCT_M0_M9_PARAMS_UPDATED,
                    AmcuipValuesActionIds.NETWORK_CONNECTION_STATUS, AmcuipValuesActionIds.READY_TO_UPDATE_SOFTWARE,
                    AmcuipValuesActionIds.SATELLITE_SIGNAL_CHANGED, AmcuipValuesActionIds.SIGN_ON_STATUS,
                    AmcuipValuesActionIds.SYSTEM_RESOURCE_REQ_STATUS))

        val objectJson = gson.toJson(notificationSubscription)
        webSocketClient!!.send(objectJson)
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

    fun sendDbMessage(message: DbMessage) {
        try {
            val messageJson = gson.toJson(message, DbMessage::class.java)
            val myObject = SendObject("messageSend", messageJson)
            val response = gson.toJson(myObject)
            if (webSocketClient != null) {
                webSocketClient!!.send(response)
            }
            Log.d(TAG, "sendDbMessageToJson: $response")
        } catch (e: Exception) {
            connect()
            sendDbMessage(message)
        }

    }

    fun sendMessageRequest(param: String, param1: Any?, param2: Any?, param3: Any?) {
        try {
            val objectRequestJson: String

            val response: String = when (param) {
                ConstsCommSvc.REQ_MESSAGE_LIST -> {
                    val objectMessageList = RequestObject(param1, param2, param3)
                    gson.toJson(objectMessageList)
                }

                ConstsCommSvc.REQ_MESSAGE_COUNT -> {
                    val objectMessageCount = MessageCount(false, msgStatusNum = 3)
                    gson.toJson(objectMessageCount)
                }
                ConstsCommSvc.REQ_MESSAGE_DELETE ->{
                    val objectDelete = MessageCode(msgCode = param1!! as Long)
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_MESSAGE_SET_AS_READ ->{
                    val objectDelete = MessageCode(msgCode = param1!! as Long)
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_CONFIG_SERVICE_LOG ->{
                    val objectDelete = MessageCode(msgCode =param1!! as Long)
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_FILE_OPERATION ->{
                    val objectDelete = MessageCode(msgCode =param1!! as Long)
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_FORM_LIST ->{
                    val objectDelete = RequestObject( param1 = param1, param2 = param2, param3 = param3 )
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_GET_CHECKLIST ->{
                    val objectReq = RequestObject( param1 = param1, param2 = param2, param3 = param3 )
                    gson.toJson(objectReq)
                }
                ConstsCommSvc.REQ_GET_CURRENT_DATE ->{
                    val objectDelete = RequestObject( param1 = param1, param2 = param2, param3 = param3 )
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_GET_MCT_PARAMETERS ->{
                    val objectDelete = RequestObject( param1 = param1, param2 = param2, param3 = param3 )
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_GET_POSITION_LAST ->{
                    val objectDelete = RequestObject( param1 = param1, param2 = param2, param3 = param3 )
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_POSITION_HISTORY_COUNT ->{
                    val objectDelete = RequestObject( param1 = param1, param2 = param2, param3 = param3 )
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_POSITION_HISTORY_LIST ->{
                    val objectDelete = RequestObject( param1 = param1, param2 = param2, param3 = param3 )
                    gson.toJson(objectDelete)
                }
                ConstsCommSvc.REQ_RESET_DATABASE ->{
                    val objectDelete = RequestObject( param1 = param1, param2 = param2, param3 = param3 )
                    gson.toJson(objectDelete)
                }
                else -> return // Return early if the parameter is not valid
            }

            val objectRequest = SendObject(param, response)
            objectRequestJson = gson.toJson(objectRequest)

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
                sendMessageRequest("messageCount", null, null, null)

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
        webSocketClient!!.close(1000, "Client disconnecting")
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
var messageInboxActivity = MessageInboxActivity()
    fun sendMessageToServer(message: DbMessage) {
        NanoWebsocketClient.getInstance().sendDbMessage(message)
    }
    fun sendRequest(param: String, param1: Any?, param2: Any?, param3: Any?){
        NanoWebsocketClient.getInstance().sendMessageRequest(param, param1, param2, param3 )

    }
//    fun listenForMessageList(): Disposable {
//        val disposable = NanoWebsocketClient.getInstance().observeMessageList()
//
//            .subscribe { messageList ->
//                // Handle the received messageList here
//               messageInboxActivity.setMessages(messageList)
//                println("Received messageList: $messageList")
//            }
//        return disposable
//    }
}
data class SendObject(val param1: String?, val param2: Any?)
data class ReceivedNotification(val param1: String?, val param2: String?, val param3: Any?)
data class ReceivedRequestResponse(val param1: String?, val param2: String?, val param3: Any?, val param4:Any?)
data class MessageList( val msgCode: Long, val isForward: Boolean?, val msgStatusNum: Int?)
data class MessageCount(val isForward: Boolean?, val msgStatusNum: Int?)
data class MessageCode(val msgCode: Long)
data class RequestObject(val param1: Any?, val param2: Any?, val param3:Any?)


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