package com.ayomicakes.app.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
@Parcelize
data class CakeItem(

    @SerialName("uid")
    val uid: String,

    @SerialName("price")
    val price: Double? = null,

    @SerialName("cakeName")
    val cakeName: String? = null,

    @SerialName("category")
    val category: String? = null,

    @SerialName("stock")
    val stock: Int? = null,

    @SerialName("imgUrl")
    val imgUrl: String? = null
) : Parcelable