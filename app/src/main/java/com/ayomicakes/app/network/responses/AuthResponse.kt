package com.ayomicakes.app.network.responses

import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@kotlinx.serialization.Serializable
data class AuthResponse(

    @SerialName("accessToken")
    val accessToken: String? = null,

    @SerialName("refreshToken")
    val refreshToken: String,

    @SerialName("userId")
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
)
