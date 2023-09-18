package com.example.nanoclientkotlin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.common.Alert
import com.example.nanoclientkotlin.common.LoadingIcon
import com.example.nanoclientkotlin.common.MessageListComposable
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.vm.MessageViewModel
import kotlinx.coroutines.launch


@Composable
fun MessageListScreen() {

    val viewModel: MessageViewModel = viewModel()
    val showDialog = remember { mutableStateOf(false) }
    val messagesState by viewModel.messages.observeAsState(emptyList())
    val messagesList = rememberSaveable{ mutableStateOf(messagesState) }
    val clickedMessage = remember { mutableStateOf<DbMessage?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
    }
    LaunchedEffect(messagesState){
        messagesList.value = messagesState
    }

    Box {
        if (messagesList.value.isEmpty()) {
            // Show the loading icon here
            Column(verticalArrangement = Arrangement.Center) {
                LoadingIcon(100)
            }
        } else {
            MessageListComposable(
                messages = messagesList.value,
                onMessageDelete = { message ->
                    viewModel.deleteMessage(message)
                },
                onMessageClick = { message ->
                    showDialog.value = true
                    clickedMessage.value = message
                },
                onDialogDismiss = {
                    showDialog.value = false
                },
                onRefresh = {
                   coroutineScope.launch {
                       viewModel.fetchMessages()
                   }
                }
            )
        }
    }

    if (showDialog.value) {
        Alert(
            onDismissRequest = { showDialog.value = false },
            onClick = {
                clickedMessage.value?.let { message ->
                    // Mark the clicked message as read
                    viewModel.markMessageAsRead(message = message)
                }
                showDialog.value = false
            },
            title = "MsgCode: ${clickedMessage.value?.code}",
            content = {
                Column {
                    Text(text = clickedMessage.value?.body ?: "")
                    val filePreviewName = clickedMessage.value?.outOfBandFilename
                    if (!filePreviewName.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "File Name: $filePreviewName")
                    }
                }
            }
        )
    }
}


