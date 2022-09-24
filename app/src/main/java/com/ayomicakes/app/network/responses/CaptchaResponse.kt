package com.ayomicakes.app.network.responses

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class CaptchaResponse(
    @SerialName("error-codes")
    val errorCodes: List<String?>? = null,

    @SerialName("apk_package_name")
    val apkPackageName: String? = null,

    @SerialName("success")
    val success: Boolean? = null,

    @SerialName("challenge_ts")
    val challengeTs: String? = null
)
