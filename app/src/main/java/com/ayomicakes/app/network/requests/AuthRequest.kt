package com.ayomicakes.app.network.requests

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class AuthRequest(
    @SerialName("email")
    val email : String,
    @SerialName("password")
    val password : String
)
