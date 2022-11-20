package com.ayomicakes.app.screen.home.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.network.responses.OrderStatusResponse
import com.ayomicakes.app.screen.home.page.OrderStatusCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun OrderStatusList(
    lazyOrderStatus: LazyPagingItems<OrderStatusResponse>
) {
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)
    Crossfade(targetState = lazyOrderStatus.loadState.refresh is LoadState.Loading) {
        if (it){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
    SwipeRefresh(state = refreshState, onRefresh = {
        lazyOrderStatus.refresh()
    }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(lazyOrderStatus.itemCount) {
                OrderStatusCard(lazyOrderStatus[it])
            }
            lazyOrderStatus.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item { LoadingView(modifier = Modifier) }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = lazyOrderStatus.loadState.refresh as LoadState.Error
                        item {
                            Text(e.error.message.toString())
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = lazyOrderStatus.loadState.append as LoadState.Error
                        item {
                            Text(e.error.message.toString())
                        }
                    }
                }
            }
        }
    }
}