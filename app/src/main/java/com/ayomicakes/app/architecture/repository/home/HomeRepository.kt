package com.ayomicakes.app.architecture.repository.home

import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PageModel
import com.ayomicakes.app.utils.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepository : AuthRepository {

    suspend fun getCakes(page: Int): Flow<Result<FullResponse<PageModel<CakeItem>>>>
}