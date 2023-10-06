package br.com.autotrac.testnanoclient.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

@Composable
fun MessageTextField(
    messageText: MutableState<String>,
    onMessageTextChanged: (String) -> Unit
) {
    var characterCount by remember(messageText) {
        mutableStateOf(messageText.value.length)
    }

    var sizeInKB by remember(messageText) {
        mutableStateOf(calculateSizeInKB(messageText.value))
    }
    LaunchedEffect(messageText.value) {
        sizeInKB = calculateSizeInKB(messageText.value)
    }
    Box {
        TextField(
            value = messageText.value,
            onValueChange = {
                messageText.value = it
                characterCount = it.length
                sizeInKB = calculateSizeInKB(it)
                onMessageTextChanged(it) // Notify the parent when the text changes
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min= 120.dp)
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            label = { Text("Message") },
            textStyle = TextStyle(fontSize = 18.sp),
            singleLine = false,
            maxLines = Int.MAX_VALUE,
        )

        // Character count and size in KB text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "$characterCount / $sizeInKB KB",
                style = MaterialTheme.typography.displaySmall,
                color = Color.Gray
            )
        }
    }
}
private fun calculateSizeInKB(text: String): String {
    val byteCount = text.toByteArray().size
    val sizeInKB = byteCount / (1024).toDouble()
    return DecimalFormat("#.##").format(sizeInKB)
}