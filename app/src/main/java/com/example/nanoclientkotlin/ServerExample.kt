package com.example.nanoclientkotlin

import android.annotation.SuppressLint
import android.util.Log
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import okhttp3.internal.userAgent
import java.beans.PropertyChangeListener
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.Observable
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


    class NanoWebsocketServer(port: Int) : NanoWSD(port) {
        var connections: CopyOnWriteArrayList<WebSocket> = CopyOnWriteArrayList()
//        var clientMessageHandler = ClientMessageHandler()
        private val executor: ExecutorService = Executors.newFixedThreadPool(
            2)
        private val TAG = "Nano Server"

        public override fun openWebSocket(handshake: IHTTPSession): NanoWSD.WebSocket {
            var uri = handshake.uri
            uri = uri.replaceFirst("/", "", true)
            val authToken = handshake.headers["authorization"]
            val userAgent = handshake.headers["user-agent"]
            val packageName = handshake.headers["package-name"]
            return  if (authenticateWebSocket(authToken, userAgent, packageName)) {

                val webSocket = WebsocketServer(handshake, this)
                connections.add(webSocket)
                webSocket
            }
            else {
                // Authentication failed, return a custom WebSocket implementation that handles unauthorized requests
                //UnauthorizedWebSocket(handshake, this)
                Log.d("Nano Server","Failed to authenticate WebSocket connection" )
                throw IOException("Failed to authenticate WebSocket connection")

            }
            //return WebsocketServer(handshake, this)
        }
        public override fun serveHttp(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
            //Implemented CountDownLatch to handle TimeOut Exception
            val latch = CountDownLatch(1)
            var response: NanoHTTPD.Response? = null

            executor.submit {
                try {
                    when (session.method) {
                        //   NanoHTTPD.Method.GET -> response = clientMessageHandler.handleGetRequest(session)
                        NanoHTTPD.Method.POST -> response = clientMessageHandler.handlePostRequest(session)
                        else -> super.serveHttp(session)
                    }
                    if ("/auth" == session.uri && session.method == NanoHTTPD.Method.GET) {
                        val token = generateAuthorizationToken(session.headers["user-agent"], session.headers["package-name"])
                        if(token != null) {
                            //session.headers["user-agent"] gets the device info
                            //userAgent Okhttp receives internal id
                            Log.d(
                                TAG,
                                "userAgent no GET Request AUTH: ${session.headers["user-agent"]} or $userAgent, packageName: " +
                                        "${session.headers["package-name"]}"
                            )
                            response = newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "$token")

                        }else{
                            response = NanoHTTPD.newFixedLengthResponse(Response.Status.FORBIDDEN, MIME_PLAINTEXT, "PackageName not authorized.")
                        }
                    }
                } catch (e: SocketTimeoutException) {
                    response = newFixedLengthResponse(
                        Response.Status.REQUEST_TIMEOUT,
                        MIME_PLAINTEXT,
                        "Socket timeout occurred."
                    )
                } catch (e: Exception) {
                    response = newFixedLengthResponse(
                        Response.Status.INTERNAL_ERROR,
                        MIME_PLAINTEXT,
                        "Internal server error."
                    )
                } finally {
                    latch.countDown()
                }
            }
            try {
                // Wait for the result with a timeout of 5 seconds
                if (!latch.await(10, TimeUnit.SECONDS)) {
                    // Timeout occurred
                    return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Timeout occurred.")
                }
            } catch (e: InterruptedException) {
                // Interrupted while waiting
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Interrupted while waiting.")
            }

            return response ?: newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Error occurred.")
        }
        fun authenticateWebSocket(authToken: String?, userAgent: String?, packageName: String?): Boolean {
            val packageName = extractPackageNameFromUserAgent(userAgent)
            val authTokenDecrypt = AESEncyption.decrypt(authToken!!)
            return authTokenDecrypt == "AUTH_TOKEN"
        }
        private fun extractPackageNameFromUserAgent(userAgent: String?): String? {
            // Example: Extract the package name from the User-Agent string (Android)
            val pattern = Regex("Android[^;]+;\\s*([^\\s\\)]+)")
            val matchResult = pattern.find(userAgent ?: "")
            return matchResult?.groupValues?.getOrNull(1)
        }
        private fun generateAuthorizationToken(userAgent:String?, packageName: String?): String? {
            // Generate the authorization token here based on your logic
            val packageNameString = extractPackageNameFromUserAgent(userAgent)
            Log.d(TAG, "packageName on authenticate Websocket: $packageName")
            //Validate packageName in list on BD.
            val token = AESEncyption.encrypt("AUTH_TOKEN")
            Log.d(TAG, "token no generate: $token")
            return token
        }
    }


    object NanoSendMessageNotification{
        private val externalMessageHandler = ExternalMessageHandler()
        private val clientMessageHandler = ClientMessageHandler()
        /****************************************************************************************
         * Evento que sinaliza a ocorrencia de Status de Envio e Recebimento de Mensagens.
         */
        fun notifyMessages(appFilesPath: String) {
            externalMessageHandler.addEventMessage()
            //Log.d("NanoWebsocket","NotificationService from NanoWebsocket" )
            clientMessageHandler.filesLogPath = appFilesPath
        }

    }



}


