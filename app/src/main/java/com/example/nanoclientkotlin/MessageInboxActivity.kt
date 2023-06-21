package com.example.nanoclientkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.JsonParser
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
                    MessagesList(messages)
                }
            }
        )
    }
    @Composable
    fun MessagesList(messages: ArrayList<DbMessage>) {
        Column(
            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            messages.forEach{message ->

                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = message.body.toString(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


