package com.example.nanoclientkotlin.screens

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import com.example.nanoclientkotlin.common.Alert
import com.example.nanoclientkotlin.common.LoadingIcon
import com.example.nanoclientkotlin.common.RedBackground
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.vm.MessageViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun MessageListScreen() {

    val viewModel: MessageViewModel = viewModel()
    val showDialog = remember { mutableStateOf(false) }
    val messagesState by viewModel.messages.observeAsState(emptyList())
    val messagesList = rememberSaveable{ mutableStateOf(messagesState) }
    val clickedMessage = remember { mutableStateOf<DbMessage?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
    }
    LaunchedEffect(messagesState){
        messagesList.value = messagesState
    }
    val coroutineScope = rememberCoroutineScope()
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

@Composable
fun MessageListComposable(
    messages: List<DbMessage>,
    onMessageDelete: (DbMessage) -> Unit,
    onMessageClick: (DbMessage) -> Unit,
    onDialogDismiss: () -> Unit,
    onRefresh:()->Unit,
)
{
    val sortedMessages = messages.sortedByDescending { it.createdTime }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val lazyListState = rememberLazyListState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = {
            onRefresh()
            swipeRefreshState.isRefreshing = false
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState
        ) {

            Log.d(TAG, "Passou no loop fora")
            items(sortedMessages) { message ->
                SwipeItem(message = message, onMessageDelete = {
                    onMessageDelete(message)
                },
                    onMessageClick = {
                        onMessageClick(message)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeItem(
    message: DbMessage,
    onMessageDelete: () ->Unit,
    onMessageClick:() ->Unit,
){
    val status = when (message.msgStatusNum) {
        0 -> "Sucesso"
        1 -> "A enviar"
        2 -> "Enviada"
        3 -> "Não Lida"
        4 -> "Lida"
        else -> "Not Processed"
    }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val formattedDate = dateFormat.format(message.createdTime)
    val hasTriedToDismiss = remember { mutableStateOf(false) }
    var hasConfirmedDismissal: Boolean by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(
        initialValue = DismissValue.Default,
        confirmValueChange =  {
            if (it == DismissValue.DismissedToStart) {
                hasTriedToDismiss.value = true

                hasConfirmedDismissal
            } else {
                false
            }
        }
    )
    val dismissedToEnd = dismissState.isDismissed(DismissDirection.StartToEnd)
    val dismissedToStart = dismissState.isDismissed(DismissDirection.EndToStart)
    val isDismissed = (dismissedToEnd || dismissedToStart)

    if (hasTriedToDismiss.value && !hasConfirmedDismissal) {
                onMessageDelete()
                hasConfirmedDismissal = true
    }
    val degrees by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
    )
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            RedBackground(degrees = degrees, onMessageDelete = { onMessageDelete() })

        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clickable { onMessageClick() },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = if (message.formCode == 0L) "Mensagem Livre" else message.formCode.toString(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = formattedDate, textAlign = TextAlign.End)
                    }
                    
                    Row {
                        Text(text = "Status: $status", textAlign = TextAlign.Start)
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = "Codigo: ${message.code}", textAlign = TextAlign.End)
                    }
                }
            }
        }
    )
}



