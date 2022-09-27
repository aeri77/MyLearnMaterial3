package com.ayomicakes.app.network.requests

import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RegisterFormRequest(

    @SerialName("userId")
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID?,

    @SerialName("fullName")
    val fullName: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("address")
    val address: String,

    @SerialName("locality")
    val locality: String,

    @SerialName("subAdminArea")
    val subAdminArea: String,

    @SerialName("adminArea")
    val adminArea: String? = null,

    @SerialName("countryName")
    val countryName: String? = null,

    @SerialName("countryID")
    val countryID: String? = null,

    @SerialName("postalCode")
    val postalCode: String,

    @SerialName("latitude")
    val latitude: Double? = null,

    @SerialName("longitude")
    val longitude: Double? = null

)
