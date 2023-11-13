package br.com.autotrac.testnanoclient.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.common.CustomTopAppBar
import br.com.autotrac.testnanoclient.common.DropdownCard
import br.com.autotrac.testnanoclient.common.LoadingIcon
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.models.LastPosition
import br.com.autotrac.testnanoclient.models.ParameterModel
import br.com.autotrac.testnanoclient.handlers.ParameterHandler
import br.com.autotrac.testnanoclient.handlers.ParseData
import br.com.autotrac.testnanoclient.vm.CheckListViewModel
import br.com.autotrac.testnanoclient.vm.CurrentDateViewModel
import br.com.autotrac.testnanoclient.vm.LastPositionViewModel
import br.com.autotrac.testnanoclient.vm.MctParamsViewModel
import br.com.autotrac.testnanoclient.ui.theme.NanoClientKotlinTheme

@Composable
fun CheckListScreen(
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {
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
            CustomTopAppBar(
                title = "CheckList",
                navigateToLogs = { },
                onBackClick = { popBackStack() },
                popUpToLogin = popUpToLogin,
                isSocketOn = null,
                apiIcon = true,
            ) {}
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            item {
                DropdownCard(title = ApiEndpoints.REQ_GET_CHECKLIST) {
                    CheckListCard(
                        content = checkList
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = ApiEndpoints.REQ_GET_CURRENT_DATE,
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
                DropdownCard(title = ApiEndpoints.REQ_GET_POSITION_LAST, content = {
                    PositionLastCard(
                        content = lastPosition
                    )
                })
            }
            item {
                DropdownCard(title = ApiEndpoints.REQ_POSITION_HISTORY_COUNT, content = {
                    PositionCountCard(
                        content = positionHistoryCount
                    )
                })
            }
            item {
                DropdownCard(title = ApiEndpoints.REQ_POSITION_HISTORY_LIST, content = {
                    if (positionHistoryList.isNullOrEmpty()) {
                        Text(
                            text = "No position history list.",
                            style = TextStyle(fontSize = 14.sp)
                        )
                    } else {
                        PositionHistoryListCard(
                            content = positionHistoryList,
                        )
                    }
                })
            }

            item {
                DropdownCard(title = ApiEndpoints.REQ_GET_MCT_PARAMETERS) {
                    MctParametersCard(
                        content = mctParams
                    )
                }
            }
            item {
                DropdownCard(title = ApiEndpoints.REQ_CONFIG_SERVICE_LOG) {
                    ConfigServiceLogCard(
                        onSaveClick = { enableLog, maxFileCount, maxFileSize ->
                            viewModel.sendConfigServiceLog(enableLog, maxFileCount, maxFileSize)
                        }
                    )
                }
            }
            item {
                DropdownCard(title = ApiEndpoints.REQ_FILE_OPERATION) {
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
fun MctParametersCard(content: List<ParameterModel>?) {
    if (content != null) {
        Spacer(modifier = Modifier.height(8.dp))
        content.forEach { param ->
            val paramName = ParameterHandler.convertMctParamNumber(param.number)
            Text(
                text = "$paramName: ${param.value}",
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}

@Composable
fun PositionLastCard(content: LastPosition?) {
    Spacer(modifier = Modifier.height(8.dp))
    if (content != null) {
        Text(
            text = "Code: ${content.code}",
            style = TextStyle(fontSize = 14.sp)
        )
        Text(
            text = "Altitude: ${content.altitude}",
            style = TextStyle(fontSize = 14.sp)
        )
        Text(
            text = "PositionTime: ${ParseData.convertFromTimeStamp(content.positionTime)}",
            style = TextStyle(fontSize = 14.sp)
        )

    } else {
        LoadingIcon(25, null)
    }
}

@Composable
fun PositionCountCard(content: String?) {
    Spacer(modifier = Modifier.height(8.dp))
    if (content != null) {
        Text(
            text = content,
            style = TextStyle(fontSize = 14.sp)
        )

    } else {
        LoadingIcon(25, null)
    }
}