package com.ayomicakes.app.architecture.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ayomicakes.app.network.requests.OrderStatusRequest
import com.ayomicakes.app.network.responses.OrderStatusResponse
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.ayomicakes.app.utils.StringUtils.getBearer
import java.util.*

class CheckoutSource(
    private val services: AyomiCakeServices,
    val userId: UUID?,
    private val token: String?
) : PagingSource<Int, OrderStatusResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderStatusResponse> {
        return try {
            val nextPage = params.key ?: 1
            val statusOrderList = services.getStatusOrders(nextPage, OrderStatusRequest(userId), token)

            LoadResult.Page(
                data = statusOrderList.result.result.orEmpty(),
                prevKey = if (statusOrderList.result.page == 1) null else nextPage - 1,
                nextKey = if (statusOrderList.result.page < statusOrderList.result.pageCount) statusOrderList.result.page.plus(
                    1
                ) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, OrderStatusResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}