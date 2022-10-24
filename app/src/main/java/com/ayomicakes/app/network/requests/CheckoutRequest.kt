package com.ayomicakes.app.network.requests

import com.ayomicakes.app.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class CheckoutRequest(
    @Serializable(with = UUIDSerializer::class)
    val userRequestId: UUID,
    val merchantCode: String,
    val itemDetails: List<CheckoutItem>? = null,
    val senderAddress: AddressCheckout? = null,
    val receiverAddress: AddressCheckout? = null,
    val productDetails: String,
    val paymentAmount: Int,
    val email: String,
    val customerVaName: String,
    val paymentMethod: String,
    val returnUrl: String? = null,
    val additionalParam: String? = null
)

@Serializable
data class CheckoutItem(
    @Serializable(with = UUIDSerializer::class)
    val uid: UUID,
    val quantity: Int? = null,
    val totalPrice: Double? = null,
    val name: String? = null
)

@Serializable
data class AddressCheckout(
    @Serializable(with = UUIDSerializer::class)
    val uid: UUID? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val address: String? = null,
    val city: String? = null,
    val phone: String? = null,
    val locality: String? = null,
    val subAdminArea: String? = null,
    val adminArea: String? = null,
    val countryName: String? = null,
    val countryID: String? = null,
    val postalCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)