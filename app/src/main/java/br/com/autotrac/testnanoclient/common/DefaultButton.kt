package br.com.autotrac.testnanoclient.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultButton(
    text: String,
    icon: Int,
    modifier: Modifier,
    color: Color?,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(5.dp))
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .size(width = 250.dp, height = 70.dp)
//    )
//    {
//
//        Text(text, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.align(CenterVertically))
//
//    }
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(all = 8.dp)
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = if (color != null) CardDefaults.cardColors(containerColor = color) else CardDefaults.cardColors()

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(all = 10.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
