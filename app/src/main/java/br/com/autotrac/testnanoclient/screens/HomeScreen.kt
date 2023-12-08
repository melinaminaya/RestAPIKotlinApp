package br.com.autotrac.testnanoclient.screens


import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.R
import br.com.autotrac.testnanoclient.common.BlockingAlert
import br.com.autotrac.testnanoclient.common.ButtonTicker
import br.com.autotrac.testnanoclient.common.CustomAlert
import br.com.autotrac.testnanoclient.common.CustomTopAppBar
import br.com.autotrac.testnanoclient.common.DefaultButton
import br.com.autotrac.testnanoclient.common.ToggleCard
import br.com.autotrac.testnanoclient.vm.AppViewModel
import br.com.autotrac.testnanoclient.vm.ResetDatabaseViewModel
import br.com.autotrac.testnanoclient.ui.theme.NanoClientKotlinTheme
import br.com.autotrac.testnanoclient.ui.theme.OrangeDanger
import br.com.autotrac.testnanoclient.vm.HttpTestViewModel
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navigateToInbox: (Int, Boolean) -> Unit,
    navigateToSendMessage: (String) -> Unit,
    navigateToCheckList: (String) -> Unit,
    navigateToParameters: (String) -> Unit,
    navigateToHttpTest: (String) -> Unit,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
    // messageViewModel: (MessageViewModel) -> Unit
) {
    val context = LocalContext.current
    val viewModel: ResetDatabaseViewModel = viewModel()
    val httpViewModel = viewModel<HttpTestViewModel>()
    val appViewModel = viewModel<AppViewModel>()
    val resetDbResponse by viewModel.resetDatabaseResponse.observeAsState("")
    val isServiceOn = rememberSaveable { mutableStateOf(false) }
    val isCommunicatorOn by appViewModel.isMobileCommunicatorOn.observeAsState(false)
    var isMobileCommunicatorOn = rememberSaveable { mutableStateOf(isCommunicatorOn) }
    val showDialogService = rememberSaveable { mutableStateOf(false) }
    val isApiOnVal by appViewModel.isApiOn.observeAsState(false)
    var isApiOn by remember { mutableStateOf(isApiOnVal) }
    val isSocketOn by httpViewModel.isSocketOn.observeAsState(false)
    val showDialogApi = rememberSaveable { mutableStateOf(false) }
    val scope = CoroutineScope(Dispatchers.Main)
    var isLoadingServiceOn = remember { mutableStateOf(false) }
    var isLoadingServiceOff = remember { mutableStateOf(false) }
    var isLoadingApiOn = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        appViewModel.startCheckingApiStatus()
        isMobileCommunicatorOn.value = appViewModel.checkMobileCommunicator()
    }
    LaunchedEffect(isApiOnVal) {
        isApiOn = isApiOnVal
        if (isApiOn) {
            isServiceOn.value = isApiOn
        }
    }
    LaunchedEffect(isCommunicatorOn) {
        isMobileCommunicatorOn.value = isCommunicatorOn
    }
    if (isSocketOn) {
        isServiceOn.value = true
    }
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val requestPermissionLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // Permission granted, you can proceed with your logic
                    appViewModel.onPermissionGranted()
                } else {
                    // Permission denied, handle accordingly
                }
            }
        // Request the permission when the HomeScreen is recomposed
        DisposableEffect(Unit) {
            requestPermissionLauncher.launch(Manifest.permission.QUERY_ALL_PACKAGES)
            onDispose { /* clean-up, if needed */ }
        }
    }else{
        appViewModel.onPermissionGranted()
    }


    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = null,
                onBackClick = null,
                navigateToLogs = { },
                popUpToLogin = popUpToLogin,
                isSocketOn = isSocketOn,
                apiIcon = false,
            ) {}
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.fillMaxSize()
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //Inicialização do Serviço.
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    //TODO:Check if service is On.
                    ToggleCard(
                        isChecked = isServiceOn.value,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                isLoadingServiceOn.value = true
                                appViewModel.connectService(context)

                            } else {
                                isLoadingServiceOff.value = true
                                appViewModel.disconnectService(context)
                                isApiOn = false
                                httpViewModel.isSocketOn.value = false
                            }
                            isServiceOn.value = isChecked
                        },
                        text = "Serviço de Comunicação",
                        icon = R.drawable.baseline_start_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = Color(0x86FFFFFF).compositeOver(Color.White),
                    )
                }
            }
            //Inicializa o módulo de comunicação
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    //TODO:Check if service is On.
                    ToggleCard(
                        isChecked = isMobileCommunicatorOn.value,
                        onCheckedChange = { isChecked ->
                            isMobileCommunicatorOn.value = isChecked

                            if (isChecked) {
                                appViewModel.connectCommunicator(
                                    context,
                                    coroutineScope,
                                    snackbarHostState
                                )
                            } else {
                                appViewModel.disconnectCommunicator(
                                    context,
                                    coroutineScope,
                                    snackbarHostState
                                )
                            }
                        },
                        text = "Módulo de Comunicação",
                        icon = R.drawable.baseline_start_24,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = Color(0x86FFFFFF).compositeOver(Color.White),
                    )
                }
            }
            //Inicialização do servidor da API.
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ToggleCard(
                        isChecked = isApiOn,
                        onCheckedChange = { isChecked ->
                            if (isServiceOn.value) {
                                if (isChecked) {
                                    isLoadingApiOn.value = true
                                    appViewModel.connectToWebSocket(
                                        snackbarHostState,
                                        coroutineScope,
                                        context
                                    )
                                } else {
                                    appViewModel.disconnectWebsocket()
                                }
                            } else {
                                showDialogService.value = true
                            }

                        },

                        text = "API de Integração",
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
                        if (isApiOn) {
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
                        if (isApiOn) {
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
                        if (isApiOn) {
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
                        if (isApiOn) {
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
                        if (isApiOn) {
                            navigateToParameters("parameters")
                        } else {
                            showDialogApi.value = true
                        }

                    }
                    DefaultButton(
                        text = "Test",
                        icon = R.drawable.http_icon_foreground,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        color = null,
                    ) {
                        navigateToHttpTest("httpTest")
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
        if (isLoadingServiceOn.value) {
            BlockingAlert(
                message = "Aguarde o processo de inicialização...",
                durationMillis = 8000
            ) {
                isLoadingServiceOn.value = false
            }
        }
        if (isLoadingServiceOff.value) {
            BlockingAlert(
                message = "Aguarde o processo de finalização...",
                durationMillis = 19000
            ) {
                isLoadingServiceOff.value = false
            }
        }
        if (isLoadingApiOn.value) {
            BlockingAlert(message = "Conectando à Api...", durationMillis = 5000) {
                isLoadingApiOn.value = false
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
                navigateToInbox = { _, _ -> },
                navigateToSendMessage = {},
                navigateToCheckList = {},
                navigateToParameters = {},
                navigateToHttpTest = {},
                popBackStack = {}
            ) {}
            // messageViewModel = {})
        }
    }
}