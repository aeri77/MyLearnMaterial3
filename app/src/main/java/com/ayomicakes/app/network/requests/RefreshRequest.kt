package com.ayomicakes.app.network.requests

import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RefreshRequest(
    @SerialName("userId")
    @Serializable(with = UUIDSerializer::class)
    val userId : UUID?
)
