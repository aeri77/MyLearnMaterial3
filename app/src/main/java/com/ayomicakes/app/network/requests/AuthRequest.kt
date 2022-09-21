package com.ayomicakes.app.network.requests


@kotlinx.serialization.Serializable
data class AuthRequest(
    val username : String,
    val password : String
)
