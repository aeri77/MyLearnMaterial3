package com.ayomicakes.app.network.responses

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
sealed class BaseResponse<T> {
    abstract val message: String?
    abstract val result: T?
}

@kotlinx.serialization.Serializable
@SerialName("Response")
class Response(
    @SerialName("message") override val message: String?, override val result: String? = null
) : BaseResponse<String>()

@kotlinx.serialization.Serializable
@SerialName("FullResponse")
class FullResponse<T>(
    @SerialName("message") override val message: String?, override val result: T
) : BaseResponse<T>()