package br.com.autotrac.testnanoclient.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.common.CustomTopAppBar
import br.com.autotrac.testnanoclient.common.DropDownToSet
import br.com.autotrac.testnanoclient.common.LoadingIcon
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.requestObjects.RequestObject
import br.com.autotrac.testnanoclient.handlers.EndpointsLists
import br.com.autotrac.testnanoclient.ui.theme.responseHttpColors
import br.com.autotrac.testnanoclient.vm.HttpTestViewModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Tela de retorno de todas as requisições via HTTP.
 * @author Melina Minaya
 */
@Composable
fun HttpTestScreen(
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {
    val viewModel: HttpTestViewModel = viewModel()
    val requestResponse by viewModel.responseRequest.observeAsState(initial = null)
    val isSocketOn by viewModel.isSocketOn.observeAsState(false)
    var isHttpOn by rememberSaveable {
        mutableStateOf(ObservableUtil.getValue("isSocketOn").toString().toBoolean())
    }

    val listOfOptions = EndpointsLists.requestsList + EndpointsLists.parametersList +
            listOf(
                ApiEndpoints.SEND_MESSAGE,
                ApiEndpoints.SEND_FILE_MESSAGE
            )


    var optionsOperation by rememberSaveable {
        mutableStateOf(listOfOptions[1])
    }
    var selectedOption: Int by rememberSaveable {
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

    val requestObject =
        RequestObject(param1 = param1, param2 = param2, param3 = param3, param4 = param4)
    val scope = rememberCoroutineScope()
    var parsedMessage: String? = null
    val gson: Gson = Gson()
    val context = LocalContext.current
    val responseColors = responseHttpColors
    var currentColorIndex by remember { mutableStateOf(0) }
    val currentColor = responseColors[currentColorIndex % responseColors.size]
    var isCardVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0) }
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.messageCountHttp()
    }
    LaunchedEffect(isSocketOn) {
        isHttpOn = isSocketOn
    }
    LaunchedEffect(requestResponse) {
        isLoading = false
        isCardVisible = true
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Requisições Http",
                navigateToLogs = { },
                onBackClick = { popBackStack() },
                popUpToLogin = popUpToLogin,
                isSocketOn = isHttpOn,
                apiIcon = false,
            ) {}
        }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DropDownToSet(
                        title = "Opções de requisições",
                        previousText = optionsOperation,
                        onTextChange = {
                            selectedOption = it.toInt()
                            isCardVisible = false
                        },
                        textStatus = optionsOperation,
                        dropdownItems = listOfOptions
                    )
                }
            }
            if (EndpointsLists.listOfFilteredRequests.contains(listOfOptions[selectedOption])) {
                item {
                    FilterSelectionBox(
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
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                elapsedTime = 0
                                coroutine.launch {
                                    while (isLoading) {
                                        delay(1000)
                                        elapsedTime++
                                    }
                                }
                                currentColorIndex++
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
                if (isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 25.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LoadingIcon(size = 25, padding = 0)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Processando Requisição... ${elapsedTime}s")
                    }
                } else if (isCardVisible && (requestResponse != null)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = currentColor),

                        ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Resposta da requisição: ${listOfOptions[selectedOption]}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            )
                            Text(
                                    text = (requestResponse!!.ifEmpty { "Null" }),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    style = TextStyle(color = Color.Black)
                                )
                        }
                    }
                }
            }
        }
    }
}


