package com.ayomicakes.app.model

import android.os.Parcelable
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.parcelize.Parcelize
import java.util.*

@kotlinx.serialization.Serializable
@Parcelize
data class CheckoutModel (
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
    val checkoutId: UUID,
    val items : Map<CakeItem, Int>
) : Parcelable