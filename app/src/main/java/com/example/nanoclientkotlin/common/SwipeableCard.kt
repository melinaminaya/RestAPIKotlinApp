package com.example.nanoclientkotlin.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.compose.runtime.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nanoclientkotlin.HighPriorityColor
import com.example.nanoclientkotlin.R


@Composable
fun Swipeable(
    modifier: Modifier = Modifier,
    onSwipeStart: () -> Unit = {},
    onSwipeEnd: () -> Unit = {},
    onDelete: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var dragging by remember { mutableStateOf(false) }
    var velocityX by remember { mutableStateOf(0f) }
//    val degrees by animateFloatAsState(
//        targetValue =
//        if (dismissState.targetValue == DismissValue.Default)
//            0f
//        else
//            -45f
//    )
    val degrees by animateFloatAsState(targetValue = -45f)

    val scope = rememberCoroutineScope()

    val panGesture = Modifier.pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            if (change.pressed) {
                    onSwipeStart()
                    dragging = true
                    velocityX = 0f
                if (change.pressed != change.previousPressed) change.consume()
            }
               else {
                    dragging = false
                    onSwipeEnd()

                    if (dragAmount.x<0) {
                        scope.launch {
                            offsetX = -200f
                            onDelete()
                        }
                    } else if (offsetX < 0)  {
                        scope.launch {
                            offsetX = 0f
                        }
                    }
                if (change.positionChange() != Offset.Zero) change.consume()
            }


            if (dragging ){
//                if (dragAmount.x>=0){
//                    change.consume()
//                }else {

                    offsetX += dragAmount.x
                    velocityX = dragAmount.x / change.uptimeMillis
//                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .then(panGesture)
    ) {
        content()
        if (offsetX < 0) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = {
                    onDelete()
                    scope.launch {
                        offsetX = 0f
                    } },
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
//            RedBackground(degrees = degrees)
        }

    }

}
@Composable
fun RedBackground(degrees: Float ) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .background(HighPriorityColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp),

        contentAlignment = Alignment.CenterEnd,
//    colors = CardDefaults.cardColors(HighPriorityColor)

    ) {

        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}
//@Composable
//@ExperimentalMaterial3Api
//fun SwipeToDismiss(
//    state: DismissState,
//    background: @Composable RowScope.() -> Unit,
//    dismissContent: @Composable RowScope.() -> Unit,
//    modifier: Modifier = Modifier,
//    directions: Set<DismissDirection> = setOf(EndToStart, StartToEnd),
//){
//
//}