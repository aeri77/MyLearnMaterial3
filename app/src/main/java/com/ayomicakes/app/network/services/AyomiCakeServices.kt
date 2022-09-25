package com.ayomicakes.app.network.services

import com.ayomicakes.app.network.config.RouteConstant
import com.ayomicakes.app.network.requests.AuthRequest
import com.ayomicakes.app.network.requests.CaptchaRequest
import com.ayomicakes.app.network.requests.OAuthRequest
import com.ayomicakes.app.network.responses.AuthResponse
import com.ayomicakes.app.network.responses.CaptchaResponse
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

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

}