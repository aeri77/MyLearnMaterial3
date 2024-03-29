package com.ayomicakes.app.network.services

import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.config.RouteConstant
import com.ayomicakes.app.network.requests.*
import com.ayomicakes.app.network.responses.*
import retrofit2.http.*
import java.util.UUID


interface AyomiCakeServices {

    @POST(RouteConstant.SIGN_IN)
    suspend fun postSignIn(
        @Body authRequest: AuthRequest
    ): FullResponse<AuthResponse>

    @POST(RouteConstant.SIGN_UP)
    suspend fun postSignUp(
        @Body authRequest: AuthRequest
    ): Response

    @POST(RouteConstant.SEND_CAPTCHA)
    suspend fun sendCaptcha(@Body captchaRequest: CaptchaRequest): FullResponse<CaptchaResponse>

    @POST(RouteConstant.VERIFY_OAUTH)
    suspend fun verifyOAuth(@Body oAuthRequest: OAuthRequest): FullResponse<AuthResponse>

    @POST(RouteConstant.REGISTER_FORM)
    suspend fun postRegisterForm(
        @Header("Authorization") authHeader: String,
        @Body registerFormRequest: RegisterFormRequest
    ): Response

    @GET(RouteConstant.PROFILE + "/{idx}")
    suspend fun getProfile(
        @Header("Authorization") authHeader: String,
        @Path("idx") idx: UUID?
    ): FullResponse<ProfileStore>

    @GET(RouteConstant.CAKES)
    suspend fun getCakes(
        @Query("page") page: Int
    ): FullResponse<PageModel<CakeItem>>

    @POST(RouteConstant.STATUS_ORDER)
    suspend fun getStatusOrders(
        @Query("page") page: Int,
        @Body orderStatusRequest: OrderStatusRequest,
        @Header("Authorization") authHeader: String?,
    ): FullResponse<PageModel<OrderStatusResponse>>


    @GET(RouteConstant.CAKES + "/{idx}")
    suspend fun getCakes(@Path("idx") idx: UUID?): FullResponse<CakeItem>

    @POST(RouteConstant.REFRESH_TOKEN)
    suspend fun postRefreshToken(
        @Header("Authorization") authHeader: String?,
        @Body refreshRequest: RefreshRequest
    ): FullResponse<UserStore>

    @POST(RouteConstant.FCM_TOKEN)
    suspend fun postFCMToken(
        @Body fcmRequest: FCMTokenRequest
    ): Response
    @POST(RouteConstant.FCM_TOKEN_DELETE)
    suspend fun deleteFCMToken(
        @Body fcmRequest: FCMTokenRequest
    ): Response

    @POST(RouteConstant.CHECKOUT)
    suspend fun postCheckoutRequest(
        @Header("Authorization") authHeader: String?,
        @Body checkoutRequest: CheckoutRequest
    ) : FullResponse<PaymentTransactionResponse>
}