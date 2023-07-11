package com.example.nanoclientkotlin.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nanoclientkotlin.HighPriorityColor
import com.example.nanoclientkotlin.R

@Composable
fun RedBackground(
    degrees: Float,
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .background(HighPriorityColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp),

        contentAlignment = Alignment.CenterEnd,
//    colors = CardDefaults.cardColors(HighPriorityColor)

    ) {

        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}


