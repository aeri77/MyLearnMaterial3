package com.ayomicakes.app.architecture.repository.home

import androidx.datastore.core.DataStore
import androidx.paging.PagingData
import com.ayomicakes.app.architecture.repository.auth.AuthRepositoryImpl
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.services.AyomiCakeServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import com.ayomicakes.app.utils.Result

@Singleton
class HomeRepositoryImpl @Inject constructor(
    override val userStore: DataStore<UserStore>,
    private val profileStore: DataStore<ProfileStore>,
    override val mainApi: AyomiCakeServices
) : AuthRepositoryImpl(userStore, profileStore, mainApi), HomeRepository {
    override fun getCakes(uuid: UUID): Flow<Result<FullResponse<CakeItem>>> {
        val flowData = flow {
            handlingError {
                val res = mainApi.getCakes(uuid)
                emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }
}