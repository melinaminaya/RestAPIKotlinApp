package com.example.nanoclientkotlin.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.vm.CheckListViewModel
import com.example.nanoclientkotlin.vm.MessageViewModel
import java.beans.PropertyChangeListener


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonTicker(
    text: String,
    icon: Int,
    modifier: Modifier,
    onClick: () -> Unit,

    ) {
//    val viewModel: MessageViewModel = viewModel()
//    val checkList by viewModel.checkList.observeAsState(emptyList())
    val count = ObservableUtil.transformJsonToInteger(ObservableUtil.getValue(ConstsCommSvc.REQ_MESSAGE_COUNT).toString()).toString()
    val badgeText = remember { mutableStateOf(if (count != "null" ) count else "") }
    val propertyChangeListener = PropertyChangeListener { event ->
        // Update the property value state when the event is triggered
        if (event.propertyName == ConstsCommSvc.REQ_MESSAGE_COUNT) {
            badgeText.value = ObservableUtil.transformJsonToInteger(ObservableUtil.getValue(ConstsCommSvc.REQ_MESSAGE_COUNT).toString()).toString()
        }
    }
    LaunchedEffect(Unit) {
        ObservableUtil.addPropertyChangeListener(propertyChangeListener)
    }


    Spacer(modifier = Modifier.height(5.dp))
    Box (  modifier= modifier ) {
        Card(
            onClick = onClick,
            modifier = modifier
                .padding(all = 8.dp)
                .height(150.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

            ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(all = 10.dp)
                )
                Text(
                    text = text,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }

        if (badgeText.value.isNotEmpty() && badgeText.value != "0") {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, end = 8.dp)
                    .align(Alignment.TopEnd)
                    .wrapContentSize(),

                ) {
                Text(
                    text = badgeText.value,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color.Red, shape = CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
