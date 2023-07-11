package com.example.nanoclientkotlin.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.common.Alert
import com.example.nanoclientkotlin.vm.MessageViewModel


@Composable
fun OutboxScreen() {

    val viewModel: MessageViewModel = viewModel()
    val showDialog = remember { mutableStateOf(false) }
    val messagesState by viewModel.outboxMessages.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchOutboxMessages()
    }

    MessageListComposable(
        messages = messagesState,
        onMessageDelete = { message ->
            viewModel.deleteMessage(message)
        },
        onMessageClick = { message ->
            showDialog.value = true
//            viewModel.markMessageAsRead(message)
        },
        onDialogDismiss = {
            showDialog.value = false
        }
    )
    if (showDialog.value) {
        Alert(
            onDismissRequest = { showDialog.value = false },
            onClick = {
//                viewModel.confirmMessageRead()
                showDialog.value = false
            }
        )
    }
}



