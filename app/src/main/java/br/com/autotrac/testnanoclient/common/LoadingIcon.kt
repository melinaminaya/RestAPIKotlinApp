package br.com.autotrac.testnanoclient.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIcon(
    size: Int
) {

        CircularProgressIndicator(
            color = Color.Blue,
            trackColor = Color.White,
            strokeWidth = 2.dp,
            modifier = Modifier.size(size.dp),
            strokeCap = StrokeCap.Round,

        )

}