class WebsocketServer(
    handshakeRequest: NanoHTTPD.IHTTPSession,
    private var httpServer: NanoWebsocketServer,
) : NanoWSD.WebSocket(handshakeRequest), PropertyChangeListener {

    companion object {
        const val TAG = "NanoWebsocket"
    }

    val gson = Gson()

    //var externalMessageHandler = ExternalMessageHandler()
    private var connectionTime: Long = 0
    private val state: NanoWSD.State = NanoWSD.State.UNCONNECTED
    private var subscriptionList:List<Int>? = null
    private var clientMessageHandler = ClientMessageHandler()

    init {
        // Add this class as a listener to the ObservableUtil
        ObservableUtil.addPropertyChangeListener(this)
    }


    public override fun onOpen() {
        // WebSocket connection is opened
        Log.d(TAG, "Nano onOpen: ")
        httpServer.connections.add(this)

        try {
            val pingFrame = NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")
            ping(pingFrame.binaryPayload)
            Log.d(TAG, "Nano onOpen: ping")
            connectionTime = System.currentTimeMillis()
            Thread {
                startRunner()
                startNotification()
            }.start()
        } catch (e: CharacterCodingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @SuppressLint("CheckResult")
    fun startRunner() {
        Observable.fromCallable {
            //Do Something
            Log.d(TAG, "onOpen: 2")

            val pingFrame = NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")

            //TODO: Implement all Event Notifications per filter in the first call

            ping(pingFrame.binaryPayload)
            true
        }.delay(1, TimeUnit.SECONDS)
            .repeat()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            //.timeout(8, TimeUnit.SECONDS)
            .subscribe({
                // Handle onNext value
                // println("Received value: $onNextValue")
            },
                { throwable ->
                    when (throwable) {
                        is TimeoutException -> {
                            // Handle read timeout
                            Log.e(TAG, "Read timed out.")
                        }

                        is IOException -> {
                            // Handle connection closed or network error
                            Log.e(TAG, "Socket closed or network error: ${throwable.message}")
                        }

                        else -> {
                            // Handle other types of errors
                            Log.e(TAG, "Error: ${throwable.message}")
                        }
                    }
                }
            )


    }
    @SuppressLint("CheckResult")
    fun startNotification(){
        Observable.fromCallable {
            //Do Something
            scheduledNotification()
            Log.d(TAG, "Ready for Notification every 5 min")

            val pingFrame = NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")

            //TODO: Implement all Event Notifications per filter in the first call

            ping(pingFrame.binaryPayload)
            true
        }.delay(5, TimeUnit.MINUTES)
            .repeat()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            //.timeout(8, TimeUnit.SECONDS)
            .subscribe({
                // Handle onNext value
                // println("Received value: $onNextValue")
            },
                { throwable ->
                    when (throwable) {
                        is TimeoutException -> {
                            // Handle read timeout
                            Log.e(TAG, "Read timed out Notification.")
                        }

                        is IOException -> {
                            // Handle connection closed or network error
                            Log.e(TAG, "Socket closed or network error Notification: ${throwable.message}")
                        }

                        else -> {
                            // Handle other types of errors
                            Log.e(TAG, "Error Notification: ${throwable.message}")
                        }
                    }
                }
            )

    }

    public override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode?,
        reason: String?,
        initiatedByRemote: Boolean,
    ) {
        // WebSocket connection is closed
        this.httpServer.connections.remove(this)
    }

    public override fun onMessage(message: NanoWSD.WebSocketFrame) {
        val payload = message.textPayload
        val thread = Thread {

            try {
                val jsonElement = JsonParser.parseString(payload)
                when {
                    jsonElement.isJsonObject -> {

                        val objectPayload =
                            gson.fromJson(payload, WebsocketObject::class.java)
                        when (objectPayload.param1) {
                            "notification" -> handleNotification(objectPayload)
                            "messageSend" -> {
                                val response = clientMessageHandler.handleMessageObject(jsonElement.asJsonObject)
                                send(response)
                            }
                            IntegrationConsts.REQ_MESSAGE_LIST, IntegrationConsts.REQ_MESSAGE_COUNT, IntegrationConsts.REQ_MESSAGE_DELETE, IntegrationConsts.REQ_MESSAGE_SET_AS_READ,
                            IntegrationConsts.REQ_CONFIG_SERVICE_LOG, IntegrationConsts.REQ_FILE_OPERATION,
                            IntegrationConsts.REQ_FORM_LIST, IntegrationConsts.REQ_GET_CHECKLIST, IntegrationConsts.REQ_GET_CURRENT_DATE,
                            IntegrationConsts.REQ_GET_MCT_PARAMETERS, IntegrationConsts.REQ_GET_POSITION_LAST,
                            IntegrationConsts.REQ_POSITION_HISTORY_COUNT, IntegrationConsts.REQ_POSITION_HISTORY_LIST,
                            IntegrationConsts.REQ_RESET_DATABASE -> {
                                val response =   clientMessageHandler.handleWSRequests(
                                    objectPayload, jsonElement.asJsonObject)
                                send(response)
                            }
                        }
                    }

                    jsonElement.isJsonPrimitive -> {
                        clientMessageHandler.handleJsonPrimitive(jsonElement.asJsonPrimitive)
                    }
                    else -> {
                        clientMessageHandler.handleUnrecognizedJsonFormat(payload)
                    }
                }
                message.setUnmasked()
            } catch (e: Exception) {
                clientMessageHandler.handleProcessingError(payload, e)
            }
        }
        thread.start()
    }

    override fun onPong(pong: NanoWSD.WebSocketFrame) {

        // Handle WebSocket Pong frame
        if(!AESEncyption.isTokenExpired() ){
            Log.d(TAG, "onPong: ")
        }else if(AESEncyption.isTokenExpired() && subscriptionList != null){
            Log.d(TAG, "onPong: Notification On ")
        }else{
            closeWebSocket()
            Log.d(TAG, "Token Expired, Connection closed and Removed")
        }
        //connectionTime = System.currentTimeMillis()

    }

    override fun onException(exception: IOException) {
        // Handle WebSocket exception
        Log.d(TAG, "onException: " + exception.message)
    }

    override fun isOpen(): Boolean {
        return state == NanoWSD.State.OPEN
    }

    override fun propertyChange(evt: PropertyChangeEvent?) {
        if (evt != null) {
            when (evt.propertyName) {
                IntegrationConsts.MESSAGE_COUNT -> {
                }
                IntegrationConsts.MESSAGE -> {
                }
                IntegrationConsts.BAPTISM_STATUS -> {
                    if(subscriptionList!= null && AmcuipValuesActionIds.BAPTISM_STATUS in subscriptionList!!) {
                        val value = ObservableUtil.getValue(IntegrationConsts.BAPTISM_STATUS)
                        sendImmediately(IntegrationConsts.BAPTISM_STATUS, value!!)
                    }
                }
                IntegrationConsts.MESSAGE_CODE -> {
                    if(subscriptionList!= null && AmcuipValuesActionIds.MESSAGE_STATUS in subscriptionList!!) {
                        val messageCode = ObservableUtil.getValue(IntegrationConsts.MESSAGE_CODE)
                        if (messageCode != null) {
                            sendImmediately(IntegrationConsts.MESSAGE_CODE, messageCode)
                        }
                    }
                }
                IntegrationConsts.COMMUNICATION_MODE_CHANGED ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.COMMUNICATION_MODE_CHANGED in subscriptionList!!) {
                        val value = ObservableUtil.getValue(IntegrationConsts.COMMUNICATION_MODE_CHANGED)
                        if (value != null) {
                            sendImmediately(IntegrationConsts.COMMUNICATION_MODE_CHANGED, value)
                        }
                    }
                }
                IntegrationConsts.DATE_TIME_CHANGED ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.DATE_TIME_CHANGED in subscriptionList!!) {
                        val value = ObservableUtil.getValue(IntegrationConsts.DATE_TIME_CHANGED)
                        if (value != null) {
                            sendImmediately(IntegrationConsts.DATE_TIME_CHANGED, value)
                        }
                    }
                }
                IntegrationConsts.FILE_OPERATION_STATUS ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.FILE_OPERATION_STATUS in subscriptionList!!) {
                        val value = ObservableUtil.getValue(IntegrationConsts.FILE_OPERATION_STATUS)
                        if (value != null) {
                            sendImmediately(IntegrationConsts.FILE_OPERATION_STATUS, value)
                        }
                    }
                }
                IntegrationConsts.FORM_DELETED ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.FORM_DELETED in subscriptionList!!) {
                        val value = ObservableUtil.getValue(IntegrationConsts.FORM_DELETED)
                        if (value != null) {
                            sendImmediately(IntegrationConsts.FORM_DELETED, value)
                        }
                    }
                }
                IntegrationConsts.FORM_RECEIVED ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.FORM_RECEIVED in subscriptionList!!) {
                        val value = ObservableUtil.getValue(IntegrationConsts.FORM_RECEIVED)
                        if (value != null) {
                            sendImmediately(IntegrationConsts.FORM_RECEIVED, value)
                        }
                    }
                }
                IntegrationConsts.IGNITION_STATUS ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.IGNITION_STATUS in subscriptionList!!) {
                        val value = ObservableUtil.getValue(IntegrationConsts.IGNITION_STATUS)
                        if (value != null) {
                            sendImmediately(IntegrationConsts.IGNITION_STATUS, value)
                        }
                    }
                }
                IntegrationConsts.MCT_M2_PARAM ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.MCT_M0_M9_PARAMS_UPDATED in subscriptionList!!) {
                        val param1 = ObservableUtil.getValue(IntegrationConsts.MCT_M0_PARAM)
                        val param2 = ObservableUtil.getValue(IntegrationConsts.MCT_M2_PARAM)
                        val listParam:List<Any?> = listOf(param1, param2)
                        if (param1 != null && param2 != null ) {
                            sendImmediately(IntegrationConsts.MCT_M0_M9_PARAMS_UPDATED, listParam)
                        }
                    }
                }
                IntegrationConsts.NETWORK_CONNECTION_STATUS ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.NETWORK_CONNECTION_STATUS in subscriptionList!!) {
                        val param1 = ObservableUtil.getValue(IntegrationConsts.NETWORK_CONNECTION_TYPE)
                        val param2 = ObservableUtil.getValue(IntegrationConsts.NETWORK_CONNECTION_STATUS)
                        val listParam:List<Any?> = listOf(param1, param2)
                        if (param1 != null ) {
                            sendImmediately(IntegrationConsts.NETWORK_CONNECTION_STATUS, listParam)
                        }
                    }
                }
                IntegrationConsts.READY_TO_UPDATE_SOFTWARE ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.READY_TO_UPDATE_SOFTWARE in subscriptionList!!) {
                        val param3 = ObservableUtil.getValue(IntegrationConsts.READY_TO_UPDATE_SOFTWARE)
                        if (param3 != null ) {
                            sendImmediately(IntegrationConsts.READY_TO_UPDATE_SOFTWARE, param3)
                        }
                    }
                }
                IntegrationConsts.SATELLITE_SIGNAL_CHANGED ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.SATELLITE_SIGNAL_CHANGED in subscriptionList!!) {
                        val param1 = ObservableUtil.getValue(IntegrationConsts.SATELLITE_SIGNAL_CHANGED)
                        if (param1 != null ) {
                            sendImmediately(IntegrationConsts.SATELLITE_SIGNAL_CHANGED, param1)
                        }
                    }
                }
                IntegrationConsts.SIGN_ON_STATUS ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.SIGN_ON_STATUS in subscriptionList!!) {
                        val param1 = ObservableUtil.getValue(IntegrationConsts.SIGN_ON_STATUS)
                        if (param1 != null ) {
                            sendImmediately(IntegrationConsts.SIGN_ON_STATUS, param1)
                        }
                    }
                }
                IntegrationConsts.SYSTEM_RESOURCE_REQ_STATUS ->{
                    if(subscriptionList!= null && AmcuipValuesActionIds.SYSTEM_RESOURCE_REQ_STATUS in subscriptionList!!) {
                        val param1 = ObservableUtil.getValue(IntegrationConsts.NETWORK_CONNECTION_TYPE)
                        val param2 = ObservableUtil.getValue(IntegrationConsts.NETWORK_CONNECTION_STATUS)
                        val listParam:List<Any?> = listOf(param1, param2)
                        if (param2 != null ) {
                            sendImmediately(IntegrationConsts.SYSTEM_RESOURCE_REQ_STATUS, listParam)
                        }
                    }
                }
            }
        }

    }
    private fun sendImmediately(label: String, messageInQueue: Any){
        val myObject = gson.toJson(
            WebsocketRequestResponse(
                "notification", label,
                messageInQueue, null
            )
        )
        try {
            send(myObject)
            Log.d(TAG, "Sending notification Immediately: $myObject")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(
                TAG,
                "Websocket Notification sendImmediately: $e"
            )
        }
    }
    private fun closeWebSocket() {
        val closeFrame = NanoWSD.WebSocketFrame(
            NanoWSD.WebSocketFrame.OpCode.Close,
            true,
            ""
        )
        sendFrame(closeFrame)
        httpServer.connections.remove(this)
    }
    private fun handleNotification(objectPayload: WebsocketObject) {
        val objectFilter = NotificationSubscriber.subscriptionMessage(objectPayload)
        subscriptionList = objectFilter

        val response = if (subscriptionList?.isNotEmpty() == true) {
            gson.toJson(WebsocketObject("response", NanoHTTPD.Response.Status.OK.toString()))
        } else {
            gson.toJson(
                WebsocketObject(
                    "response",
                    NanoHTTPD.Response.Status.NOT_IMPLEMENTED.toString()
                )
            )
        }
        send(response)
        Log.d(TAG, "Received Subscription list: $objectFilter")
    }
    private fun scheduledNotification(){
        if(AmcuipValuesActionIds.MESSAGE_STATUS in subscriptionList!!) {

            // Code to send the message
            val messageCode = ObservableUtil.getValue(IntegrationConsts.MESSAGE_CODE)
            val messageCodeList = clientMessageHandler.handleNotificationNotRead()
            if (messageCode != null) {
                try {
                    sendImmediately(IntegrationConsts.MESSAGE_CODE, messageCode)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "There is no message in queue")
                }

            } else {
                try {

                    sendImmediately(IntegrationConsts.MESSAGE_CODE, messageCodeList!!)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "There is no message NOT READ")
                }
            }
            Log.d(TAG, "Sending notification every 5 min")

        }
    }
}