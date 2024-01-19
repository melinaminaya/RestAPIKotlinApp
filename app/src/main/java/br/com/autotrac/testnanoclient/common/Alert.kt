package br.com.autotrac.testnanoclient.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun Alert(
    onDismissRequest: () -> Unit,
    onClick: () -> Unit,
    title: String,
    content: (@Composable () -> Unit),
    showMarkAsRead: Boolean,
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        // state = dialogState,
        title = {
            Text(text = title)
        },
        text = {content()},
        confirmButton = {
            if (showMarkAsRead) {
                Button(
                    onClick = onClick
                ) {
                    Text(text = "Marcar como Lida")
                }
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
@Composable
fun CustomAlert(
    onDismissRequest: () -> Unit,
    title: String?,
    content:String,
    confirmBt:String,
    onClick: () -> Unit
){

    //  val dialogState = remember { AlertDialogState() }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        // state = dialogState,

        title = {
            if (title!= null){
                Text(text = title)
            }
        },
        text = {
            Text(text = content)
        },
        confirmButton = {
            Button(
                onClick = onClick
            ) {
                Text(text = confirmBt)
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