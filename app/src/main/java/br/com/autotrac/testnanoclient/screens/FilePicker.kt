package br.com.autotrac.testnanoclient.screens

import android.Manifest
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.R
import br.com.autotrac.testnanoclient.common.LoadingIcon
import br.com.autotrac.testnanoclient.common.ShowSnackbar
import br.com.autotrac.testnanoclient.consts.ActionValues
import br.com.autotrac.testnanoclient.models.IntegrationMessage
import br.com.autotrac.testnanoclient.vm.FilePickerViewModel
import br.com.autotrac.testnanoclient.vm.PermissionState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Composable de seleção de arquivos.
 * @author Melina Minaya
 */
@Composable
fun FilePicker(
    buttonSend: Boolean,
    onSendMessage: () -> String,
    selectedFileStringPicker: (Uri?) -> Unit,
    selectedFileName: (String) -> Unit,
    navigateToInbox: ((Int, Boolean) -> Unit)?,
    snackbarHost:SnackbarHostState?
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val viewModel: FilePickerViewModel = viewModel()

    val permissionState by viewModel.permissionState.collectAsState()
    val fileProcessed by viewModel.fileProcessed.collectAsState()

    var loadingScreen by remember { mutableStateOf(fileProcessed) }

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileString by remember { mutableStateOf<Uri?>(null) }
    var selectedFileNameString by remember { mutableStateOf<String>("") }

    val requiredPermission = Manifest.permission.READ_EXTERNAL_STORAGE

    var fileProcessedDeferred = remember { CompletableDeferred<Boolean>() }
    var showSnackbar by remember { mutableStateOf(false)}
    var responseSendMessage by remember { mutableStateOf("")}

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri ->
        selectedFileUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            coroutineScope.launch {
                viewModel.updatePermissionState(PermissionState.Granted)
            }
            filePickerLauncher.launch("*/*") // Launch the file picker when permission is granted
        } else {
            coroutineScope.launch {
                viewModel.updatePermissionState(PermissionState.Denied)
            }
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }
    LaunchedEffect(selectedFileUri) {
        if (selectedFileUri != null) {
            selectedFileString = viewModel.processFile(selectedFileUri, context.contentResolver)
            selectedFileStringPicker(selectedFileString)
        }
    }
    LaunchedEffect(selectedFileUri) {
        if (selectedFileUri != null) {
            selectedFileNameString = getFileNameFromUri(selectedFileUri!!, context)
            selectedFileName(selectedFileNameString)
        }
    }


    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                when (permissionState) {
                    PermissionState.Granted -> {
                        // Permission is already granted, launch the file picker
                        filePickerLauncher.launch("*/*")
                        Log.d("ExampleScreen", "Code requires permission")
                    }
                    PermissionState.NotRequested -> {
                        // Request the permission using the permission launcher
                        permissionLauncher.launch(requiredPermission)
                    }

                    else -> {
                        // Handle other states (PermissionState.Denied) if needed
                    }
                }
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_attach_file_24),
                contentDescription = "Attach File"
            )
        }
        if(buttonSend) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        loadingScreen = true
//                    fileProcessedDeferred.await() // Wait for the file processing to complete
                        try {
                            if (selectedFileUri != null) {
                                if (fileProcessed) {
                                   responseSendMessage = onSendMessage()
                                    if (responseSendMessage != null) {
                                        showSnackbar = true
                                        if (navigateToInbox != null) {
                                            loadingScreen = false
                                            navigateToInbox(1, true)

                                        }
                                    }
                                } else {
                                    fileProcessedDeferred.await()
                                }
                            } else {
                                responseSendMessage = onSendMessage()
                                if (responseSendMessage != null) {
                                    showSnackbar = true
                                    if (navigateToInbox != null) {
                                        loadingScreen = false
                                        navigateToInbox(1, true)

                                    }
                                }
                            }
//                            //TODO: Wait for the onSendMessage response OK to redirect.
//                            if (navigateToInbox != null) {
//                                navigateToInbox(1, true)
//                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Failed to Send", Toast.LENGTH_LONG).show()
                        } finally {
                            loadingScreen = false
                        }

                    }

                },
                modifier = Modifier
