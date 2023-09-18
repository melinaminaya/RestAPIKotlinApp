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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.NanoWebsocketClient
import com.example.nanoclientkotlin.vm.AppViewModel
import com.example.nanoclientkotlin.vm.HttpTestViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonTicker(
    text: String,
    icon: Int,
    modifier: Modifier,
    onClick: () -> Unit,

    ) {

    val httpTestViewModel:HttpTestViewModel = viewModel()
    val count by httpTestViewModel.reqMessageCount.observeAsState("")
    val messagesCount = rememberSaveable{ mutableStateOf(count) }
    val appViewModel:AppViewModel = AppViewModel()

    val isApiOn = appViewModel.isApiOn
    LaunchedEffect(Unit) {
      if (isApiOn) {
          NanoWebsocketClient.startSendingRequests()
      } else{
        httpTestViewModel.messageCountHttp()
      }
    }
    LaunchedEffect(count){
        messagesCount.value = count

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

        if (messagesCount.value.isNotEmpty() && messagesCount.value != "0") {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, end = 8.dp)
                    .align(Alignment.TopEnd)
                    .wrapContentSize(),

                ) {
                Text(
                    text = messagesCount.value,
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
