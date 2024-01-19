package br.com.autotrac.testnanoclient.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.autotrac.testnanoclient.models.PositionHistory
import kotlin.math.max
import kotlin.math.min

@Composable
fun PositionHistoryListCard(content: List<PositionHistory>) {
    val visibleItemCount = 5
    var currentPosition by rememberSaveable {
        mutableStateOf(content.size - 1)
    }
    val visibleContent = remember(currentPosition) {
        val startIndex = max(0, currentPosition - visibleItemCount + 1)
        val endIndex = min(content.size, currentPosition + 1)
        content.subList(startIndex, endIndex)
    }
    Column(
        modifier = Modifier
//                .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        //Modify visibleContent of list to show next or last
        visibleContent.forEach { contentItem ->
            Text(
                text = contentItem.toString(),
                style = TextStyle(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentPosition > 0) {
                IconButton(
                    onClick = {
                        currentPosition = max(0, currentPosition - visibleItemCount)
                    }
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Previous")
                }
            }

            if (currentPosition + visibleItemCount < content.size) {
                IconButton(
                    onClick = {
                        currentPosition = min(content.size - 1, currentPosition + visibleItemCount)
                    }
                ) {
                    Icon(Icons.Filled.ArrowForward, contentDescription = "Next")
                }
            }
        }
    }
}