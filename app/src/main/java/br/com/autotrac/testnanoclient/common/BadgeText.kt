package br.com.autotrac.testnanoclient.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.autotrac.testnanoclient.ui.theme.GreenLight
import br.com.autotrac.testnanoclient.ui.theme.OrangeDanger

@Composable
fun BadgeText(text: String, isServiceOn: Boolean): Unit {
    return Box(
        modifier = Modifier
            .padding(end = 16.dp)
            .background(
                color = if (!isServiceOn) OrangeDanger.copy(alpha = 0.8f) else
                    GreenLight.copy(alpha = 0.8f),
                shape = RoundedCornerShape(16.dp)
            )
            .width(60.dp)
        ,
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, style = MaterialTheme.typography.titleSmall,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            fontWeight = FontWeight.Bold)

    }
}