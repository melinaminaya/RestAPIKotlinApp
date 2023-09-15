package com.example.nanoclientkotlin.screens

import android.graphics.Color
import android.net.Uri
import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.common.DropDownToSet
import com.example.nanoclientkotlin.common.DropdownCard
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.dataRemote.RequestObject
import com.example.nanoclientkotlin.vm.HttpTestViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Tela de retorno de todas as requisições via HTTP.
 * @author Melina Minaya
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HttpTestScreen(
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {
    val viewModel: HttpTestViewModel = viewModel()
    val requestResponse by viewModel.responseRequest.observeAsState(initial = "")

    val listOfOptions = ConstsCommSvc.requestsList + ConstsCommSvc.parametersList +
            listOf(
                ConstsCommSvc.SEND_MESSAGE ,
                ConstsCommSvc.SEND_FILE_MESSAGE
            )


    var optionsOperation by rememberSaveable {
        mutableStateOf(listOfOptions[1])
    }
    var selectedOption:Int by rememberSaveable {
        mutableStateOf(1)
    }
    var param1 by remember { mutableStateOf("") }
    var param2 by remember { mutableStateOf("") }
    var param3 by remember { mutableStateOf("") }
    var param4 by remember { mutableStateOf("") }
    fun updateParams(newParam1: String, newParam2: String, newParam3: String, newParam4: String) {
        param1 = newParam1
        param2 = newParam2
        param3 = newParam3
        param4 = newParam4
    }
    val requestObject= RequestObject(param1 = param1, param2 = param2, param3 = param3, param4 =  param4)
    val scope = rememberCoroutineScope()
    var parsedMessage: String? = null
    val gson: Gson = Gson()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Requisições Http") },
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Column (modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    DropDownToSet(
                        title = "Opções de requisições",
                        previousText = optionsOperation,
                        onTextChange = { selectedOption = it.toInt() },
                        textStatus = optionsOperation,
                        dropdownItems = listOfOptions
                    )
                }
            }
            if (ConstsCommSvc.listOfFilteredRequests.contains(listOfOptions[selectedOption])) {
                item {
                    filterSelectionBox(
                        optionSelected = listOfOptions[selectedOption],
                        param1,
                        param2,
                        param3,
                        param4
                    ) { newParam1, newParam2, newParam3, newParam4 ->
                        updateParams(newParam1, newParam2, newParam3, newParam4)
                    }
                }
            }
            item {
                Row (modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.fetchRequest(
                                    listOfOptions[selectedOption],
                                    requestObject, context
                                )
                            }
                        },
                    ) {
                        Text("Send Request")
                    }
                }
            }
            item {
                Text(
                    text = requestResponse,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = TextStyle(color = androidx.compose.ui.graphics.Color.Black)
                )
            }
        }
    }

}



