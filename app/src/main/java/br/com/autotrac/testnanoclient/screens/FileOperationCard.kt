package br.com.autotrac.testnanoclient.screens

import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.autotrac.testnanoclient.common.DropDownToSet
import br.com.autotrac.testnanoclient.consts.ActionValues
import br.com.autotrac.testnanoclient.handlers.ParameterHandler

@Composable
fun FileOperationCard(
    onSaveClick: (Int, Int, String, Int) -> Unit,
) {
    val listOfOptions = listOf(
        ParameterHandler.convertFileOperationParam2(ActionValues.FileOperationOptions.NO_OPTIONS),
        ParameterHandler.convertFileOperationParam2(ActionValues.FileOperationOptions.COPY_FILES),
        ParameterHandler.convertFileOperationParam2(ActionValues.FileOperationOptions.ZIP_COMPRESSION),
    )
    val pathDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath

    var filesOperation by rememberSaveable {
        mutableStateOf(ActionValues.FileOperationFiles.API_LOG.toString())
    }
    var optionsOperation by rememberSaveable {
        mutableStateOf(listOfOptions[2])
    }
    var selectedOption: Int? by rememberSaveable {
        mutableStateOf(null)
    }
    var destinationOperation by rememberSaveable {
        mutableStateOf("$pathDir/AutotracAPI")
    }
    var timeoutMs by rememberSaveable {
        mutableStateOf(0.toString())
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Files: Mapa de bits ")

        TextField(
            value = filesOperation,
            onValueChange = { newValue ->
                filesOperation = newValue
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropDownToSet(
            title = "Options: cópia, zip ou nenhuma. ",
            previousText = optionsOperation,
            onTextChange = {
                selectedOption = it.toInt()
            },
            textStatus = optionsOperation,
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
            value = timeoutMs,
            onValueChange = { timeoutMs = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onSaveClick(
                    filesOperation.toInt(),
                    selectedOption ?: 2,
                    destinationOperation,
                    timeoutMs.toInt()
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Salvar")
        }
    }

}