package com.example.nanoclientkotlin.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.ObservableUtil.addPropertyChangeListener
import com.google.gson.Gson
import java.beans.PropertyChangeListener
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    id: Int,
    showDetails: Boolean,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
//    messageViewModel: (MessageViewModel) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Caixa de Entrada") },
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
        }
    ) { it ->

        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(5.dp))
            Text("Details: $showDetails", fontSize = 20.sp)

                MessageListScreen()

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
            InboxScreen(
                id = 7,
                showDetails = true,
                popBackStack = {},
                popUpToLogin = {},
                //messageViewModel = {}
            )
        }
    }
}