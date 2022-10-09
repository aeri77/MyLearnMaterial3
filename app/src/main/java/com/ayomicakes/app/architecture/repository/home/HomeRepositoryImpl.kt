package com.ayomicakes.app.architecture.repository.home

import androidx.datastore.core.DataStore
import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.auth.AuthRepositoryImpl
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.architecture.repository.base.BaseRepositoryImpl
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PageModel
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.ayomicakes.app.utils.Result
import com.ayomicakes.app.utils.StringUtils.getBearer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    override val userStore: DataStore<UserStore>,
    private val profileStore: DataStore<ProfileStore>,
    override val mainApi: AyomiCakeServices
) : AuthRepositoryImpl(userStore, profileStore, mainApi), HomeRepository {

    override suspend fun getCakes(page: Int): Flow<Result<FullResponse<PageModel<CakeItem>>>> {
        val flowData = flow {
            handlingError {
                val res = mainApi.getCakes(page)
                it.emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }
}