//                    .align(Alignment.End)
                    .padding(horizontal = 16.dp)
            ) {
                if (loadingScreen) {
                    // Show the circular progress indicator while loading is true
                    LoadingIcon(25)
                } else {
                    // Show the "Enviar" button text when loading is false
                    Text("Enviar")
                }
            }
        }
    }
    /**
     *  Esses métodos são executados após o recebimento da mudança para confirmação
     *  do salvamento em variável.
     *  São necessários em duplicidade.
     *  TODO: Verificar outro modo de executar esses métodos.
     */


    if (selectedFileUri != null) {
        // Call processFile when selectedFileUri is updated
        LaunchedEffect(selectedFileUri) {
            viewModel.processFile(selectedFileUri, context.contentResolver)
            selectedFileStringPicker(selectedFileUri)

        }
        LaunchedEffect(selectedFileUri) {
            selectedFileNameString = getFileNameFromUri(selectedFileUri!!, context)
            selectedFileName(selectedFileNameString)
        }
    }
    if(showSnackbar){
        if (snackbarHost != null) {
            ShowSnackbar(
                snackbarHostState = snackbarHost,
                message = "Mensagem de Resposta: $responseSendMessage",
                duration = SnackbarDuration.Short ,
                coroutineScope = coroutineScope
            )
        }
    }
}

/**
 * Transforma a messagem para o DataClasse de Transmissão [IntegrationMessage].
 * Caso de exemplo a mensagem é enviada como texto, portanto seta o [IntegrationMessage.msgSubtype] == 0.
 * Caso a mensagem seja enviada como byteArray (Binária), seta o [IntegrationMessage.msgSubtype] == 1.
 */
suspend fun messageOnPattern(value: String, selectedFileString: Uri?, selectedFileName: String?): IntegrationMessage {
    var isOutOfBandMessage = false
    if (selectedFileString != null) {
        isOutOfBandMessage = true
    }
    //Exemplo de envio em ByteArray.
//    val byteArray = value.toByteArray()

    return withContext(Dispatchers.Default) {
        IntegrationMessage(
            // Set the properties of the IntegrationMessage based on the input
            code = null,
            isForward = false,
            msgStatusNum = 1, //TO_SEND = 1
            msgSubtype = 0, // TEXT == 0 or BINARY == 1
            formCode = ActionValues.FormTypeValues.FILTER_DISABLED.toLong(),
            subject = "",
//        body = buildMessageBody(value, selectedFileUri),
            body = value.replace("\n", "\\"), // emptyString se valor for binário.
            bodyBytes = byteArrayOf(), //byteArray ?: byteArrayOf(),
            createdTime = null,
            sendReceivedTime = null,
            deliveryTime = null,
            gmn = null,
            isOutOfBandMsg = isOutOfBandMessage,
            outOfBandFilename = selectedFileName,
            outOfBandMsgStatusNum = null,
            sourceAddress = "",
            destAddress = "",
            lastStatusTime = null,
            msgPriority = 1, //NORMAL_PRIORITY
            replyGmn = null,
            positionLatitude = null,
            positionLongitude = null,
            positionTime = null,
            positionSpeed = null,
            positionHeading = null,
            positionAging = null,
            transmissionChannel = 16, // ANY_AVAIL_NETWORK
            transmittedChannel = null,
            transmissionType = 0, //DEFAULT_TRANS , if serialized SERIAL_TRANS
        )
    }
}
fun getFileNameFromUri(uri: Uri, context: Context): String {
    val contentResolver = context.contentResolver
    var cursor: Cursor? = null

    try {
        cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex != -1) {
                return cursor.getString(displayNameIndex)
            }
        }
    } finally {
        cursor?.close()
    }

    // If the cursor is null or the file name couldn't be retrieved, return a default value
    return "Unknown File"

}
