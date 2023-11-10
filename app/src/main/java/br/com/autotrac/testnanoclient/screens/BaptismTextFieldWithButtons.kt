package br.com.autotrac.testnanoclient.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.autotrac.testnanoclient.common.BaptismStatusAlert
import br.com.autotrac.testnanoclient.ui.theme.HighPriorityColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BaptismTextFieldWithButtons(
    title: String,
    text: String?,
    baptismStatus: String,
    onTextChange: (String) -> Unit,
    onSaveClick: (String) -> Unit,
    onCancelClick: () -> Unit,
) {
    var enabled by rememberSaveable { mutableStateOf(false) }
    var editedText by rememberSaveable { mutableStateOf(text ?: "") }
    val focusRequester = remember { FocusRequester() }
    // Track the processing status with a mutableStateOf
    var processingStatus by rememberSaveable { mutableStateOf(false) }
    // Update the isFocused state based on the focus state of the TextField
    SideEffect {
        CoroutineScope(Dispatchers.Main).launch {
            enabled = focusRequester.captureFocus()
        }
    }
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = editedText,
            onValueChange = {
                editedText = it
                onTextChange(it)
            },
            label = { Text(text = title, fontSize = 14.sp) },
            modifier = Modifier
                .focusRequester(focusRequester)
                .weight(1f),
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = {
                Text(
                    text = "Salve o SSID para batizar.",
                    color = androidx.compose.ui.graphics.Color.Gray,
                    fontSize = 12.sp
                )
            },
            trailingIcon = {
                if (enabled) {
                    IconButton(onClick = { editedText = "" }) {
                        Icon(
                            Icons.Default.Delete, contentDescription = "Clear",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

            }
        )

        Spacer(modifier = Modifier.width(8.dp))
        //Save Button
        if (enabled) {
            Button(
                onClick = {
                    onSaveClick(editedText)
                    enabled = false
                    if (editedText != "") {
                        processingStatus = true
                    }
                    focusRequester.freeFocus()
                },
            ) {
                Text(text = "SALVAR", fontSize = 14.sp)

            }
        }
    }
    if (enabled) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Ao salvar sem SSID, efetuará o desbatismo.",
                fontSize = 12.sp,
                color = HighPriorityColor,
            )
        }
    }
    // Show an AlertDialog for processing status
    if (processingStatus) {
        BaptismStatusAlert(
            title = "Processando Batismo",
            text = baptismStatus,
            openDialog = processingStatus,
            dismissAction = {
                processingStatus = false
            }
        )
    }
}
