package com.example.nanoclientkotlin.screens

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.NanoWebsocketClient.TAG
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.vm.FormListViewModel
import com.example.nanoclientkotlin.vm.MessageViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessageScreen(
    query : (String?),
    onSendMessage: (DbMessage) -> Unit,
    navigateToInbox: (Int, Boolean) -> Unit,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
    ) {
    val viewModel: FormListViewModel = viewModel()
    val messageText= remember { mutableStateOf(" ") }
//    val mascaras = listOf("Mensagem Livre","Mask 1", "Mask 2", "Mask 3")
    val mascaras by viewModel.formList.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var selectedMascara by remember { mutableStateOf(mascaras.firstOrNull()) }
    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Envio de Mensagem") },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = popUpToLogin) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Log Out")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(60.dp)
                    .padding(horizontal = 10.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = selectedMascara?.code?.toString() ?: "Mensagem Livre",
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(Color.LightGray),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Toggle Dropdown Menu"
                        )
                    }
                }
            }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    mascaras?.let { mascarasList ->
                        if (mascarasList.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text(text = "No options available") },
                                onClick = { expanded = false }
                            )
                        } else {
                            mascarasList.forEach { mascara ->
                                DropdownMenuItem(
                                    text = { Text(mascara.code.toString()) },
                                    onClick = {
                                        selectedMascara = mascara
                                        expanded = false
                                        messageText.value = mascara.code.toString()
                                    }
                                )
                            }
                        }
                    } ?: run {
                        DropdownMenuItem(
                            text = { Text(text = "No options available") },
                            onClick = { expanded = false }
                        )
                    }
                }



            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = messageText.value,
                onValueChange = { messageText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                label = { Text("Message") },
                textStyle = TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onSendMessage(messageOnPattern(messageText.value))
                            navigateToInbox(1, true)
                          },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 16.dp)
            ) {
                Text("Send")
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
            SendMessageScreen(
                onSendMessage = {},
                query = "sendMessage",
                navigateToInbox = { _,_ -> },
                popBackStack = {},
                popUpToLogin = {}
            )
        }
    }
}
fun messageOnPattern(value: String): DbMessage {

    return DbMessage(
        // Set the properties of the DbMessage based on the input
        code = null,
        isForward = false,
        msgStatusNum = 1 , //TO_SEND = 1
        subtype = 0, // It can be BINARY == 1 as well
        formCode = 0, //FILTER_DISABLED
        subject = "" ,
        body = value.replace("\n", "\\"),
        createdTime = null,
        sendReceivedTime = null,
        deliveryTime = null,
        gmn = null ,
        isOutOfBandMessage = false ,
        outOfBandFileName = null,
        outOfBandNumMsgStatus = null,
        sourceAddress ="" ,
        destAddress = "",
        lastStatusTime = null,
        msgPriority = 1 , //NORMAL_PRIORITY
        replyGmn = null,
        posLatitude = null,
        posLongitude = null ,
        positionTime = null,
        posSpeed = null,
        positionHeading = null,
        positionAging = null,
        transmissionChannel = 16, // ANY_AVAIL_NETWORK
        transmittedChannel = null,
        transmissionType = 0, //DEFAULT_TRANS , if serialized SERIAL_TRANS
    )
}