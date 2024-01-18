package br.com.autotrac.testnanoclient.handlers

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import br.com.autotrac.testnanoclient.ApiObservableConsts
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.common.showSnackbarSuspend
import kotlinx.coroutines.launch
import java.beans.PropertyChangeListener

@Composable
fun NotificationHandler(snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val propertyChangeListener = PropertyChangeListener { evt ->
        evt?.let {
            // Handle property changes here
            when(evt.propertyName){
                in ApiObservableConsts.allConstants -> {
                    val newValue = evt.newValue.toString()
                    scope.launch {
                        showSnackbarSuspend(
                            snackbarHostState,
                            message = "NOTIFICATION: ${evt.propertyName}, $newValue",
                            context = context,
                            actionLabel = false,
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
        }
    }
    ObservableUtil.addPropertyChangeListener(propertyChangeListener)

}