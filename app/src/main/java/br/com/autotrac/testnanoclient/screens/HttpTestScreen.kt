package br.com.autotrac.testnanoclient.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.common.CustomTopAppBar
import br.com.autotrac.testnanoclient.common.DropDownToSet
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.requestObjects.RequestObject
import br.com.autotrac.testnanoclient.handlers.EndpointsLists
import br.com.autotrac.testnanoclient.vm.HttpTestViewModel
import com.google.gson.Gson
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
    val requestResponse by viewModel.responseRequest.observeAsState(initial = "")

    val listOfOptions = EndpointsLists.requestsList + EndpointsLists.parametersList +
            listOf(
                ApiEndpoints.SEND_MESSAGE ,
                ApiEndpoints.SEND_FILE_MESSAGE
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
            CustomTopAppBar(
                title = "Requisições Http",
                navigateToLogs = { },
                onBackClick = {popBackStack()},
                popUpToLogin = popUpToLogin,
                isSocketOn = null) {
            }
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



