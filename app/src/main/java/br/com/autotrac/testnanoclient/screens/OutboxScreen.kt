package br.com.autotrac.testnanoclient.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.common.Alert
import br.com.autotrac.testnanoclient.common.LoadingIcon
import br.com.autotrac.testnanoclient.common.MessageListComposable
import br.com.autotrac.testnanoclient.models.IntegrationMessage
import br.com.autotrac.testnanoclient.vm.MessageViewModel
import kotlinx.coroutines.launch


@Composable
fun OutboxScreen() {

    val viewModel: MessageViewModel = viewModel()
    val showDialog = remember { mutableStateOf(false) }
    val messagesState by viewModel.outboxMessages.observeAsState(null)
    val messagesList = rememberSaveable{ mutableStateOf(messagesState) }
    var clickedMessage = remember { mutableStateOf<IntegrationMessage?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchOutboxMessages()
    }
    LaunchedEffect(messagesState){
        messagesList.value = messagesState
    }

    Box {
        when {
            messagesState == null -> {
                LoadingIcon(size = 50, 16)
            }

            messagesState!!.isEmpty() -> {
                // Empty state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Caixa de saída vazia")
                }
            }

            else -> {
                messagesList.value?.let {
                    MessageListComposable(
                        messages = it,
                        onMessageDelete = { message ->
                            viewModel.deleteMessageOutbox(message)
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
                                viewModel.fetchOutboxMessages()
                            }
                        }
                    )
                }
            }
        }
        if (showDialog.value) {
            Alert(
                onDismissRequest = { showDialog.value = false },
                onClick = {
//                viewModel.confirmMessageRead()
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
                },
                showMarkAsRead = false,
            )
        }

    }
}



