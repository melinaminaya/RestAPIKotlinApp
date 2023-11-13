package br.com.autotrac.testnanoclient.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ConfigServiceLogCard(
    onSaveClick: (Boolean, Int, Long) -> Unit,
) {
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