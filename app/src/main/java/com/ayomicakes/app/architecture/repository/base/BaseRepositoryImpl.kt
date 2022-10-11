package com.ayomicakes.app.architecture.repository.base

import androidx.datastore.core.DataStore
import com.ayomicakes.app.BuildConfig
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.config.ApiConfig
import com.ayomicakes.app.network.responses.GeoCodeResponse
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.ayomicakes.app.network.services.MapServices
import com.ayomicakes.app.utils.Result
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.await
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseRepositoryImpl @Inject constructor(
    private val userStore: DataStore<UserStore>,
    private val profileStore: DataStore<ProfileStore>,
    private val mainApi: AyomiCakeServices
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



    override fun getUserStore(): Flow<UserStore?> {
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

    override fun getProfileStore(): Flow<ProfileStore?> {
        return profileStore.data.catch { exception ->
            when (exception) {
                is IOException -> {
                    Timber.e("Error reading store. ", exception)
                    emit(ProfileStore())
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
                userId = user.userId,
                accessToken = user.accessToken,
                refreshToken = user.refreshToken
            )
        }
    }

    override suspend fun updateProfileStore(profile: ProfileStore) {
        profileStore.updateData {
            profile.copy(
                userId = profile.userId,
                fullName = profile.fullName,
                addresses = profile.addresses
            )
        }
    }

    override suspend fun clearStore() {
        userStore.updateData {
            UserStore()
        }

        profileStore.updateData {
            ProfileStore()
        }
    }

    internal suspend inline fun <T> FlowCollector<Result<T>>.handlingError(invoke: (FlowCollector<Result<T>>) -> Unit) {
        try {
            invoke(this)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> emit(Result.Error(e.message.toString(), e.code()))
                else -> {
                    emit(Result.Error(e.message.toString()))
                }
            }

        }
    }
}