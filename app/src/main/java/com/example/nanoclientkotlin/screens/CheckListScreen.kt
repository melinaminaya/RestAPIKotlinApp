package com.example.nanoclientkotlin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.NanoClientKotlinTheme
import com.example.nanoclientkotlin.common.DropdownCard
import com.example.nanoclientkotlin.common.LoadingIcon
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.CheckList
import com.example.nanoclientkotlin.dataRemote.LastPosition
import com.example.nanoclientkotlin.dataRemote.ParameterModel
import com.example.nanoclientkotlin.dataRemote.PositionHistory
import com.example.nanoclientkotlin.vm.CheckListViewModel
import com.example.nanoclientkotlin.vm.CurrentDateViewModel
import com.example.nanoclientkotlin.vm.FormListViewModel
import com.example.nanoclientkotlin.vm.LastPositionViewModel
import com.example.nanoclientkotlin.vm.MctParamsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListScreen( query : (String?),
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DropdownCard(title = ConstsCommSvc.REQ_GET_CHECKLIST) {
                    CheckListCard(
                        content = checkList
                    )
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
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
                query = "checkList",
                popBackStack = {},
                popUpToLogin = {}
            )
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
        LoadingIcon()
    }
}@Composable
fun PositionCountCard(content: String?){
    Spacer(modifier = Modifier.height(8.dp))
    if(content!= null) {
        Text(
            text = content,
            style = TextStyle(fontSize = 14.sp)
        )

    }else{
        LoadingIcon()
    }
}