package com.ayomicakes.app.architecture

import androidx.datastore.core.DataStore
import com.ayomicakes.app.BuildConfig
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.config.ApiConfig
import com.ayomicakes.app.network.responses.GeoCodeResponse
import com.ayomicakes.app.network.services.MapServices
import com.google.android.gms.maps.model.LatLng
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.await
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface BaseRepository {
    fun getUserStore(): Flow<UserStore>

    suspend fun updateUserStore(user: UserStore)

    suspend fun clearUserStore()
    suspend fun getAddressByLatLng(latLng: LatLng): Flow<GeoCodeResponse>

}

@Singleton
class BaseRepositoryImpl @Inject constructor(
    private val userStore: DataStore<UserStore>,
) : BaseRepository {

    private val mapApi: MapServices = ApiConfig.getMapServices()
    override suspend fun getAddressByLatLng(latLng: LatLng): Flow<GeoCodeResponse> {
        val flowData = flow {
            val res = mapApi.getGeocode(
                latLng = "${latLng.latitude},${latLng.longitude}",
                key = BuildConfig.MAPS_API_KEY,
            ).await()
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }


    override fun getUserStore(): Flow<UserStore> {
        return userStore.data.catch { exception ->
            when (exception) {
                is IOException -> {
                    Timber.e("Error reading store. ", exception)
                    emit(UserStore())
                }
                else -> {
                    throw exception
                }
            }
        }
    }

    override suspend fun updateUserStore(user: UserStore) {
        userStore.updateData { store ->
            store.copy(
                username = user.username,
                address = user.address,
                fullName = user.fullName,
                phone = user.phone
            )
        }
    }

    override suspend fun clearUserStore() {
        userStore.updateData {
            UserStore()
        }
    }

}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun provideBaseRepositoryImpl(repository: BaseRepositoryImpl): BaseRepository
}