@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.ayomicakes.app.screen.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.database.model.CartItem
import com.ayomicakes.app.database.model.ReceiverAddress
import com.ayomicakes.app.model.CheckoutModel
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PaymentTransactionResponse
import com.ayomicakes.app.screen.checkout.component.SetAddressDialog
import com.ayomicakes.app.screen.checkout.component.SetOrderDateDialog
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.ui.theme.Primary80
import com.ayomicakes.app.ui.theme.Tertiary60
import com.ayomicakes.app.utils.Result
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun Checkout(
    checkoutId: String?,
    homeViewModel: HomeViewModel = hiltViewModel(),
    checkoutViewModel: CheckoutViewModel = hiltViewModel(),
    onSuccessCreateTransaction : (String?) -> Unit
) {
    val checkout by homeViewModel.getCheckout(checkoutId ?: "").observeAsState()
    val paymentReq by checkoutViewModel.transactionResponse.collectAsState(initial = null)
    var addressDialogShow by remember { mutableStateOf(false) }
    var dateDialogShow by remember { mutableStateOf(false) }
    var receiverAddress by remember {
        mutableStateOf(
            ReceiverAddress(
                "Nama Penerima", "No. Handphone", "Alamat"
            )
        )
    }

    LaunchedEffect(key1 = true) {
        Timber.d("checkout item : $checkout")
    }

    LaunchedEffect(paymentReq) {
        Timber.d("Payment Req status : $paymentReq")
        if (paymentReq != null) {
            if (paymentReq is Result.Success) {
                homeViewModel.setTransactionRequest(
                    (paymentReq as Result.Success<FullResponse<PaymentTransactionResponse>>).data.result.reference,
                    (paymentReq as Result.Success<FullResponse<PaymentTransactionResponse>>).data.result
                )
                onSuccessCreateTransaction((paymentReq as Result.Success<FullResponse<PaymentTransactionResponse>>).data.result.reference)
            }
        }
    }

    if (addressDialogShow) {
        SetAddressDialog(
            onDismiss = {
                addressDialogShow = !it
            },
            onSave = {
                receiverAddress = it
                addressDialogShow = false
            }
        )
    }

    if (dateDialogShow) {
        SetOrderDateDialog(onSave = {

        }, onDismiss = {
            dateDialogShow = !it
        })
    }


    LazyColumn(
        contentPadding = PaddingValues(12.dp)
    ) {
        item {
            AddressSend(receiverAddress) {
                addressDialogShow = true
            }
        }
        item {
            OrderQueue(dateDialogShow) {
                dateDialogShow = true
            }
        }
        item {
            PaymentPriceDetail(checkout)
        }
        item {
            PaymentMethod()
        }
        item {
            ButtonPay(checkout, checkoutViewModel)
        }
    }
}

@Composable
fun AddressSend(receiverAddress: ReceiverAddress, onChangeAddress: () -> Unit) {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Alamat Pengiriman", fontSize = 12.sp, fontWeight = W600)
            Text("${receiverAddress.name} - ${receiverAddress.phone}", fontSize = 12.sp)
            Text("${receiverAddress.address}", fontSize = 12.sp, lineHeight = 14.sp)
            OutlinedButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onChangeAddress,
                border = BorderStroke(0.dp, Color.Transparent),
                contentPadding = PaddingValues(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "edit"
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Rubah Alamat", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun PaymentPriceDetail(checkout: CheckoutModel?) {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text("Rincian Pembayaran", fontSize = 12.sp, fontWeight = W600)
            if (checkout != null) {
                val listItem = checkout.items.map { CartItem(it.value, it.key) }
                repeat(listItem.size) { idx ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${listItem[idx].count} ${listItem[idx].item.cakeName}",
                            fontSize = 12.sp
                        )
                        Text(
                            "Rp ${(listItem[idx].count * (listItem[idx].item.price ?: 0.0)).roundToInt()}",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentMethod() {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Methode Pembayaran", fontSize = 12.sp, fontWeight = W600)
            Text("-- Pilih Metode Pembayaran --", fontSize = 12.sp)

        }
    }
}

@Composable
fun OrderQueue(dateDialogShow: Boolean, onDateChange: () -> Unit) {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Jadwal Pemesanan", fontSize = 12.sp, fontWeight = W600)
            OutlinedCard(
                onClick = onDateChange, colors = CardDefaults.outlinedCardColors(
                    containerColor = Primary80,
                    contentColor = Tertiary60
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = " -- Pilih Tanggal Pemensanan -- ",
                        fontSize = 12.sp
                    )
                }
            }
            Row() {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Peringatan"
                )
                Spacer(Modifier.size(6.dp))
                Text("Pemesanan pada hari ini sedang Penuh", fontSize = 12.sp)
            }
            OutlinedButton(modifier = Modifier.align(Alignment.End), onClick = {
            }) {
                Text("Cek Antrian Pemesanan", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ButtonPay(checkout: CheckoutModel?, checkoutViewModel: CheckoutViewModel = hiltViewModel()) {
    Card(modifier = Modifier.padding(top = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (checkout != null) {
                Text(
                    "Rp ${
                        checkout.items.toList()
                            .sumOf { item -> item.second * (item.first.price ?: 0.0) }.roundToInt()
                    }"
                )
                Button(onClick = {
                    checkoutViewModel.postCheckoutRequest(checkout)
                }) {
                    Text("Bayar")
                }
            }
        }
    }
}