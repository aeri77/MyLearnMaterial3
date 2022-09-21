package com.ayomicakes.app.network.responses

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Respond<T>(

    @SerialName("message")
    val message : String? = null,

    @SerialName("result")
    val result : T? = null
)
