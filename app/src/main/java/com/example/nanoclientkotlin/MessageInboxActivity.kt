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


