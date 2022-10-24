package com.ayomicakes.app.architecture.repository.checkout

import androidx.datastore.core.DataStore
import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.auth.AuthRepositoryImpl
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.requests.CheckoutRequest
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PaymentTransactionResponse
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.ayomicakes.app.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckoutRepositoryImpl @Inject constructor(
    override val userStore: DataStore<UserStore>,
    val profileStore: DataStore<ProfileStore>,
    override val mainApi: AyomiCakeServices
) : AuthRepositoryImpl(userStore, profileStore, mainApi), CheckoutRepository {
    override suspend fun postCheckout(
        authHeader: String,
        checkoutRequest: CheckoutRequest
    ): Flow<Result<FullResponse<PaymentTransactionResponse>>> {
        val flowData = flow {
            handlingError {
                val res = mainApi.postCheckoutRequest(authHeader, checkoutRequest)
                emit(Result.Success(res))
            }
        }
        return flowData.flowOn(Dispatchers.IO)
    }

}