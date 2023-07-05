package com.example.nanoclientkotlin.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun Alert(
    onDismissRequest: () -> Unit,
    onClick: () -> Unit
){

    //  val dialogState = remember { AlertDialogState() }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        // state = dialogState,
        title = {
            Text(text = "Modal Title")
        },
        text = {
            Text(text = "Modal Content")
        },
        confirmButton = {
            Button(
                onClick = onClick
            ) {
                Text(text = "Marcar como Lida")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest
            ) {
                Text(text = "Fechar")
            }
        }
    )
}