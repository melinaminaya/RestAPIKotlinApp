package br.com.autotrac.testnanoclient.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun BaptismStatusAlert(
    title: String,
    text: String,
    openDialog: Boolean,
    dismissAction: () -> Unit,
) {
    var openDialog by rememberSaveable { mutableStateOf(openDialog) }
    var counter by rememberSaveable { mutableStateOf(0) }
    var lastText by rememberSaveable { mutableStateOf(text) }

    if (text != lastText) {
        counter = 0
        lastText = text
        if ((lastText == "Batizada" ||
            lastText == "MCT não autorizado a se comunicar." ||
            lastText == "Houve timeout no processo de batismo.")
            && openDialog
        ) {
            // dismiss the AlertDialog
            openDialog = false
            lastText = ""
            dismissAction()
        }
    }
    LaunchedEffect(Unit) {
        while (openDialog) {
            delay(1000)
            counter++
        }
    }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = text, fontSize = 12.sp)
                    Text(text = "Tempo de Espera: $counter", fontSize = 12.sp)
                }
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    onClick = {
                        openDialog = false
                        lastText = ""
                        dismissAction()
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}