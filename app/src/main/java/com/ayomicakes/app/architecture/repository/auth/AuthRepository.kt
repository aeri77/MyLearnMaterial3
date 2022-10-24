package com.ayomicakes.app.architecture.repository.auth

import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.requests.*
import com.ayomicakes.app.network.responses.AuthResponse
import com.ayomicakes.app.network.responses.CaptchaResponse
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.Response
import com.ayomicakes.app.utils.Result
import kotlinx.coroutines.flow.Flow
import java.util.*

interface AuthRepository: BaseRepository {

    suspend fun signUp(authRequest: AuthRequest): Flow<Response>
    suspend fun signIn(authRequest: AuthRequest): Flow<FullResponse<AuthResponse>>
    suspend fun sendCaptcha(captchaRequest: CaptchaRequest): Flow<FullResponse<CaptchaResponse>>
    suspend fun verifyOAuth(oAuthRequest: OAuthRequest): Flow<FullResponse<AuthResponse>>
    suspend fun postRefreshToken(
        userStore: UserStore?,
        refreshRequest: RefreshRequest
    ): Flow<Result<FullResponse<UserStore>>>

    suspend fun postRegisterForm(
        authHeader: String,
        registerFormRequest: RegisterFormRequest
    ): Flow<Result<Response>>

    suspend fun getProfile(
        authHeader: String,
        userId: UUID?
    ): Flow<Result<FullResponse<ProfileStore>>>

    suspend fun removeFCM(fcmTokenRequest: FCMTokenRequest): Flow<Result<Response>>
}