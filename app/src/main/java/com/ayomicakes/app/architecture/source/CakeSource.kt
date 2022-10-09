package com.ayomicakes.app.architecture.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.services.AyomiCakeServices
import kotlinx.coroutines.flow.collectLatest

class CakeSource(
    private val services: AyomiCakeServices
) : PagingSource<Int, CakeItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CakeItem> {
        return try {
            val nextPage = params.key ?: 1
            val cakeListRes = services.getCakes(nextPage)

            LoadResult.Page(
                data = cakeListRes.result.result.orEmpty(),
                prevKey = if (cakeListRes.result.page == 1) null else nextPage - 1,
                nextKey = if (cakeListRes.result.page < cakeListRes.result.pageCount) cakeListRes.result.page.plus(
                    1
                ) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CakeItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}