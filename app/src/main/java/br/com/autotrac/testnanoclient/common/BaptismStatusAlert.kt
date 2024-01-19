package br.com.autotrac.testnanoclient.common

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.autotrac.testnanoclient.NanoWebsocketClient.TAG
import br.com.autotrac.testnanoclient.consts.ApiConstants
import kotlinx.coroutines.delay

@Composable
fun BaptismStatusAlert(
    title: String,
    text: String,
    openDialog: Boolean,
    dismissActionWithDeBaptism: (Boolean) -> Unit,
) {
    var openDialogInternal by rememberSaveable { mutableStateOf(openDialog) }
    var counter by rememberSaveable { mutableStateOf(0) }
    var lastText by rememberSaveable { mutableStateOf(text) }
    val updatedTextState = rememberUpdatedState(text)


    if (text != lastText) {
        counter = 0
        lastText = text
        if (((lastText == "Batizada") ||
                    (lastText == "MCT não autorizado a se comunicar.") ||
                    (lastText == "Houve timeout no processo de batismo.")) && openDialogInternal
        ) {
            // dismiss the AlertDialog
            openDialogInternal = false
            lastText = ""
            dismissActionWithDeBaptism(false)
        }
    }
    LaunchedEffect(updatedTextState.value, openDialogInternal) {
        while (openDialogInternal) {
            delay(1000)
            counter++
        }
    }
    LaunchedEffect(openDialog) {
        openDialogInternal = openDialog
        Log.d("Processing Status", "openDialogInternal: $openDialogInternal")
    }


    if (openDialogInternal) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = updatedTextState.value, fontSize = 12.sp)
                    Text(text = "Tempo de Espera: $counter", fontSize = 12.sp)
                }
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    onClick = {
                        openDialogInternal = false
                        lastText = ""
                        dismissActionWithDeBaptism(true)
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}