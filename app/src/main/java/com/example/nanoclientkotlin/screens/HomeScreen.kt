package com.example.nanoclientkotlin.screens


import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.NanoWebsocketClient
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import com.example.nanoclientkotlin.OrangeDanger
import com.example.nanoclientkotlin.R
import com.example.nanoclientkotlin.common.ButtonTicker
import com.example.nanoclientkotlin.common.CustomAlert
import com.example.nanoclientkotlin.common.DefaultButton
import com.example.nanoclientkotlin.common.ToggleCard
import com.example.nanoclientkotlin.common.ToggleSwitch
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.vm.ResetDatabaseViewModel
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToInbox: (Int, Boolean) -> Unit,
    navigateToSendMessage: (String) -> Unit,
    navigateToCheckList:(String) ->Unit,
    navigateToParameters: (String) -> Unit,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
   // messageViewModel: (MessageViewModel) -> Unit
) {
    val context = LocalContext.current
    val viewModel: ResetDatabaseViewModel = viewModel()
    val resetDbResponse by viewModel.resetDatabaseResponse.observeAsState("")
    val isServiceOn = rememberSaveable{ mutableStateOf(false) }
    val showDialogService = rememberSaveable { mutableStateOf(false) }
    val isApiOn = rememberSaveable{ mutableStateOf(false) }
    val showDialogApi = rememberSaveable { mutableStateOf(false) }
    val checkedService = rememberSaveable { mutableStateOf(false) }
    val scope = CoroutineScope(Dispatchers.Main)
    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.image_size))
                                .padding(dimensionResource(id = R.dimen.padding_small)),
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null
                        )
                        Text(text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge)
                    }
                        },
                actions = {
                    IconButton(onClick = popUpToLogin) {
                        Icon(Icons.Filled.ExitToApp , contentDescription = "Log Out")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    ToggleCard(
                        isChecked = isServiceOn.value,
                        onCheckedChange = { isChecked ->
                            isServiceOn.value = isChecked

                            if (isChecked) {
                                val thread = Thread {
                                    try {
                                        val intent = Intent(ConstsCommSvc.INTENT_SVC_START)
                                        intent.setPackage(ConstsCommSvc.INTENT_SVC_PACKAGE_NAME)
                                        intent.putExtra(ConstsCommSvc.INTENT_ACTION_NEED_KNOX, true)
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            context.startForegroundService(intent)
                                        } else {
                                            context.startService(intent)
                                        }
                                        Log.i(TAG, "Service Started")
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                                thread.start()
                            } else {
                                val thread = Thread {
                                    try {
                                        val intent = Intent(ConstsCommSvc.INTENT_SVC_STOP)
                                        intent.setPackage(ConstsCommSvc.INTENT_SVC_PACKAGE_NAME)
                                        intent.putExtra(ConstsCommSvc.INTENT_ACTION_NEED_KNOX, true)
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            context.startForegroundService(intent)
                                        } else {
                                            context.startService(intent)
                                        }
                                        Log.i(TAG, "Service Stopped")
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                                thread.start()
                            }
                        },
                        text = "Inicia Serviço",
                        icon = R.drawable.baseline_start_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = Color(0x86FFFFFF).compositeOver(Color.White),
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ToggleCard(
                        isChecked = isApiOn.value,
                        onCheckedChange = { isChecked ->
                            if (isServiceOn.value) {
                                isApiOn.value = isChecked

                                if (isChecked) {
                                    val thread = Thread {
                                        try {
                                            NanoWebsocketClient.connect()
                                            NanoWebsocketClient.sendMessageFromClient()
                                            Log.i(TAG, "NanoWebSocket Started")
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                                    thread.start()
                                } else {
                                    val thread = Thread {
                                        try {
                                            NanoWebsocketClient.disconnect()
                                            Log.i(TAG, "NanoWebSocket Disconnected")
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                                    thread.start()
                                }
                            } else {
                                showDialogService.value = true

                            }

                        },

                        text = "Conecta à API",
                        icon = R.drawable.baseline_private_connectivity_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = Color(0x86FFFFFF).compositeOver(Color.White),
                    )

                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonTicker(
                        text = "Mensagens",
                        icon = R.drawable.baseline_inbox_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) {
                        if (isApiOn.value) {
                            navigateToInbox(0, true)
                        } else {
                            showDialogApi.value = true
                        }
                    }

                    DefaultButton(
                        text = "Envio de Mensagem",
                        icon = R.drawable.baseline_send_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = null,
                    ) {
                        if (isApiOn.value) {
                            navigateToSendMessage("sendMessage")
                        } else {
                            showDialogApi.value = true
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DefaultButton(
                        text = "CheckList",
                        icon = R.drawable.baseline_checklist_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = null,
                    ) {
                        if (isApiOn.value) {
                            navigateToCheckList("checkList")
                        } else {
                            showDialogApi.value = true
                        }

                    }
                    DefaultButton(
                        text = "Reset Database",
                        icon = if (resetDbResponse != null && resetDbResponse == NanoHTTPD.Response.Status.OK.toString())
                            R.drawable.baseline_check_24 else R.drawable.baseline_delete_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = OrangeDanger,
                    ) {
                        if (isApiOn.value) {
                            scope.launch {
                                viewModel.fetchResetDb()
                            }
                        } else {
                            showDialogApi.value = true
                        }


                    }
                }
            }
            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DefaultButton(
                        text = "Parâmetros",
                        icon = R.drawable.baseline_display_settings_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = null,
                    ) {
                        if (isApiOn.value) {
                            navigateToParameters("parameters")
                        } else {
                            showDialogApi.value = true
                        }

                    }
                    DefaultButton(
                        text = "Disabled",
                        icon = if (resetDbResponse != null && resetDbResponse == NanoHTTPD.Response.Status.OK.toString())
                            R.drawable.baseline_check_24 else R.drawable.baseline_delete_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = null,
                    ) {
                        if (isApiOn.value) {
                            scope.launch {
//                            viewModel.fetchResetDb()
                            }
                        } else {
                            showDialogApi.value = true
                        }


                    }
                }
            }


        }

        if (showDialogService.value) {
            CustomAlert(
                onDismissRequest = { showDialogService.value = false },
                title = null,
                content = "Please initialize Autotrac Service",
                confirmBt = "OK"
            ) {
                showDialogService.value = false
            }
        }
        if (showDialogApi.value) {
            CustomAlert(
                onDismissRequest = { showDialogApi.value = false },
                title = null,
                content = "Please initialize Autotrac Api",
                confirmBt = "OK"
            ) {
                showDialogApi.value = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NanoClientKotlinTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                navigateToInbox = { _,_ -> },
                navigateToSendMessage = {},
                navigateToCheckList = {},
                navigateToParameters = {},
                popBackStack = {},
                popUpToLogin = {})
               // messageViewModel = {})
        }
    }
}