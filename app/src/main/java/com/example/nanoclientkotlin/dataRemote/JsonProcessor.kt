package com.example.nanoclientkotlin.dataRemote

import android.util.Log
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.WebSocket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue


class JsonProcessor(private val webSocketClient: WebSocket?) {
    private val jsonQueue: BlockingQueue<String> = LinkedBlockingQueue()
    fun processJson(json: String) {
        jsonQueue.add(json)
    }

    suspend fun startProcessing() {
        while (true) {
            val json = withContext(Dispatchers.IO) {
                jsonQueue.take()
            }
            processJson(json)
            processSingleJson(json)
        }
    }
    fun processSingleJson(json: String) {
        // You can implement your specific JSON processing logic here
        // For example, sending the JSON to the WebSocket client
        webSocketClient?.send(json)
        // Alternatively, perform any other processing that you need to do
        // ...
    }
}