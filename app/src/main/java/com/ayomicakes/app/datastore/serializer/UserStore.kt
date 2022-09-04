package com.ayomicakes.app.datastore.serializer

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class UserStore(
    @SerialName("username") val username : String? = null,
    @SerialName("full_name") val fullName : String? = null,
    @SerialName("address") val address : String? = null,
    @SerialName("phone") val phone : String? = null,
)
