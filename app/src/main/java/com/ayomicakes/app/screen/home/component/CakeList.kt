@file:OptIn(ExperimentalFoundationApi::class)

package com.ayomicakes.app.screen.home.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.ui.theme.Primary95
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun CakeList(
    gridState: LazyGridState,
    lazyCakeItems: LazyPagingItems<CakeItem>,
    onCakeClick : (CakeItem?) -> Unit
) {
    val configuration = LocalConfiguration.current
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)
    Crossfade(targetState = lazyCakeItems.loadState.refresh is LoadState.Loading) {
        if (it){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
    SwipeRefresh(state = refreshState, onRefresh = {
        lazyCakeItems.refresh()
    }) {
        LazyVerticalGrid(
            state = gridState,
            modifier = Modifier
                .height(configuration.screenHeightDp.dp)
                .background(Primary95), columns = GridCells.Fixed(2)
        ) {
            items(lazyCakeItems.itemCount) { item ->
                CakeCard(cakeItem = lazyCakeItems[item]){
                    onCakeClick(lazyCakeItems[item])
                }
            }
            lazyCakeItems.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item { LoadingView(modifier = Modifier) }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = lazyCakeItems.loadState.refresh as LoadState.Error
                        item {
                            Text(e.error.message.toString())
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = lazyCakeItems.loadState.append as LoadState.Error
                        item {
                            Text(e.error.message.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingView(modifier: Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator()
    }
}
