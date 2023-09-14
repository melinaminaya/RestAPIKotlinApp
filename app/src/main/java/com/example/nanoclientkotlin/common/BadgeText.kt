package com.example.nanoclientkotlin.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nanoclientkotlin.OrangeDanger

@Composable
fun BadgeText(text: String, isServiceOn: MutableState<Boolean>): Unit {
    return Box(
        modifier = Modifier
            .padding(end = 16.dp)
            .background(
                color = if (!isServiceOn.value) OrangeDanger.copy(alpha = 0.8f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
        ,
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, style = MaterialTheme.typography.titleSmall,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            fontWeight = FontWeight.Bold)

    }
}