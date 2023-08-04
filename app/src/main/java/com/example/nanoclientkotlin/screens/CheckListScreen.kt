package com.example.nanoclientkotlin.screens

import android.content.Context
import android.os.Environment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.common.DropDownToSet
import com.example.nanoclientkotlin.common.DropdownCard
import com.example.nanoclientkotlin.common.LoadingIcon
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.consts.ParameterValues
import com.example.nanoclientkotlin.dataRemote.CheckList
import com.example.nanoclientkotlin.dataRemote.LastPosition
import com.example.nanoclientkotlin.dataRemote.ParameterModel
import com.example.nanoclientkotlin.dataRemote.PositionHistory
import com.example.nanoclientkotlin.handlers.ParameterHandler
import com.example.nanoclientkotlin.vm.CheckListViewModel
import com.example.nanoclientkotlin.vm.CurrentDateViewModel
import com.example.nanoclientkotlin.vm.LastPositionViewModel
import com.example.nanoclientkotlin.vm.MctParamsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListScreen(
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
){
    val viewModel: CheckListViewModel = viewModel()
    val checkList by viewModel.checkList.observeAsState(emptyList())
    val currentDateViewModel: CurrentDateViewModel = viewModel()
    val currentDate by currentDateViewModel.currentDate.observeAsState("")
    val lastPositionViewModel: LastPositionViewModel = viewModel()
    val lastPosition by lastPositionViewModel.lastPosition.observeAsState<LastPosition>()
    val positionHistoryCount by lastPositionViewModel.positionHistoryCount.observeAsState("")
    val positionHistoryList by lastPositionViewModel.positionHistoryList.observeAsState(emptyList())
    val mctParamsViewModel: MctParamsViewModel = viewModel()
    val mctParams by mctParamsViewModel.mctParams.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchCheckList()
        currentDateViewModel.fetchCurrentDate()
        mctParamsViewModel.fetchData()
        lastPositionViewModel.fetchPositionLast()
        lastPositionViewModel.fetchPositionHistoryCount()
        lastPositionViewModel.fetchPositionHistoryList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "CheckList") },
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
        ) {
            item {
                DropdownCard(title = ConstsCommSvc.REQ_GET_CHECKLIST) {
                    CheckListCard(
                        content = checkList
                    )
                }
            }

            item {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = ConstsCommSvc.REQ_GET_CURRENT_DATE,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentDate,
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                }
            }
            item {
                DropdownCard(title = ConstsCommSvc.REQ_GET_POSITION_LAST, content = {
                    PositionLastCard(
                        content = lastPosition
                    )
                })
            }
            item {
                DropdownCard(title = ConstsCommSvc.REQ_POSITION_HISTORY_COUNT, content = {
                    PositionCountCard(
                        content = positionHistoryCount
                    )
                })
            }
            item {
                DropdownCard(title = ConstsCommSvc.REQ_POSITION_HISTORY_LIST, content = {
                    PositionHistoryListCard(
                        content = positionHistoryList
                    )
                })
            }

            item {
                DropdownCard(title = ConstsCommSvc.REQ_GET_MCT_PARAMETERS) {
                    MctParametersCard(
                        content = mctParams
                    )
                }
            }
            item {
                DropdownCard(title = ConstsCommSvc.REQ_CONFIG_SERVICE_LOG) {
                    ConfigServiceLogCard(
                       onSaveClick = { enableLog, maxFileCount, maxFileSize ->
                           viewModel.sendConfigServiceLog(enableLog, maxFileCount, maxFileSize)
                       }
                    )
                }
            }
            item {
                DropdownCard(title = ConstsCommSvc.REQ_FILE_OPERATION) {
                    FileOperationCard(onSaveClick = { files, options, destination, timeoutMs ->
                        viewModel.sendFileOperation(files, options, destination, timeoutMs)
                    })
                }
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
            CheckListScreen(
                popBackStack = {}
            ) {}
        }
    }
}


