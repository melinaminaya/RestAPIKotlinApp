package br.com.autotrac.testnanoclient.vm

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.autotrac.testnanoclient.NanoWebsocketClient
import br.com.autotrac.testnanoclient.data.WebSocketConnectionCallback
import br.com.autotrac.testnanoclient.data.WebSocketDisconnectionCallback
import br.com.autotrac.testnanoclient.logger.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class AppViewModel(private val state: SavedStateHandle) : ViewModel() {
    // LiveData to observe WebSocket connection status
    private val _isApiOn = MutableLiveData<Boolean>()
    val isApiOn: LiveData<Boolean>
        get() = _isApiOn

    private val _logs = MutableStateFlow(emptyList<String>())
    val logs: Flow<List<String>> get() = _logs

    init {
        // Initialize the WebSocket connection status from the SavedStateHandle
        _isApiOn.value = state.get<Boolean>("isApiOn") ?: false
    }

    fun startCheckingApiStatus() {
        viewModelScope.launch {
            while (true) {
                val isConnected = NanoWebsocketClient.isWebSocketConnected()
                if (isConnected != _isApiOn.value) {
                    _isApiOn.value = isConnected
                }
                delay(60000) // Check every 5 seconds (adjust as needed)
            }
        }
    }

    fun registerLogs() {
        viewModelScope.launch {
            while (true) {
                val fetchLogs: List<String> = AppLogger.getLogs()
                _logs.value = fetchLogs
                delay(1000)
            }
        }
    }

    private fun connectToWebSocketIn(
        snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope,
        callback: WebSocketConnectionCallback,
    ) {
        val thread = Thread {
            try {
                NanoWebsocketClient.connect()
                Thread.sleep(5000) //while isLoadingApiOn with BlockingAlert finishes
                if (NanoWebsocketClient.isWebSocketConnected()) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Websocket conectado com sucesso",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                    }
                    NanoWebsocketClient.sendMessageFromClient()
                    Log.i(NanoWebsocketClient.TAG, "NanoWebSocket Started")
                    callback.onConnectionSuccess()

                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Não foi possível conectar ao servidor",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                    }
                    Log.i(NanoWebsocketClient.TAG, "NanoWebSocket Disconnected")
                    callback.onConnectionFailure()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private fun disconnectWebsocketIn(callback: WebSocketDisconnectionCallback) {
        val thread = Thread {
            try {
                NanoWebsocketClient.disconnect()
                Log.i(NanoWebsocketClient.TAG, "NanoWebSocket Disconnected")
                callback.onDisconnectionSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                callback.onDisconnectionFailure()
            }
        }
        thread.start()
    }

    fun connectToWebSocket(
        snackbarHostState: SnackbarHostState,
        coroutineScope: CoroutineScope,
    ) {
        connectToWebSocketIn(
            snackbarHostState,
            coroutineScope,
            object : WebSocketConnectionCallback {
                override fun onConnectionSuccess() {
                    viewModelScope.launch {
                        _isApiOn.value = true
                    }
                }

                override fun onConnectionFailure() {
                    viewModelScope.launch {
                        _isApiOn.value = false
                    }
                }
            })
    }

    fun disconnectWebsocket() {
        disconnectWebsocketIn(object : WebSocketDisconnectionCallback {
            override fun onDisconnectionSuccess() {
                viewModelScope.launch {
                    _isApiOn.value = false
                }
            }

            override fun onDisconnectionFailure() {
                // Handle disconnection failure
            }
        })
    }

    // This method will be called when the ViewModel is about to be cleared (e.g., when the activity is destroyed)
    override fun onCleared() {
        super.onCleared()
        // Save the WebSocket connection status to the SavedStateHandle
        state["isApiOn"] = _isApiOn.value
//        state["isDisconnectionSuccessful"] = _isDisconnectionSuccessful
        //This is closing when viewModel is changed
//        disconnectWebsocket()
    }
}