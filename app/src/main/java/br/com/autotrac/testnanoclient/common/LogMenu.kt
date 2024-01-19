package br.com.autotrac.testnanoclient.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.vm.AppViewModel
import kotlinx.coroutines.delay

@Composable
fun LogContent() {
    val appViewModel: AppViewModel = viewModel()
    val logs by appViewModel.logs.collectAsState(initial = emptyList())
    // State for holding the search query
    var searchQuery by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        appViewModel.registerLogs()
        while (true) {
            delay(1000)
            appViewModel.registerLogs()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Search field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
            },
            label = { Text("Pesquisar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            text = "Logs",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),

            )
        LazyColumn(
            userScrollEnabled = true,
            reverseLayout = true,
            state = LazyListState(logs.size),
        ) {
            // Filter logs based on search query
            val filteredLogs = logs.filter { log ->
                log.contains(searchQuery, ignoreCase = true)
            }
            items(filteredLogs) { log ->
                Text(
                    text = log,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            //TODO: Copy when clickable?
//                            onLogSelected(log)
                        }
                        .padding(16.dp),
                )
            }
        }
    }
}

