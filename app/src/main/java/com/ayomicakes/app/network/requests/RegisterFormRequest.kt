package com.ayomicakes.app.network.requests

import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RegisterFormRequest(

    @SerialName("userId")
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,

    @SerialName("address")
    val address: String,

    @SerialName("locality")
    val locality: String,

    @SerialName("subAdminArea")
    val subAdminArea: String,

    @SerialName("adminArea")
    val adminArea: String,

    @SerialName("countryName")
    val countryName: String,

    @SerialName("countryID")
    val countryID: String,

    @SerialName("postalCode")
    val postalCode: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("phone")
    val phone: String
)
