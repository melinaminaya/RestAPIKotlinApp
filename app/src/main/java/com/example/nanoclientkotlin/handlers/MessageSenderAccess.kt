package com.example.nanoclientkotlin.handlers

import android.content.Context
import android.net.Uri
import com.example.nanoclientkotlin.NanoWebsocketClient
import com.example.nanoclientkotlin.dataRemote.DbMessage

/**
 * Classe para acessar externamente o Websocket Client.
 * @author Melina Minaya
 */
class MessageSenderAccess {
    suspend fun sendMessageToServer(message: DbMessage, context: Context, fileUri: Uri?) {
        NanoWebsocketClient.getInstance().sendDbMessage(message, fileUri, context)
    }

    fun sendRequest(param: String, param1: Any?, param2: Any?, param3: Any?, param4: Any?) {
        NanoWebsocketClient.getInstance().sendMessageRequest(param, param1, param2, param3, param4)

    }

}