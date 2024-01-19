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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.ApiObservableConsts
import br.com.autotrac.testnanoclient.ObservableUtil.addPropertyChangeListener
import br.com.autotrac.testnanoclient.ObservableUtil.removePropertyChangeListener
import br.com.autotrac.testnanoclient.common.Alert
import br.com.autotrac.testnanoclient.common.LoadingIcon
import br.com.autotrac.testnanoclient.common.MessageListComposable
import br.com.autotrac.testnanoclient.models.IntegrationMessage
import br.com.autotrac.testnanoclient.vm.MessageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.beans.PropertyChangeListener


@Composable
fun MessageListScreen() {

    val viewModel: MessageViewModel = viewModel()
    val showDialog = remember { mutableStateOf(false) }
    val messagesState by viewModel.messages.collectAsState()
    var messagesList by remember{ mutableStateOf(messagesState) }
    val clickedMessage = remember { mutableStateOf<IntegrationMessage?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val loadingState = remember { mutableStateOf(false) }
    val propertyChangeListener = PropertyChangeListener { evt ->
        evt?.let {
            // Handle property changes here
            if (evt.propertyName == ApiObservableConsts.MESSAGE_STATUS) {
                println("Message status changed: ${evt.newValue}")
                if (evt.newValue is List<*> && (evt.newValue as List<*>).size >= 2) {
                    val firstElement = (evt.newValue as List<*>)[0]
                    if (firstElement == 3.0) {
                        coroutineScope.launch {
                            loadingState.value = true
                            viewModel.fetchMessages()
                            loadingState.value = false
                            messagesList = messagesState.distinctBy { it.code }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
        messagesList = messagesState
    }
    LaunchedEffect(messagesState) {
        messagesList = messagesState
        removePropertyChangeListener(propertyChangeListener)
        addPropertyChangeListener(propertyChangeListener)
    }

    Box {
        when {
            loadingState.value -> {
                LoadingIcon(size = 50, 16)
            }
            messagesList.isEmpty() -> {
                // Empty state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Caixa de entrada vazia")
                }
            }

            else -> {
                MessageListComposable(
                    messages = messagesList,
                    onMessageDelete = { message ->
                        coroutineScope.launch {
                            viewModel.deleteMessage(message)
                            // Remove the deleted message from the list directly
                            loadingState.value = true
                            viewModel.fetchMessages()
                            delay(500)
                            loadingState.value = false
                        }
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
                            loadingState.value = true
                            viewModel.fetchMessages()
                            delay(500)
                            loadingState.value = false
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
                clickedMessage.value?.let { message ->
                    coroutineScope.launch {
                        // Mark the clicked message as read
                        viewModel.markMessageAsRead(message = message)
                        loadingState.value = true
                        viewModel.fetchMessages()
                        delay(500)
                        loadingState.value = false

                    }
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
            },
            showMarkAsRead = true
        )
    }
}


