package com.ayomicakes.app.datastore.serializer

import com.ayomicakes.app.network.responses.AddressResponse
import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class ProfileStore(
    @SerialName("userId")
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID? = null,

    @SerialName("fullName")
    val fullName: String? = null,

    @SerialName("addresses")
    val addresses : List<AddressResponse>? = null,

    @SerialName("email")
    val email : String? = null
)
