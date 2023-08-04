package com.example.nanoclientkotlin.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.MessageSenderAccess
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import com.example.nanoclientkotlin.vm.FilePickerViewModel
import com.example.nanoclientkotlin.vm.FormListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessageScreen(
    navigateToInbox: (Int, Boolean) -> Unit,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
    ) {
    val viewModel: FormListViewModel = viewModel()
    val senderAccess = MessageSenderAccess()
    var viewModelFilePicker:FilePickerViewModel = viewModel()
    val selectedFileString by viewModelFilePicker.fileProcessedString.observeAsState()
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
    }
    LaunchedEffect(selectedFileString) {
        selectedFileUri = selectedFileString
    }
    val messageText= remember { mutableStateOf("") }
    val mascaras by viewModel.formList.observeAsState(emptyList())
    val selectedMascara: String = if (mascaras != null) {
        mascaras.firstOrNull().toString()
    }else{
        "Mensagem Livre"
    }
val context = LocalContext.current


    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Envio de Mensagem") },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = popUpToLogin) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Log Out")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

           MascaraDropdownMenu(mascaras = mascaras,
               selectedMascara = selectedMascara, onMascaraSelected = {mascara ->
                   messageText.value = mascara.code.toString()
               })

            Spacer(modifier = Modifier.height(16.dp))
            // Separate composable for the TextField
            MessageTextField(
                messageText = messageText,
                onMessageTextChanged = { messageText.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
           FilePicker(
               selectedFileStringPicker = {
                   selectedFileUri = it
               },
               onSendMessage = {
                   coroutineScope.launch(Dispatchers.IO) {
                       val dbMessageProcessed = messageOnPattern(messageText.value, selectedFileUri)
//                       onSendMessage(dbMessageProcessed)
                       try {
                           senderAccess.sendMessageToServer(message = dbMessageProcessed, context = context, selectedFileUri)
                           // e.g., call an API or perform any desired action
                           println("Sending message: $dbMessageProcessed")
                       } catch (e: Exception) {
                           // Handle any exceptions that might occur during the suspend function call
                           // e.g., connection error, timeout, etc.
                           Log.e(TAG, "Failed to send message: $e")
                           e.printStackTrace()
                       }
                   }
               },
               navigateToInbox = navigateToInbox
           )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NanoClientKotlinTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SendMessageScreen(
                navigateToInbox = { _,_ -> },
                popBackStack = {}
            ) {}
        }
    }
}

