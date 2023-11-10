package br.com.autotrac.testnanoclient.screens

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.NanoWebsocketClient.TAG
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.common.CustomAttachFile
import br.com.autotrac.testnanoclient.common.CustomTopAppBar
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import br.com.autotrac.testnanoclient.logger.AppLogger
import br.com.autotrac.testnanoclient.ui.theme.NanoClientKotlinTheme
import br.com.autotrac.testnanoclient.vm.FilePickerViewModel
import br.com.autotrac.testnanoclient.vm.FormListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.beans.PropertyChangeListener

/**
 * Tela de envio de mensagens.
 * @author Melina Minaya
 */
@Composable
fun SendMessageScreen(
    navigateToInbox: (Int, Boolean) -> Unit,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {
    val viewModel: FormListViewModel = viewModel()
    val senderAccess = MessageSenderAccess()
    var viewModelFilePicker: FilePickerViewModel = viewModel()
    val selectedFileString by viewModelFilePicker.fileProcessedString.observeAsState()
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf<String?>("") }
    var response by remember { mutableStateOf("") }
    val fileList = remember { mutableStateListOf<Uri>() }
    val propertyChangeListener = PropertyChangeListener { evt ->
        evt?.let {
            // Handle property changes here
            if (evt.propertyName == ApiEndpoints.SEND_MESSAGE) {
                val newValue = evt.newValue.toString()
                response = newValue.toDouble().toInt().toString()
            } else if (evt.propertyName == ApiEndpoints.SEND_FILE_MESSAGE) {
                val newValue = evt.newValue.toString()
                response = newValue
            }
        }
    }
    ObservableUtil.addPropertyChangeListener(propertyChangeListener)

    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
    }
    LaunchedEffect(selectedFileString) {
        selectedFileUri = selectedFileString
        selectedFileString?.let {
            //Enquanto mandar apenas um arquivo por ver manter o clear da lista.
            fileList.clear()
            fileList.add(it)
        }
    }
    val messageText = remember { mutableStateOf("") }
    val mascaras by viewModel.formList.observeAsState(emptyList())
    val defaultMascaraOption = "Mensagem Livre"

    val selectedMascara = remember { mutableStateOf(defaultMascaraOption) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Envio de Mensagem",
                navigateToLogs = { },
                popUpToLogin = popUpToLogin,
                onBackClick = { popBackStack() },
                isSocketOn = null,
                apiIcon = true
            ) {}
        }

    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            MascaraDropdownMenu(
                mascaras = mascaras,
                selectedMascara = selectedMascara.value
            ) { mascara ->
                messageText.value = mascara.definition
            }

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
                selectedFileName = {
                    selectedFileName = it
                },
                buttonSend = true,
                onSendMessage = {
                    coroutineScope.launch(Dispatchers.IO) {
                        val dbMessageProcessed =
                            messageOnPattern(messageText.value, selectedFileUri, selectedFileName)
//                       onSendMessage(dbMessageProcessed)
//                       val filePath = getFilePathFromUri(selectedFileUri!!)
                        try {
                            senderAccess.sendMessageToServer(
                                message = dbMessageProcessed,
                                context = context,
                                selectedFileUri
                            )
                            // e.g., call an API or perform any desired action
                            Log.d(TAG, "Sending message: $dbMessageProcessed")
                            AppLogger.log("Sending message: $dbMessageProcessed")
//                           if (response != null) {
//                               // Navigate to the inbox screen when the response is not null
//                               navigateToInbox(1, true)
//                           }
                            fileList.clear()
                        } catch (e: Exception) {
                            // Handle any exceptions that might occur during the suspend function call
                            // e.g., connection error, timeout, etc.
                            Log.e(TAG, "Failed to send message: $e")
                            AppLogger.log("Failed to send message: $e")
                            e.printStackTrace()
                        }
                    }
                    return@FilePicker response
                },
                navigateToInbox = navigateToInbox,
                snackbarHost = snackbarHostState,
                addFile = {
                    //TODO: send many files at the same time, then there will be no need to clear List.
                    fileList.clear()
                    if (it != null) {
                        fileList.add(it)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (response == null || response == "null" || response == "") "" else "Mensagem enviada nº:$response",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(color = Color.Black)
            )
            CustomAttachFile(fileList = fileList,
                deletedFileName = {
                    fileList.remove(it)
                }
            )
        }
    }
    fun getFilePathFromUri(uri: Uri): String? {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            filePath = cursor.getString(columnIndex)
            cursor.close()
        }
        return filePath
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
                navigateToInbox = { _, _ -> },
                popBackStack = {}
            ) {}
        }
    }
}

