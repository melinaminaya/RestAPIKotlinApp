package br.com.autotrac.testnanoclient.common

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
    coroutineScope: CoroutineScope
) {
    DisposableEffect(Unit) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
        onDispose {  }

    }
}