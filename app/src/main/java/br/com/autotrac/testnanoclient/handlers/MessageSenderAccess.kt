package br.com.autotrac.testnanoclient.handlers

import android.content.Context
import android.net.Uri
import br.com.autotrac.testnanoclient.NanoWebsocketClient
import br.com.autotrac.testnanoclient.dataRemote.IntegrationMessage

/**
 * Classe para acessar externamente o Websocket Client.
 * @author Melina Minaya
 */
class MessageSenderAccess {
    suspend fun sendMessageToServer(message: IntegrationMessage, context: Context, fileUri: Uri?) {
        NanoWebsocketClient.getInstance().sendDbMessage(message, fileUri, context)
    }

    fun sendRequest(param: String, param1: Any?, param2: Any?, param3: Any?, param4: Any?) {
        NanoWebsocketClient.getInstance().sendMessageRequest(param, param1, param2, param3, param4)

    }

}