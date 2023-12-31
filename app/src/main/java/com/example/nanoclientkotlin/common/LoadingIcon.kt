package com.example.nanoclientkotlin.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIcon() {

        CircularProgressIndicator(
            color = Color.Blue,
            trackColor = Color.White,
            strokeWidth = 2.dp,
            modifier = Modifier.size(25.dp),
            strokeCap = StrokeCap.Round,

        )

}