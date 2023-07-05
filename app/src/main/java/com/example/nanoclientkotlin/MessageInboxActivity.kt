package com.example.nanoclientkotlin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.screens.MessageListScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MessageInboxActivity {
    //var messageSenderAccess = MessageSenderAccess()
    var messages: ArrayList<DbMessage> = arrayListOf()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        handleWebsocketRequestMessageList()
//        setContent {
//            NanoClientKotlinTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    InboxMessagesScreen()
//                }
//            }
//        }
//    }

    private fun handleWebsocketRequestMessageList() {
//        messageSenderAccess.sendRequest()
//        messageSenderAccess.listenForMessageList()

        // Pass the list of messages to the MessageInboxActivity composable


        // return "Success"
    }

    fun setMessages(json: String) {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<DbMessage>>() {}.type
        messages = gson.fromJson(json, type)

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InboxMessagesScreen() {


        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Inbox") }
                )
            },
            content = {innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    MessageListScreen()
                }
            }
        )
    }

}


