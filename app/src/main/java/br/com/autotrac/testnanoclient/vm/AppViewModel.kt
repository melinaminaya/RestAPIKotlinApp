package br.com.autotrac.testnanoclient.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.autotrac.testnanoclient.NanoWebsocketClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class AppViewModel:ViewModel() {
    var isApiOn by mutableStateOf(false)

    fun startCheckingApiStatus() {
        viewModelScope.launch {
            while (true) {
                val isConnected = NanoWebsocketClient.isWebSocketConnected()
                if (isConnected != isApiOn) {
                    isApiOn = isConnected
                }
                delay(60000) // Check every 5 seconds (adjust as needed)
            }
        }
    }

}