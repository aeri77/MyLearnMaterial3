package com.ayomicakes.app.screen.checkout

import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.architecture.repository.checkout.CheckoutRepository
import com.ayomicakes.app.model.CheckoutModel
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.network.requests.AddressCheckout
import com.ayomicakes.app.network.requests.CheckoutItem
import com.ayomicakes.app.network.requests.CheckoutRequest
import com.ayomicakes.app.utils.StringUtils.getBearer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: CheckoutRepository
) : MainViewModel(
    repository
) {

    fun postCheckoutRequest(checkout: CheckoutModel) {
        val listCheckoutItem = checkout.items.map {
            CheckoutItem(
                uid = UUID.fromString(it.key.uid),
                name = it.key.cakeName,
                quantity = it.value,
                totalPrice = it.value * (it.key.price ?: 0.0)
            )
        }
        viewModelScope.launch {
            repository.getProfileStore().collectLatest { profileStore ->
                repository.getUserStore().collectLatest { userStore ->
                    if (userStore?.userId != null) {
                        repository.postCheckout(
                            userStore.accessToken.toString().getBearer(),
                            CheckoutRequest(
                                userRequestId = userStore.userId,
                                additionalParam = "Payment via Mobile",
                                email = profileStore?.email ?: "",
                                customerVaName = profileStore?.fullName ?: "",
                                merchantCode = "DS13707",
                                paymentAmount = listCheckoutItem.sumOf { item ->
                                    (item.totalPrice ?: 0.0).roundToInt()
                                },
                                itemDetails = listCheckoutItem,
                                paymentMethod = "VA",
                                productDetails = "Orderan atas nama ${profileStore?.fullName}",
                                returnUrl = Navigation.Checkout.ROUTE,
                                receiverAddress = AddressCheckout(
                                    UUID.randomUUID(),
                                    "Bapak",
                                    "Budi",
                                    "Jl. Semplak",
                                    "Bogor",
                                    "088822143131",
                                    "IDN",
                                    "Bandrek",
                                    "Jawa Barat",
                                    "Indonesia",
                                    "ID",
                                    "16131",
                                    0.0,
                                    0.0,
                                ),
                                senderAddress = AddressCheckout(
                                    UUID.randomUUID(),
                                    "Kakak",
                                    "Yanto",
                                    "Jl. Pemuda",
                                    "Bandung",
                                    "0888227666661",
                                    "IDN",
                                    "Bandrek",
                                    "Jawa Barat",
                                    "Indonesia",
                                    "ID",
                                    "16161",
                                    0.0,
                                    0.0,
                                )
                            )
                        ).collectLatest {
                            Timber.d("result bayar $it")
                        }
                    }
                }
            }
        }
    }
}