package com.example.nanoclientkotlin.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropdownCard(title: String,content: @Composable () -> Unit) {
    val isExpanded = false
    val expanded = rememberSaveable { mutableStateOf(isExpanded) }
    val rotationAngle by animateFloatAsState(targetValue = if (expanded.value) 180f else 0f)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded.value = !expanded.value }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { expanded.value = !expanded.value }
                        .rotate(rotationAngle)
                )
            }
            if (expanded.value) {
                content()
            }
        }
    }
}