@Composable
fun FileOperationCard(
    onSaveClick: (Int, Int, String, Int) -> Unit
){
    val listOfOptions = listOf(
        ParameterHandler.convertFileOperationParam2(ParameterValues.FileOperationParam2.NO_OPTIONS),
        ParameterHandler.convertFileOperationParam2(ParameterValues.FileOperationParam2.COPY_FILES),
        ParameterHandler.convertFileOperationParam2(ParameterValues.FileOperationParam2.ZIP_COMPRESSION),
    )
    val pathDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
    var filesOperation by rememberSaveable {
        mutableStateOf(2)
    }
    var optionsOperation by rememberSaveable {
        mutableStateOf(listOfOptions[1])
    }
    var selectedOption:Int? by rememberSaveable {
        mutableStateOf(null)
    }
    var destinationOperation by rememberSaveable {
        mutableStateOf("$pathDir/AutotracMobile/logs.zip")
    }
    var timeoutMs by rememberSaveable {
        mutableStateOf(10000)
    }
    Column(modifier = Modifier.padding(16.dp)) {

        Text("Files: Mapa de bits ")

        TextField(
            value = filesOperation.toString(),
            onValueChange = { filesOperation = it.toInt() },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            maxLines = 1
        )


        Spacer(modifier = Modifier.height(16.dp))

        /**
         * COPY = 1, none=0, Zip = 2
         */

        DropDownToSet(
            title = "Options: cópia, zip ou nenhuma. ",
            previousText = optionsOperation,
            onTextChange = { selectedOption = it.toInt()},
            textStatus = optionsOperation ,
            dropdownItems = listOfOptions
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Destination: diretório de destino.")
        TextField(
            value = destinationOperation,
            onValueChange = { destinationOperation = it },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Timeout em MS: indica quanto deve aguardar até o fim da operação.")
        TextField(
            value = timeoutMs.toString(),
            onValueChange = {
                timeoutMs = it.toInt()},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSaveClick(filesOperation,selectedOption ?: 1,destinationOperation , timeoutMs.toInt()) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Salvar")
        }
    }

}
@Composable
fun ConfigServiceLogCard(
    onSaveClick: (Boolean, Int, Long) -> Unit
){
    val context = LocalContext.current.applicationContext
    val sharedPreferences = context.getSharedPreferences("ConfigState", Context.MODE_PRIVATE)
    var enableLog by rememberSaveable {
        mutableStateOf(sharedPreferences.getBoolean("enableLog", true))
    }
    var maxFileSize by rememberSaveable {
        mutableStateOf(sharedPreferences.getString("maxFileSize", "") ?: "")
    }
    var maxFileCount by rememberSaveable {
        mutableStateOf(sharedPreferences.getString("maxFileCount", "") ?: "")
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Service Log")
            Switch(
                checked = enableLog,
                onCheckedChange = { enableLog = it }
            )
           
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Max File Size")
            TextField(
                value = maxFileSize,
                onValueChange = { maxFileSize = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Max File Count")
            TextField(
                value = maxFileCount,
                onValueChange = { maxFileCount = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val editor = sharedPreferences.edit()
                editor.putBoolean("enableLog", enableLog)
                editor.putString("maxFileSize", maxFileSize)
                editor.putString("maxFileCount", maxFileCount)
                editor.apply()

//                val maxFileCountValue = maxFileCount.toIntOrNull() ?: 0
//                val maxFileSizeValue = maxFileSize.toLongOrNull() ?: 0L
                onSaveClick(enableLog, maxFileCount.toInt(), maxFileSize.toLong())
                      },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Salvar")
        }
    }

}
@Composable
fun PositionHistoryListCard(content:List<PositionHistory>?){
    if (content != null) {
        Spacer(modifier = Modifier.height(8.dp))
        content.forEach { contentItem ->
            Text(
                text = contentItem.toString(),
                style = TextStyle(fontSize = 14.sp)
            )
        }
    } else {
        Text(
            text = "No position history list.",
            style = TextStyle(fontSize = 14.sp)
        )
    }
}
@Composable
fun MctParametersCard(content: List<ParameterModel>?){
    if (content!= null){
        Spacer(modifier = Modifier.height(8.dp))
       content.forEach { param ->
            Text(
                text = "Param Type: ${param.type}",
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "${param.number}: ${param.value}",
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}
@Composable
fun CheckListCard(content: List<CheckList>?){
    if (content!=null){
        Spacer(modifier = Modifier.height(8.dp))
        content.forEach { item ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cellular Status and Signal: ${item.cellularStatus} - ${item.cellularSignalLevel}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Satellite Status and Signal: ${item.hasSatelliteSignal} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Wifi Status: ${item.wifiStatus} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Last GPS Position Date: ${item.lastGpsPosDate} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Service Version: ${item.serviceVersion} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Interface Version: ${item.interfaceVersion} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "DatabaseVersion: ${item.databaseVersion} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "SO System Version: ${item.soSystemVersion} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }

}
@Composable
fun PositionLastCard(content: LastPosition?){
    Spacer(modifier = Modifier.height(8.dp))
    if(content!= null) {
        Text(
            text = "Code: ${content.code}",
            style = TextStyle(fontSize = 14.sp)
        )
        Text(
            text = "Altitude: ${content.altitude}",
            style = TextStyle(fontSize = 14.sp)
        )
        Text(
            text = "PositionTime: ${content.positionTime}",
            style = TextStyle(fontSize = 14.sp)
        )

    }else{
        LoadingIcon(25)
    }
}
@Composable
fun PositionCountCard(content: String?){
    Spacer(modifier = Modifier.height(8.dp))
    if(content!= null) {
        Text(
            text = content,
            style = TextStyle(fontSize = 14.sp)
        )

    }else{
        LoadingIcon(25)
    }
}