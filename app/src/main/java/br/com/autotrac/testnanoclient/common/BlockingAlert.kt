package br.com.autotrac.testnanoclient.common

import android.os.Handler
import android.os.Looper
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp


@Composable
fun BlockingAlert(
    message: String,
    durationMillis: Long,
    dismissAction: () -> Unit
){
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {  },
            // state = dialogState,

            title = {
                if (message!= null){
                    Text(text = message, fontSize = 12.sp )
                }
            },
            confirmButton = {

            },
            dismissButton = {

            }
        )

        // Automatically dismiss the AlertDialog after the desired duration
        DisposableEffect(Unit) {
            val dismissHandler = Handler(Looper.getMainLooper())
            val dismissRunnable = Runnable {
                dismissAction()
                openDialog = false
            }
            dismissHandler.postDelayed(dismissRunnable, durationMillis)
            onDispose {
                dismissHandler.removeCallbacks(dismissRunnable)
            }
        }
    }

}