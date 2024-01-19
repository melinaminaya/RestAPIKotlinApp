package br.com.autotrac.testnanoclient.common

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.autotrac.testnanoclient.models.IntegrationMessage
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeItem(
    message: IntegrationMessage,
    onMessageDelete: () -> Unit,
    onMessageClick: () -> Unit,
) {
    val context = LocalContext.current
    var status by remember {
        mutableStateOf(
            when (message.msgStatusNum) {
                0 -> "N/A"
                1 -> "A enviar"
                2 -> "Enviada"
                3 -> "Não Lida"
                4 -> "Lida"
                5 -> "Not Processed"
                6 -> "Transmitida"
                7 -> "Mensagem Longa não processada"
                8 -> "Transmitindo"
                else -> "Erro"
            }
        )
    }
    LaunchedEffect(message) {
        status = message.msgStatusNum.let {
            when (message.msgStatusNum) {
                0 -> "N/A"
                1 -> "A enviar"
                2 -> "Enviada"
                3 -> "Não Lida"
                4 -> "Lida"
                5 -> "Not Processed"
                6 -> "Transmitida"
                7 -> "Mensagem Longa não processada"
                8 -> "Transmitindo"
                else -> "Erro"
            }
        }.toString()
    }

    var dateFormat: SimpleDateFormat?
    var formattedDate: String? = ""
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        formattedDate = dateFormat.format(message.createdTime)
    } else {
        formattedDate =
            message.createdTime?.let {
                android.text.format.DateFormat.getDateFormat(context).format(
                    it
                )
            }
    }
    val hasTriedToDismiss = remember { mutableStateOf(false) }
    var hasConfirmedDismissal: Boolean by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(
        initialValue = DismissValue.Default,
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart && !hasConfirmedDismissal) {
//                hasTriedToDismiss.value = true
                hasConfirmedDismissal = true
                onMessageDelete()
                true
            } else {
                false
            }
        },
    )
    var dismissStateVal by remember { mutableStateOf(dismissState) }
    if (hasConfirmedDismissal) {
        // Reset dismissState after the item has been deleted
        LaunchedEffect(Unit) {
            hasConfirmedDismissal = false
            dismissState.snapTo(DismissValue.Default)
            dismissState.reset()
            dismissStateVal = dismissState
        }
    }
    val degrees by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f, label = ""
    )
    SwipeToDismiss(
        state = dismissStateVal,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            RedBackground(degrees = degrees, onMessageDelete = { onMessageDelete() })

        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clickable { onMessageClick() },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = if (message.formCode == 0L) "Mensagem Livre" else message.formCode.toString(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        if (formattedDate != null) {
                            Text(text = formattedDate, textAlign = TextAlign.End)
                        }
                    }

                    Row {
                        Text(text = "Status: $status", textAlign = TextAlign.Start)
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = "Codigo: ${message.code}", textAlign = TextAlign.End)
                    }
                }
            }
        }
    )
}

