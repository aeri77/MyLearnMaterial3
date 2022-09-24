package com.ayomicakes.app.datastore.serializer

import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@kotlinx.serialization.Serializable
data class UserStore(

    @SerialName("userId")
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID? = null,
    @SerialName("accessToken") val accessToken : String? = null,
    @SerialName("refreshToken") val refreshToken : String? = null,
)
