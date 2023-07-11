package com.example.nanoclientkotlin.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import com.example.nanoclientkotlin.common.Alert
import com.example.nanoclientkotlin.common.RedBackground
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.vm.MessageViewModel


@Composable
fun MessageListScreen() {

    val viewModel: MessageViewModel = viewModel()
    val showDialog = remember { mutableStateOf(false) }
    val messagesState by viewModel.messages.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
    }

    MessageListComposable(
        messages = messagesState,
        onMessageDelete = { message ->
            viewModel.deleteMessage(message)
        },
        onMessageClick = { message ->
            showDialog.value = true
            viewModel.markMessageAsRead(message)
        },
        onDialogDismiss = {
            showDialog.value = false
        }
    )
    if (showDialog.value) {
        Alert(
            onDismissRequest = { showDialog.value = false },
            onClick = {
                viewModel.confirmMessageRead()
                showDialog.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageListComposable(
    messages: List<DbMessage>,
    onMessageDelete:( DbMessage) -> Unit,
    onMessageClick: (DbMessage) -> Unit,
    onDialogDismiss: () -> Unit)
{
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Log.d(TAG, "Passou no loop fora")
        items(messages) { message ->
//            MessageItem(
//                message = message,
//                onMessageDelete = onMessageDelete,
//                onMessageClick = onMessageClick
//            )
            val status = when (message.msgStatusNum) {
                0 -> "Sucesso"
                1 -> "A enviar"
                2 -> "Enviada"
                3 -> "Não Lida"
                4 -> "Lida"
                else -> "Not Processed"
            }

            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                onMessageDelete(message)
            }

            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    RedBackground(degrees = degrees)

                },
                dismissContent = {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .clickable { onMessageClick(message) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (message.formCode == 0L) "Mensagem Livre" else message.formCode.toString(),
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                Text(text = "Status: $status")
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(text = "Codigo: ${message.code}")
                            }
                        }
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageItem(message: DbMessage,
                onMessageDelete: (DbMessage) -> Unit,
                onMessageClick: (DbMessage) -> Unit
) {
    val status = when (message.msgStatusNum) {
        0 -> "Sucesso"
        1 -> "A enviar"
        2 -> "Enviada"
        3 -> "Não Lida"
        4 -> "Lida"
        else -> "Not Processed"
    }

    val dismissState = rememberDismissState()
    val dismissDirection = dismissState.dismissDirection
    val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

    if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
        onMessageDelete(message)
    }

    val degrees by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
                RedBackground(degrees = degrees)

        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clickable { onMessageClick(message) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (message.formCode == 0L) "Mensagem Livre" else message.formCode.toString(),
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        Text(text = "Status: $status")
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = "Codigo: ${message.code}")
                    }
                }
            }
        }
    )
}



