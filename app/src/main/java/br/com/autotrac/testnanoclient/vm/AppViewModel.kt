package br.com.autotrac.testnanoclient.vm

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.autotrac.testnanoclient.NanoWebsocketClient
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.common.showSnackbarSuspend
import br.com.autotrac.testnanoclient.consts.ApiConstants
import br.com.autotrac.testnanoclient.data.WebSocketConnectionCallback
import br.com.autotrac.testnanoclient.data.WebSocketDisconnectionCallback
import br.com.autotrac.testnanoclient.logger.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class AppViewModel(private val state: SavedStateHandle) : ViewModel() {
    // LiveData to observe WebSocket connection status
    private val _isApiOn = MutableLiveData<Boolean>()
    val isApiOn: LiveData<Boolean>
        get() = _isApiOn

    private val _logs = MutableStateFlow(emptyList<String>())
    val logs: Flow<List<String>> get() = _logs

    private val _isMobileCommunicatorOn = MutableLiveData<Boolean>(false)
    val isMobileCommunicatorOn: MutableLiveData<Boolean> get() = _isMobileCommunicatorOn

    private val _permissionState = MutableStateFlow<PermissionState>(PermissionState.NotRequested)
    val permissionState: StateFlow<PermissionState> = _permissionState


    init {
        // Initialize the WebSocket connection status from the SavedStateHandle
        _isApiOn.value = state.get<Boolean>("isApiOn") ?: false
        //When init grant this permission to call the intents below
    }

    fun onPermissionGranted() {
        // Perform actions when the permission is granted, e.g., call intents
        _permissionState.value = PermissionState.Granted
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

    fun setIsMobileCommunicatorOn(value: Boolean) {
        _isMobileCommunicatorOn.value = value
        ObservableUtil.attachProperty("isMobileCommunicatorOn", value)
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
        snackbarHostState: SnackbarHostState,
        coroutineScope: CoroutineScope,
        context: Context,
        callback: WebSocketConnectionCallback,
    ) {
        val thread = Thread {
            try {
                NanoWebsocketClient.connect()
                Thread.sleep(5000) //while isLoadingApiOn with BlockingAlert finishes
                if (NanoWebsocketClient.isWebSocketConnected()) {
                    coroutineScope.launch {
                        showSnackbarSuspend(
                            message = "Websocket conectado com sucesso",
                            actionLabel = true,
                            duration = SnackbarDuration.Short,
                            context = context,
                            snackbarHostState = snackbarHostState
                        )
                    }
                    NanoWebsocketClient.sendMessageFromClient()
                    Log.i(NanoWebsocketClient.TAG, "NanoWebSocket Started")
                    callback.onConnectionSuccess()

                } else {
                    coroutineScope.launch {
                        showSnackbarSuspend(
                            message = "Não foi possível conectar ao servidor",
                            actionLabel = true,
                            duration = SnackbarDuration.Short,
                            context = context,
                            snackbarHostState = snackbarHostState
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
        context: Context,
    ) {
        connectToWebSocketIn(
            snackbarHostState,
            coroutineScope,
            context,
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

    fun connectCommunicator(
        context: Context,
        coroutineScope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
    ) {
        when (permissionState.value) {
            PermissionState.Granted -> {
                val thread = Thread {
                    try {
                        val intent = Intent(ApiConstants.INTENT_SVC_START)
                        intent.setPackage(ApiConstants.INTENT_SVC_PACKAGE_NAME)
                        intent.putExtra(ApiConstants.INTENT_ACTION_NEED_KNOX, true)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(intent)
                        } else {
                            context.startService(intent)
                        }
                        Log.i(NanoWebsocketClient.TAG, "Mobile Communicator Started")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
//                                        isLoading = false
                    }
                }
                thread.start()
                coroutineScope.launch {
                    showSnackbarSuspend(
                        message = "Módulo de Comunicação Conectado",
                        actionLabel = true,
                        duration = SnackbarDuration.Short,
                        context = context,
                        snackbarHostState = snackbarHostState
                    )
                }
                setIsMobileCommunicatorOn(true)
            }

            else -> {
                coroutineScope.launch {
                    showSnackbarSuspend(
                        message = "Módulo de Comunicação não conectado. Permissão de intent negada.",
                        actionLabel = true,
                        duration = SnackbarDuration.Long,
                        context = context,
                        snackbarHostState = snackbarHostState
                    )
                }
                AppLogger.log("Permission QUERY_ALL_PACKAGES denied: ao iniciar comunicador")
                Log.i(
                    NanoWebsocketClient.TAG,
                    "Permission QUERY_ALL_PACKAGES denied: ao iniciar comunicador"
                )
            }
        }

    }

    fun disconnectCommunicator(
        context: Context,
        coroutineScope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
    ) {
        val thread = Thread {
            try {
                val intent = Intent(ApiConstants.INTENT_SVC_STOP)
                intent.setPackage(ApiConstants.INTENT_SVC_PACKAGE_NAME)
                intent.putExtra(ApiConstants.INTENT_ACTION_NEED_KNOX, true)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
                Log.i(NanoWebsocketClient.TAG, "Mobile Communicator Stopped")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        coroutineScope.launch {
            showSnackbarSuspend(
                message = "Módulo de Comunicação Desconectado",
                actionLabel = true,
                duration = SnackbarDuration.Short,
                context = context,
                snackbarHostState = snackbarHostState
            )
        }
        setIsMobileCommunicatorOn(false)
    }

    fun checkMobileCommunicator(): Boolean {
        val isMobCommOn = ObservableUtil.getValue("isMobileCommunicatorOn").toString().toBoolean()
        _isMobileCommunicatorOn.value = isMobCommOn
        return isMobCommOn
    }

    fun connectService(context: Context) {
        when (permissionState.value) {
            PermissionState.Granted -> {
                val thread = Thread {

                    try {
                        val intent = Intent(ApiConstants.INTENT_SVC_INITIALIZE)
                        intent.setPackage(ApiConstants.INTENT_SVC_PACKAGE_NAME)
                        intent.putExtra(ApiConstants.INTENT_ACTION_NEED_KNOX, true)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(intent)
                        } else {
                            context.startService(intent)
                        }
                        Log.i(NanoWebsocketClient.TAG, "Service Started")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                    }
                }
                thread.start()
            }

            else -> {
                Log.i(NanoWebsocketClient.TAG, "Permission QUERY_ALL_PACKAGES Denied")
                AppLogger.log("Permission QUERY_ALL_PACKAGES Denied: ao iniciar o serviço")
            }
        }

    }

    fun disconnectService(context: Context) {
        val thread = Thread {
            try {
                val intent = Intent(ApiConstants.INTENT_SVC_FINALIZE)
                intent.setPackage(ApiConstants.INTENT_SVC_PACKAGE_NAME)
                intent.putExtra(ApiConstants.INTENT_ACTION_NEED_KNOX, true)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
                Log.i(NanoWebsocketClient.TAG, "Service Stopped")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        setIsMobileCommunicatorOn(false)
        _isApiOn.value = false
    }
}