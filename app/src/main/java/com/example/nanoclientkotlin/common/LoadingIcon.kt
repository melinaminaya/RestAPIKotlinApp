package com.example.nanoclientkotlin.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIcon() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(40.dp)
            .padding(8.dp)
    )
}