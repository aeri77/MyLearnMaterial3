package com.ayomicakes.app.architecture.repository.auth

import androidx.datastore.core.DataStore
import com.ayomicakes.app.architecture.repository.base.BaseRepositoryImpl
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.requests.*
import com.ayomicakes.app.network.responses.*
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
open class AuthRepositoryDummy @Inject constructor(
    open val userStore: DataStore<UserStore>,
    private val profileStore: DataStore<ProfileStore>,
    open val mainApi: AyomiCakeServices
) : BaseRepositoryImpl(userStore, profileStore, mainApi), AuthRepository {
    override suspend fun signUp(authRequest: AuthRequest): Flow<Response> {
        val flowData = flow {
            val res = Response("", "")
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun signIn(authRequest: AuthRequest): Flow<FullResponse<AuthResponse>> {
        val flowData = flow {
            val res = FullResponse("", AuthResponse("accessToken","refreshToken", UUID.randomUUID()))
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun sendCaptcha(captchaRequest: CaptchaRequest): Flow<FullResponse<CaptchaResponse>> {
        val flowData = flow {
            val res = FullResponse("",CaptchaResponse(listOf("212"),"tes",true))
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun verifyOAuth(oAuthRequest: OAuthRequest): Flow<FullResponse<AuthResponse>> {
        val flowData = flow {
            val res = FullResponse("", AuthResponse("accessToken","refreshToken", UUID.randomUUID()))
            emit(res)
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun postRefreshToken(
        userStore: UserStore?,
        refreshRequest: RefreshRequest
    ): Flow<Result<FullResponse<UserStore>>> {
        val flowData = flow {
            handlingError {
                val res = FullResponse("", UserStore(UUID.randomUUID(), "" ,""))
                emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun postRegisterForm(
        authHeader: String,
        registerFormRequest: RegisterFormRequest
    ): Flow<Result<Response>> {
        val flowData = flow {
            handlingError {
                val res = Response("")
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
                val res = FullResponse("", ProfileStore(UUID.randomUUID(),"aeri", listOf(AddressResponse(1,"","","","","","","",""))))
                emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }

    override suspend fun removeFCM(fcmTokenRequest: FCMTokenRequest): Flow<Result<Response>> {
        val flowData = flow {
            handlingError {
                val res = Response("success")
                emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }

}