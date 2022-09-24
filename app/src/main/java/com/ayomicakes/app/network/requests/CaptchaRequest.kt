package com.ayomicakes.app.network.requests

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class CaptchaRequest(
    @SerialName("captchaToken")
    val captchaToken: String? = null
)
