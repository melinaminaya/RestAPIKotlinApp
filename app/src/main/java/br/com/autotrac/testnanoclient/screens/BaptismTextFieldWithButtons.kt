package br.com.autotrac.testnanoclient.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.ObservableUtil.addPropertyChangeListener
import br.com.autotrac.testnanoclient.ObservableUtil.removePropertyChangeListener
import br.com.autotrac.testnanoclient.common.BaptismStatusAlert
import br.com.autotrac.testnanoclient.common.showSnackbarSuspend
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.consts.ApiResponses
import br.com.autotrac.testnanoclient.ui.theme.HighPriorityColor
import br.com.autotrac.testnanoclient.vm.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.beans.PropertyChangeListener

@Composable
fun BaptismTextFieldWithButtons(
    title: String,
    text: String?,
    baptismStatus: String,
    onTextChange: (String) -> Unit,
    onSaveClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    context: Context,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel: AppViewModel = viewModel()
    var enabled by rememberSaveable { mutableStateOf(false) }
    var editedText by rememberSaveable { mutableStateOf(text ?: "") }
    val focusRequester = remember { FocusRequester() }
    // Track the processing status with a mutableStateOf
    var processingStatus by rememberSaveable { mutableStateOf(false) }
    var setBaptismResponse by rememberSaveable { mutableStateOf("") }
    var alertOnCommunicatorState by remember { mutableStateOf(false) }
    val checkOn = viewModel.checkMobileCommunicator()

    fun checkResponse() {
        when (setBaptismResponse) {
            "" -> Unit // No action required if empty response
            ApiResponses.UC_NOT_ENABLE, ApiResponses.BAD_REQUEST, ApiResponses.ON_ERROR -> {
                processingStatus = false
//            onCancelClick() // If you want to cancel on these errors
                coroutineScope.launch {
                    showSnackbarSuspend(
                        snackbarHostState = snackbarHostState,
                        message = setBaptismResponse,
                        context = context,
                        actionLabel = true,
                        duration = SnackbarDuration.Long
                    )
                }
            }

            ApiResponses.OK -> {
                processingStatus = editedText != ""
            }

            else -> {
                Log.d(
                    "Processing Status",
                    "Unexpected response no Checkresponse: $setBaptismResponse"
                )
            }
        }
    }

    val propertyChangeListener = PropertyChangeListener { evt ->
        evt?.let {
            if (evt.propertyName == ApiEndpoints.SET_PARAM_WIFI_SSID) {
                val newValue = evt.newValue.toString()
                setBaptismResponse = newValue
                checkResponse()
            }
        }
    }

    LaunchedEffect(setBaptismResponse) {
        when (setBaptismResponse) {
            "" -> Unit // No action required if empty response
            ApiResponses.UC_NOT_ENABLE, ApiResponses.BAD_REQUEST, ApiResponses.ON_ERROR -> {
                processingStatus = false
//            onCancelClick() // If you want to cancel on these errors
                showSnackbarSuspend(
                    snackbarHostState = snackbarHostState,
                    message = setBaptismResponse,
                    context = context,
                    actionLabel = true,
                    duration = SnackbarDuration.Long
                )
            }

            ApiResponses.OK -> {
                if (editedText != "") {
                    processingStatus = true
                }
            }

            else -> {
                Log.d(
                    "Processing Status",
                    "Unexpected response no Checkresponse: $setBaptismResponse"
                )
            }
        }
    }
    DisposableEffect(context) {
        addPropertyChangeListener(propertyChangeListener)
        onDispose {
            removePropertyChangeListener(propertyChangeListener)
        }
    }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }
    // Update the isFocused state based on the focus state of the TextField
    SideEffect {
        CoroutineScope(Dispatchers.Main).launch {
            enabled = focusRequester.captureFocus()
        }
    }


    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = editedText,
            onValueChange = {
                val formattedText = it.replace(" ", "").uppercase()
                editedText = formattedText
                onTextChange(formattedText)
            },
            label = { Text(text = title, fontSize = 14.sp) },
            modifier = Modifier
                .focusRequester(focusRequester)
                .weight(1f),
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = {
                Text(
                    text = "Salve o SSID para batizar.",
                    color = androidx.compose.ui.graphics.Color.Gray,
                    fontSize = 12.sp
                )
            },
            trailingIcon = {
                if (enabled) {
                    IconButton(onClick = { editedText = "" }) {
                        Icon(
                            Icons.Default.Delete, contentDescription = "Clear",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

            }
        )

        Spacer(modifier = Modifier.width(8.dp))
        //Save Button
        if (enabled) {
            Button(
                onClick = {
                    if (checkOn) {
                        onSaveClick(editedText)
                        checkResponse()
                    } else {
                        alertOnCommunicatorState = true
                    }
                },
            ) {
                Text(text = "SALVAR", fontSize = 14.sp)

            }
        }
    }
    if (enabled) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Ao salvar sem SSID, efetuará o desbatismo.",
                fontSize = 12.sp,
                color = HighPriorityColor,
            )
        }
    }
    // Show an AlertDialog for processing status
    if (processingStatus) {
        BaptismStatusAlert(
            title = "Processando Batismo",
            text = baptismStatus,
            openDialog = processingStatus,
            dismissActionWithDeBaptism = {
                processingStatus = false
                if (it) {
                    editedText = ""
                    onCancelClick()
                }
            }
        )
    }
    if (alertOnCommunicatorState) {
        AlertDialog(
            onDismissRequest = { alertOnCommunicatorState = false },
            text = {
                Text(text = "Deseja ativar o módulo de comunicação para efetuar o batismo?")
            },
            confirmButton = {

                Button(
                    onClick = {
                        viewModel.connectCommunicator(context, coroutineScope, snackbarHostState)
                        alertOnCommunicatorState = false
                    }
                ) {
                    Text(text = "Sim")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        alertOnCommunicatorState = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
