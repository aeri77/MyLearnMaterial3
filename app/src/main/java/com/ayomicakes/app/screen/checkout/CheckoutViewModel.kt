package com.ayomicakes.app.screen.checkout

import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.architecture.repository.checkout.CheckoutRepository
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

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: CheckoutRepository
) : MainViewModel(
    repository
) {

    fun postCheckoutRequest() {
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
                                paymentAmount = 50000,
                                itemDetails = listOf(
                                    CheckoutItem(
                                        name = "Brownis Panggang",
                                        uid = UUID.fromString("50a660b4-2e64-4b47-bf5d-d3e577d38605"),
                                        totalPrice = 50000.00,
                                        quantity = 2
                                    )
                                ),
                                paymentMethod = "VA",
                                productDetails = "Orderan atas nama ${profileStore?.fullName}",
                                returnUrl = Navigation.CHECKOUT,
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