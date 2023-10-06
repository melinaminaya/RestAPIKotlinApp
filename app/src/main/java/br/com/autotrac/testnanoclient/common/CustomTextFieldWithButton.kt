package br.com.autotrac.testnanoclient.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomTextFieldWithButton(
    title: String,
    text: String?,
    onTextChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    var enabled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text ?: "Not available",
            onValueChange = onTextChange,
            label = { Text(text = title, fontSize = 14.sp) },
            enabled = enabled,
            modifier = Modifier
                .focusRequester(focusRequester)
                .weight(1f),
            textStyle = TextStyle(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                enabled = !enabled
                if (!enabled) {
                    onSaveClick()
                }
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = if (enabled) Icons.Default.Check else Icons.Default.Edit,
                contentDescription = if (enabled) "Save" else "Edit",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}







