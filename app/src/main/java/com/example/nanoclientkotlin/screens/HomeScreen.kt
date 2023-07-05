package com.example.nanoclientkotlin.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.NanoWebsocketClient
import com.example.nanoclientkotlin.R
import com.example.nanoclientkotlin.common.ButtonTicker
import com.example.nanoclientkotlin.common.DefaultButton
import com.example.nanoclientkotlin.consts.ConstsCommSvc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToProfile: (Int, Boolean) -> Unit,
    navigateToSendMessage: (String) -> Unit,
    navigateToCheckList:(String) ->Unit,
    navigateToSearch: (String) -> Unit,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
   // messageViewModel: (MessageViewModel) -> Unit
) {
    val context = LocalContext.current
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
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = popUpToLogin) {
                        Icon(Icons.Filled.ExitToApp , contentDescription = "Log Out")
                    }
                }
            )
        },
        content = {innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center


            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DefaultButton(
                        text = "Inicia Serviço",
                        icon = R.drawable.baseline_start_24,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) {
                        val thread = Thread {
                            try {
//                                val intent = Intent(ConstsCommSvc.INTENT_SVC_INITIALIZE)
                                val intent = Intent(ConstsCommSvc.INTENT_SVC_START)
                                intent.setPackage(ConstsCommSvc.INTENT_SVC_PACKAGE_NAME)
                                intent.putExtra(ConstsCommSvc.INTENT_ACTION_NEED_KNOX, true)
                                context.startService(intent)
                                Log.i("NanoWebsocket", "NanoWebsoket Started")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        thread.start()
                    }
                    DefaultButton(
                        text = "Conecta à API",
                        icon = R.drawable.baseline_private_connectivity_24,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) {
                        val thread = Thread {
                            try {
                                NanoWebsocketClient.connect()
                                NanoWebsocketClient.sendMessageFromClient()
                                Log.i("NanoWebsocket", "NanoWebsoket Started")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        thread.start()
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonTicker(
                        text = "Caixa de Entrada",
                        icon = R.drawable.baseline_inbox_24,
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        ) { navigateToProfile(7, true) }

                    DefaultButton(
                        text = "Envio de Mensagem",
                        icon = R.drawable.baseline_send_24,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) { navigateToSendMessage("sendMessage") }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DefaultButton(
                        text = "CheckList",
                        icon = R.drawable.baseline_checklist_24,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) { navigateToCheckList("checkList") }
                    DefaultButton(
                        text = "Busca",
                        icon = R.drawable.baseline_manage_search_24,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) { navigateToSearch("liang moi") }
                }
            }
        }
    )
//    LaunchedEffect(Unit){
//        NanoWebsocketClient.getInstance().startSendingRequests()
//    }
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
                navigateToProfile = { _,_ -> },
                navigateToSendMessage = {},
                navigateToCheckList = {},
                navigateToSearch = {},
                popBackStack = {},
                popUpToLogin = {})
               // messageViewModel = {})
        }
    }
}