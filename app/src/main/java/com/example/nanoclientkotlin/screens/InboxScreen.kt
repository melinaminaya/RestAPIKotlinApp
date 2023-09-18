package com.example.nanoclientkotlin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    selectedTab: Int,
    showDetails: Boolean,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
//    messageViewModel: (MessageViewModel) -> Unit
) {
    val selectedTabIndex = rememberSaveable { mutableStateOf(selectedTab) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mensagens") },
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

            val tabs = listOf("Inbox", "Outbox")
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary)
            ) {
                tabs.forEachIndexed { index, text ->
                    Tab(
                        text = { Text(text) },
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index }
                    )
                }
            }

            when (selectedTabIndex.value) {
                0 -> MessageListScreen()
                1 -> OutboxScreen()
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
            InboxScreen(
                selectedTab = 0,
                showDetails = true,
                popBackStack = {},
                popUpToLogin = {},
                //messageViewModel = {}
            )
        }
    }
}