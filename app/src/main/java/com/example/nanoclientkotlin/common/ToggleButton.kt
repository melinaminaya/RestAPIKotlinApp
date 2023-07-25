package com.example.nanoclientkotlin.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ToggleSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    icon: Int?,
    modifier: Modifier = Modifier,
    ) {
    Row(
        modifier = modifier
            .clickable { onCheckedChange(!isChecked) }
            .padding(all = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
//    horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(start = 20.dp)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor= MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            ),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
    }
}
@Composable
fun IconToggleButtonSample() {
    val checked = remember { mutableStateOf(false) }
    IconToggleButton(checked = checked.value, onCheckedChange = { checked.value = it }) {
        if (checked.value) {
            Icon(Icons.Filled.Lock, contentDescription = "Localized description")
        } else {
            Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
        }
    }
}
@Composable
fun ToggleCard(modifier: Modifier, color: Color?,
               isChecked: Boolean,
               onCheckedChange: (Boolean) -> Unit,
               text: String,
               icon: Int?,){
    Card(
        modifier = modifier
            .padding(all = 10.dp)
            .height(70.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = if (color != null) CardDefaults.cardColors(containerColor = color) else CardDefaults.cardColors()

    ) {
        ToggleSwitch(isChecked = isChecked, onCheckedChange = onCheckedChange , text = text, icon = icon)

    }
}
