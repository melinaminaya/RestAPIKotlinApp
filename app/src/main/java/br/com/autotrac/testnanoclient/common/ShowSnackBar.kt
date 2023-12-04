package br.com.autotrac.testnanoclient.common

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ShowSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String,
    duration: SnackbarDuration,
    coroutineScope: CoroutineScope,
    actionLabel: Boolean,
    context: Context?,
) {
    DisposableEffect(Unit) {
        coroutineScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = duration,
                    actionLabel = if (actionLabel) "OK" else null
                )
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
        onDispose { }
    }
}

suspend fun showSnackbarSuspend(
    snackbarHostState: SnackbarHostState,
    message: String,
    context: Context?,
    actionLabel: Boolean,
    duration: SnackbarDuration,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = if (actionLabel) "OK" else null,
            duration = duration
        )
    } else {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}