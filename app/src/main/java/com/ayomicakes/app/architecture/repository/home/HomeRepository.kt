package com.ayomicakes.app.architecture.repository.home

import androidx.paging.PagingData
import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.responses.AuthResponse
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PageModel
import com.ayomicakes.app.utils.Result
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HomeRepository : AuthRepository {

    fun getCakes(uuid: UUID): Flow<Result<FullResponse<CakeItem>>>
}