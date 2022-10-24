package com.ayomicakes.app.architecture.repository.checkout

import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.network.requests.CheckoutRequest
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PaymentTransactionResponse
import kotlinx.coroutines.flow.Flow
import com.ayomicakes.app.utils.Result

interface CheckoutRepository : AuthRepository {
    suspend fun postCheckout( authHeader: String ,checkoutRequest: CheckoutRequest): Flow<Result<FullResponse<PaymentTransactionResponse>>>
}