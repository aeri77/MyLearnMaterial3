@file:OptIn(ExperimentalFoundationApi::class)

package com.ayomicakes.app.screen.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.ui.theme.Primary95
import kotlinx.coroutines.flow.Flow
import kotlin.math.floor


@Composable
fun CakeList(
    gridState: LazyGridState,
    navController: NavHostController,
    cakeItems: Flow<PagingData<CakeItem>>
) {
    val lazyCakeItems: LazyPagingItems<CakeItem> = cakeItems.collectAsLazyPagingItems()
    val count = lazyCakeItems.itemCount
    val height = floor((count * 240.0 / 2)).dp
    LazyVerticalGrid(
        state = gridState,
        modifier = Modifier
            .height(600.dp)
            .background(Primary95), columns = GridCells.Fixed(2)
    ) {
        items(lazyCakeItems.itemCount) { index ->
            CakeCard(navController, cakeItem = lazyCakeItems[index])
        }

        lazyCakeItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier) }
                }
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

@Composable
fun LoadingView(modifier: Modifier){
    Box(modifier = modifier){
        CircularProgressIndicator()
    }
}
