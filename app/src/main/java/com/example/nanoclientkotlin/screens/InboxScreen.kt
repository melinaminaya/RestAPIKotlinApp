package com.example.nanoclientkotlin.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nanoclientkotlin.DbMessage
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.NanoWebsocketClient
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.ObservableUtil.addPropertyChangeListener
import com.example.nanoclientkotlin.ObservableUtil.removePropertyChangeListener
import com.example.nanoclientkotlin.R
import com.example.nanoclientkotlin.common.DefaultButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.beans.PropertyChangeListener
import androidx.compose.material.icons.filled.ExitToApp



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    id: Int,
    showDetails: Boolean,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit
) {

    val gson= Gson()
    val value = ObservableUtil.getValue("messageList")
    val jsonString = gson.toJson(value)
    val listType = object : TypeToken<MutableList<DbMessage>>() {}.type
    val messages:MutableList<DbMessage> =   gson.fromJson<MutableList<DbMessage>>(jsonString!!, listType)

    val scrollStateVertical = rememberScrollState()
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
        },
        content = { it ->

            Column(
                modifier = Modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(5.dp))
                Text("Details: $showDetails", fontSize = 20.sp)

                Column(
                    modifier = Modifier.fillMaxSize()
//            contentPadding = PaddingValues(vertical = 8.dp)
                        .verticalScroll(state = scrollStateVertical)
                ) {

                    messages.forEach { message ->
                        Card(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = if (message.codeForm == 0L) "Mensagem Livre" else message.codeForm.toString() ,
                                    fontWeight = FontWeight.Bold
                                )
//                        Text(text = message.subject)
                                Text(text = message.code.toString())
                            }
                        }
                    }
                }

            }
        })
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
                popUpToLogin = {}
            )
        }
    }
}