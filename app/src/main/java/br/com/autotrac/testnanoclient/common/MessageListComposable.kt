package br.com.autotrac.testnanoclient.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.autotrac.testnanoclient.dataRemote.IntegrationMessage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun MessageListComposable(
    messages: List<IntegrationMessage>,
    onMessageDelete: (IntegrationMessage) -> Unit,
    onMessageClick: (IntegrationMessage) -> Unit,
    onDialogDismiss: () -> Unit,
    onRefresh:()->Unit,
)
{
    val sortedMessages = messages.sortedByDescending { it.createdTime }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val lazyListState = rememberLazyListState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = {
            onRefresh()
            swipeRefreshState.isRefreshing = false
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState
        ) {

            items(sortedMessages) { message ->
                SwipeItem(message = message, onMessageDelete = {
                    onMessageDelete(message)
                },
                    onMessageClick = {
                        onMessageClick(message)
                    }
                )
            }
        }
    }
}

