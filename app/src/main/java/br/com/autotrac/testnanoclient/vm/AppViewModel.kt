package br.com.autotrac.testnanoclient.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.autotrac.testnanoclient.NanoWebsocketClient
import br.com.autotrac.testnanoclient.logger.AppLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class AppViewModel:ViewModel() {
    var isApiOn by mutableStateOf(false)

    private val _logs = MutableStateFlow(emptyList<String>())
    val logs: Flow<List<String>> get() = _logs
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
    fun registerLogs(){
        viewModelScope.launch {
         while (true) {
             val fetchLogs: List<String> = AppLogger.getLogs()
             _logs.value = fetchLogs
             delay(1000)
         }
        }
    }

}