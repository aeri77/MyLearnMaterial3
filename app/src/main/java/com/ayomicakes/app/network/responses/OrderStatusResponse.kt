package com.ayomicakes.app.network.responses


@kotlinx.serialization.Serializable
data class OrderStatusResponse(
    val orderStatus: String? = null,
    val orderDate: String? = null,
    val orderItemCount: String? = null,
    val orderPrice: Int? = null,
    val orderId: String? = null
)