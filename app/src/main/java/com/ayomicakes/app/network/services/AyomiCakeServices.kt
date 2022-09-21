package com.ayomicakes.app.network.services

import com.ayomicakes.app.network.config.RouteConstant
import com.ayomicakes.app.network.requests.AuthRequest
import com.ayomicakes.app.network.responses.AuthResponse
import com.ayomicakes.app.network.responses.Respond
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AyomiCakeServices {

    @POST(RouteConstant.SIGN_IN)
    fun postSignIn(
        @Body authRequest: AuthRequest
    ): Call<Respond<AuthResponse>>

    @POST(RouteConstant.SIGN_UP)
    fun postSignUp(
        @Body authRequest: AuthRequest
    ): Call<Respond<AuthResponse>>

}