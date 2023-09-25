package com.example.nanoclientkotlin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nanoclientkotlin.dataRemote.IntegrationForm

@Composable
fun MascaraDropdownMenu(
    mascaras: List<IntegrationForm>,
    selectedMascara: String,
    onMascaraSelected: (IntegrationForm) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .height(60.dp)
            .padding(horizontal = 10.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Display selected mascara or default text
            Text(
                text = selectedMascara  ,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            IconButton(
                onClick = { expanded.value = !expanded.value },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .background(Color.LightGray),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Toggle Dropdown Menu"
                )
            }
        }
    }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        DropdownMenuItem(
            text = { Text("Mensagem Livre") },
            onClick = {
                expanded.value = false
            }
        )
        mascaras.let { mascarasList ->
            if (mascarasList?.isEmpty() == true) {
                DropdownMenuItem(
                    text = { Text(text = "No options available") },
                    onClick = { expanded.value = false }
                )
            } else {
                mascarasList?.forEach { mascara ->
                    DropdownMenuItem(
                        text = { Text(mascara.code.toString()) },
                        onClick = {
                            onMascaraSelected(mascara)
                            expanded.value = false
//                            messageText = mascara.code.toString()
                        }
                    )
                }
            }
        } ?: run {
            DropdownMenuItem(
                text = { Text(text = "No options available") },
                onClick = { expanded.value = false }
            )
        }
    }
}