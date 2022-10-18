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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.database.model.ReceiverAddress
import com.ayomicakes.app.screen.checkout.component.SetAddressDialog
import com.ayomicakes.app.screen.checkout.component.SetOrderDateDialog
import com.ayomicakes.app.ui.theme.Primary80
import com.ayomicakes.app.ui.theme.Tertiary60

@Composable
fun Checkout(mainViewModel: MainViewModel = hiltViewModel()) {
//    mainViewModel.setToolbar(
//        isHidden = false,
//        isActive = false,
//        title = navController.currentDestination?.route ?: ""
//    )
    var addressDialogShow by remember { mutableStateOf(false) }
    var dateDialogShow by remember { mutableStateOf(false) }

    var receiverAddress by remember {
        mutableStateOf(
            ReceiverAddress(
                "Nama Penerima", "No. Handphone", "Alamat"
            )
        )
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
            PaymentPriceDetail()
        }
        item {
            PaymentMethod()
        }
        item {
            ButtonPay()
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
fun PaymentPriceDetail() {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text("Rincian Pembayaran", fontSize = 12.sp, fontWeight = W600)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("1 barang", fontSize = 12.sp)
                Text("Rp65.000", fontSize = 12.sp)
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
fun ButtonPay() {
    Card(modifier = Modifier.padding(top = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Rp65.000")
            Button(onClick = {}) {
                Text("Bayar")
            }
        }
    }
}