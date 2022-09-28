package com.ayomicakes.app.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(

    @SerialName("id")
    val id: Int,

    @SerialName("user")
    val email: String,

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
    val countryID: String?= null,

    @SerialName("postalCode")
    val postalCode: String,

    @SerialName("latitude")
    val latitude: Double? = null,

    @SerialName("longitude")
    val longitude: Double? = null
)
