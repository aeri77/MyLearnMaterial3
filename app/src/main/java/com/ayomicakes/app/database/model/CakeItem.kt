package com.ayomicakes.app.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "cakes")
@kotlinx.serialization.Serializable
data class CakeItem(

    @PrimaryKey
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
)
