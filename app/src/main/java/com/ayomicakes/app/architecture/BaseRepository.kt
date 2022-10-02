package com.ayomicakes.app.architecture

import androidx.datastore.core.DataStore
import com.ayomicakes.app.BuildConfig
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.config.ApiConfig
import com.ayomicakes.app.network.requests.AuthRequest
import com.ayomicakes.app.network.requests.CaptchaRequest
import com.ayomicakes.app.network.requests.OAuthRequest
import com.ayomicakes.app.network.requests.RegisterFormRequest
import com.ayomicakes.app.network.responses.*
import com.ayomicakes.app.network.services.AyomiCakeServices
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
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import com.ayomicakes.app.utils.Result

interface BaseRepository {
    fun getUserStore(): Flow<UserStore?>
    fun getProfileStore(): Flow<ProfileStore?>
    suspend fun updateUserStore(user: UserStore)
    suspend fun updateProfileStore(profile: ProfileStore)
    suspend fun clearStore()
    suspend fun getAddressByLatLng(latLng: LatLng): Flow<GeoCodeResponse>
    suspend fun signUp(authRequest: AuthRequest): Flow<Response>
    suspend fun signIn(authRequest: AuthRequest): Flow<FullResponse<AuthResponse>>
    suspend fun sendCaptcha(captchaRequest: CaptchaRequest) : Flow<FullResponse<CaptchaResponse>>
    suspend fun verifyOAuth(oAuthRequest: OAuthRequest) : Flow<FullResponse<AuthResponse>>
    suspend fun postRegisterForm(authHeader:String, registerFormRequest: RegisterFormRequest) : Flow<Result<Response>>
    suspend fun getProfile(authHeader: String, userId : UUID?) : Flow<FullResponse<ProfileStore>>
    suspend fun getCakes(authHeader: String?) : Flow<Result<FullResponse<PageModel<CakeItem>>>>

}

@Singleton
class BaseRepositoryImpl @Inject constructor(
    private val userStore: DataStore<UserStore>,
    private val profileStore: DataStore<ProfileStore>,
) : BaseRepository {

    private val mapApi: MapServices = ApiConfig.getMapServices()
    private val mainApi : AyomiCakeServices = ApiConfig.getAppServices()
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

    override suspend fun signUp(authRequest: AuthRequest): Flow<Response> {
        val flowData = flow {
            val res = mainApi.postSignUp(authRequest)
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun signIn(authRequest: AuthRequest): Flow<FullResponse<AuthResponse>> {
        val flowData = flow {
            val res = mainApi.postSignIn(authRequest)
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun sendCaptcha(captchaRequest: CaptchaRequest): Flow<FullResponse<CaptchaResponse>> {
        val flowData = flow {
            val res = mainApi.sendCaptcha(captchaRequest)
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun verifyOAuth(oAuthRequest: OAuthRequest): Flow<FullResponse<AuthResponse>> {
        val flowData = flow {
            val res = mainApi.verifyOAuth(oAuthRequest)
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun postRegisterForm(authHeader:String, registerFormRequest: RegisterFormRequest): Flow<Result<Response>> {
        val flowData = flow {
            val res = mainApi.postRegisterForm(authHeader,registerFormRequest)
            emit(Result.Success(res))
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun getProfile(authHeader: String, userId: UUID?): Flow<FullResponse<ProfileStore>> {
        val flowData = flow {
            val res = mainApi.getProfile(authHeader,userId)
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun getCakes(authHeader: String?): Flow<Result<FullResponse<PageModel<CakeItem>>>> {
        val flowData = flow {
            val res = mainApi.getCakes(authHeader)
            emit(Result.Success(res))
        }
        return flowData
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

}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun provideBaseRepositoryImpl(repository: BaseRepositoryImpl): BaseRepository
}