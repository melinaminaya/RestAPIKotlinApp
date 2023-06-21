package com.example.nanoclientkotlin.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.nanoclientkotlin.ItemClickHandler
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.NanoTopAppBar
import com.example.nanoclientkotlin.NanoWebsocketClient
import com.example.nanoclientkotlin.common.DefaultButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToProfile: (Int, Boolean) -> Unit,
    navigateToSearch: (String) -> Unit,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {
    Scaffold(
        topBar = {
            NanoTopAppBar()
        },
        content = {innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


            ) {
                //Text("Nano App", fontSize = 40.sp)
                DefaultButton(
                    text = "Conecta à API",
                    onClick = {
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
                )
                DefaultButton(
                    text = "Caixa de Entrada",
                    onClick = { navigateToProfile(7, true) }
                )

                DefaultButton(
                    text = "Busca",
                    onClick = { navigateToSearch("liang moi") }
                )

                DefaultButton(
                    text = "Volta",
                    onClick = popBackStack
                )

                DefaultButton(
                    text = "Log Out",
                    onClick = popUpToLogin
                )
            }
        }
    )
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
                navigateToSearch = {},
                popBackStack = {},
                popUpToLogin = {})
        }
    }
}