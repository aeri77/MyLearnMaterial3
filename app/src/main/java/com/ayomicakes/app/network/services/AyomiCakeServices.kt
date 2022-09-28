package com.ayomicakes.app.network.services

import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.network.config.RouteConstant
import com.ayomicakes.app.network.requests.AuthRequest
import com.ayomicakes.app.network.requests.CaptchaRequest
import com.ayomicakes.app.network.requests.OAuthRequest
import com.ayomicakes.app.network.requests.RegisterFormRequest
import com.ayomicakes.app.network.responses.*
import retrofit2.Call
import retrofit2.http.*
import java.util.UUID


interface AyomiCakeServices {

    @POST(RouteConstant.SIGN_IN)
    fun postSignIn(
        @Body authRequest: AuthRequest
    ): Call<FullResponse<AuthResponse>>

    @POST(RouteConstant.SIGN_UP)
    fun postSignUp(
        @Body authRequest: AuthRequest
    ): Call<Response>

    @POST(RouteConstant.SEND_CAPTCHA)
    fun sendCaptcha(@Body captchaRequest: CaptchaRequest): Call<FullResponse<CaptchaResponse>>

    @POST(RouteConstant.VERIFY_OAUTH)
    fun verifyOAuth(@Body oAuthRequest: OAuthRequest): Call<FullResponse<AuthResponse>>

    @POST(RouteConstant.REGISTER_FORM)
    fun postRegisterForm(@Header("Authorization") authHeader: String, @Body registerFormRequest: RegisterFormRequest): Call<Response>

    @GET(RouteConstant.PROFILE + "/{idx}")
    fun getProfile(@Header("Authorization") authHeader: String, @Path("idx") idx: UUID?): Call<FullResponse<ProfileStore>>

}