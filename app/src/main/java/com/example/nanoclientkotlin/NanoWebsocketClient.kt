package com.example.nanoclientkotlin

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import fi.iki.elonen.NanoHTTPD
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.coroutines.coroutineContext
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.Date


object NanoWebsocketClient{
    private const val serverUrl = "http://127.0.0.1:8081" // Replace with your NanoHTTPD WebSocket server URL
    private var webSocketClient: WebSocket? = null
    private val gson:Gson = Gson()
    const val TAG = "NanoWebsocket"
    private const val MAX_RETRIES = 5
    private const val RETRY_DELAY_MS: Long = 5000 // 1 second
    private val client = OkHttpClient()
    private val packageName = "com.example.nanoclientkotlin"
    const val CONNECTION_CHECK_INTERVAL_MS = 1000
    // Declare the observable subject
    private val webSocketConnectionSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    // Expose the observable to observe the WebSocket connection status
    private fun observeWebSocketConnection(): Observable<Boolean> = webSocketConnectionSubject


    var connectionDisposable: Disposable? = null
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

            webSocketClient = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                    println("WebSocket connected")
                    webSocketConnectionSubject.onNext(true)
//                    connectionDisposable = observeWebSocketConnection()
//                        .subscribe { isConnected ->
//                            // Handle the WebSocket connection status
//                           // Thread.sleep(10000)
//                            if (isConnected) {
//                                // WebSocket is connected
//                                Log.d(TAG, "Websocket is connected")
//                            } else {
//                                // WebSocket is disconnected
//                                retryConnection()
//                                Log.d(TAG, "Websocket is retrying connection")
//                            }
//                        }
                }

                override fun onMessage(webSocket: WebSocket, text: String) {

                    try {
                        val jsonElement = JsonParser.parseString(text)
                        if (jsonElement.isJsonObject) {
                            val notification = gson.fromJson(text, ReceivedNotification::class.java)
                            when (notification.param1) {
                                "notification" -> {
                                    Log.d(TAG, "Received message: $notification")
                                    sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                    //Thread.sleep(500)
                                    //sendMessageList("messageList")
                                    // requestServer("messageList")
                                }

                                "request" -> {
                                    when(notification.param2){
                                        "messageList"->{
                                            val messagesList = notification.param3
                                            sendMessage(NanoHTTPD.Response.Status.OK.toString())
                                            Log.d(TAG, "Received messageList: $notification")
                                            ObservableUtil.attachProperty("messageList", messagesList)
                                        }
                                    }

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
        sendMessageRequest("messageList")
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
            val myObject = SendObject("message", messageJson)
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

    fun sendMessageRequest(param: String) {
        try {
            val objectRequestJson: String

            val response: String = when (param) {
                "messageList" -> {
                    val objectMessageList = MessageList(0, false, msgStatusNum = null)
                    gson.toJson(objectMessageList)
                }

                "messageCount" -> {
                    val objectMessageCount = MessageCount(false, msgStatusNum = 3)
                    gson.toJson(objectMessageCount)
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
        val client = OkHttpClient()

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
    fun sendRequest(){
        NanoWebsocketClient.getInstance().sendMessageRequest("messageList")

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
data class MessageList( val msgCode: Long, val isForward: Boolean?, val msgStatusNum: Int?)
data class MessageCount(val isForward: Boolean?, val msgStatusNum: Int?)


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

data class DbMessage(
    val code: Long, val isForward: Boolean, val subject:String, val body:ByteArray, val sourceAddr:String, val dateTime: Date,
    val createdTime:Date, val sndRcvdTime: Date, val deliveryTime : Date, val codeMsgStatus: Long, val lastStatusTime: Date,
    val returnReadReceipt:Boolean, val subtype: Int, val codeMsgPriority:Long, val codeForm: Long, val gmn:Long,
    val replyGmn:Long, val posLatitude:Int, val posLongitude:Int,
    val posTime:Date, val posSpeed:Int, val posHeading:Float, val posAging:Int, val posFlags:Int, val transmissionChannel: Int,
    val transmittedChannel:Int, val transmissionType:Int, val isOutOfBandMessage:Boolean, val outOfBandSessionId:Long,
    val outOfBandFileName:String, val outOfBandNumMsgStatus:Int, val extDevMessageId: Int,val itemId:Long,val messageId:Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DbMessage

        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        return body.contentHashCode()
    }
}