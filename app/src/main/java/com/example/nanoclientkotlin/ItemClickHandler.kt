package com.example.nanoclientkotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class ItemClickHandler (){
    private val messageSenderAccess = MessageSenderAccess()
    fun handleClickEvent(context: Context, name: Int) {
        when(name){
            R.string.item_name_1 ->{
                val thread = Thread {
                    try {
                        NanoWebsocketClient.connect()
                        NanoWebsocketClient.sendMessageFromClient()
                        Log.i("NanoWebsocket", "NanoWebsoket Started")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                thread.start()
            }
            R.string.item_name_2 ->{
                (context as? Activity)?.runOnUiThread {
                    val intent = Intent(context, MessageInboxActivity::class.java)
                    context.startActivity(intent)

                }
               // messageSenderAccess.sendRequest()
            }
        }

    }
}