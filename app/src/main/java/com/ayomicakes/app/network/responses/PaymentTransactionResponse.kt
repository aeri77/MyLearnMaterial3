package com.ayomicakes.app.network.responses

@kotlinx.serialization.Serializable
data class PaymentTransactionResponse(
	val reference: String? = null,
	val vaNumber: String? = null,
	val merchantCode: String? = null,
	val amount: String? = null,
	val paymentUrl: String? = null,
	val statusMessage: String? = null,
	val statusCode: String? = null
)