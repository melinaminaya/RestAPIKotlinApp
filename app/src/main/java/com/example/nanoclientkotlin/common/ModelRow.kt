package com.example.nanoclientkotlin.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ModelRow(title: String, status: String?) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(fontSize = 14.sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(2f)
                .wrapContentWidth(Alignment.Start)
        )
        Spacer(modifier = Modifier.width(8.dp))
        if(status != null) {
            Text(
                text = status,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth()
            )
        }else{
            LoadingIcon()
        }
    }
}