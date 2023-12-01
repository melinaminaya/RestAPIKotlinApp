package br.com.autotrac.testnanoclient.common

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

suspend fun ShowSnackbarBaptism(
    snackbarHostState: SnackbarHostState,
    message: String,
    context: Context,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = "OK",
            duration = SnackbarDuration.Long
        )
    } else {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
