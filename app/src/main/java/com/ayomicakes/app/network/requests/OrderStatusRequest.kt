package com.ayomicakes.app.network.requests

import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class OrderStatusRequest(
	@Serializable(with = UUIDSerializer::class)
	val userId: UUID? = null
)

