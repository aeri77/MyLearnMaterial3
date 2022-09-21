package com.ayomicakes.app.network.responses

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class AuthResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)