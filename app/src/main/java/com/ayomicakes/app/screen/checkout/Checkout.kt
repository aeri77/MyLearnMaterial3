@file:OptIn(ExperimentalMaterial3Api::class)

package com.ayomicakes.app.screen.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.screen.checkout.component.SetAddressDialog

@Composable
fun Checkout(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    mainViewModel.setToolbar(
        isHidden = false,
        isActive = false,
        title = navController.currentDestination?.route ?: ""
    )
    var dialogState by remember { mutableStateOf(false) }

    if(dialogState){
        SetAddressDialog {
            dialogState = false
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(12.dp)
    ) {
        item {
            AddressSend(){
                dialogState = true
            }
        }
        item {
            OrderQueue()
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
fun AddressSend(onChangeAddress : () -> Unit) {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text("Alamat Pengiriman", fontSize = 12.sp, fontWeight = W600)
            Text("Nama Orang - 088219615853", fontSize = 12.sp)
            Text("Jl. Pemuda no. 7, Bogor Utara, Kota Bogor", fontSize = 12.sp)
            OutlinedButton(modifier = Modifier.align(Alignment.End), onClick = onChangeAddress) {
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
                .padding(12.dp)
        ) {
            Text("Methode Pembayaran", fontSize = 12.sp, fontWeight = W600)
            Text("-- Pilih Metode Pembayaran --", fontSize = 12.sp)

        }
    }
}

@Composable
fun OrderQueue() {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text("Status Antrian Pemesanan", fontSize = 12.sp, fontWeight = W600)
            Text("-- Pilih Tanggal Pemensanan --", fontSize = 12.sp)
            Text("Pemesanan pada hari ini sedang Penuh", fontSize = 12.sp)
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