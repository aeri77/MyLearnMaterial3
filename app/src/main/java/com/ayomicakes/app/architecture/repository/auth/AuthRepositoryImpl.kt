package com.ayomicakes.app.architecture.repository.auth

import androidx.datastore.core.DataStore
import com.ayomicakes.app.architecture.repository.base.BaseRepositoryImpl
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.requests.*
import com.ayomicakes.app.network.responses.AuthResponse
import com.ayomicakes.app.network.responses.CaptchaResponse
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.Response
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.ayomicakes.app.utils.Result
import com.ayomicakes.app.utils.StringUtils.getBearer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AuthRepositoryImpl @Inject constructor(
    open val userStore: DataStore<UserStore>,
    private val profileStore: DataStore<ProfileStore>,
    open val mainApi: AyomiCakeServices
) : BaseRepositoryImpl(userStore, profileStore, mainApi), AuthRepository {
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

    override suspend fun postRefreshToken(
        userStore: UserStore?,
        refreshRequest: RefreshRequest
    ): Flow<Result<FullResponse<UserStore>>> {
        val flowData = flow {
            val res = mainApi.postRefreshToken(userStore?.refreshToken?.getBearer(), refreshRequest)
            emit(Result.Success(res))
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun postRegisterForm(
        authHeader: String,
        registerFormRequest: RegisterFormRequest
    ): Flow<Result<Response>> {
        val flowData = flow {
            handlingError {
                val res = mainApi.postRegisterForm(authHeader, registerFormRequest)
                emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun getProfile(
        authHeader: String,
        userId: UUID?
    ): Flow<Result<FullResponse<ProfileStore>>> {
        val flowData = flow {
            handlingError {
                val res = mainApi.getProfile(authHeader, userId)
                emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun removeFCM(fcmTokenRequest: FCMTokenRequest): Flow<Result<Response>> {
        val flowData = flow {
            handlingError {
                val res = mainApi.deleteFCMToken(fcmTokenRequest)
                emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }

}