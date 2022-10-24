package com.ayomicakes.app.network.requests

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class OAuthRequest(
    @SerialName("authToken")
    val authToken: String? = null,

    @SerialName("fcmToken")
    val fcmToken : String? = null
)
