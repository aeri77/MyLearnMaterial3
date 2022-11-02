@file:OptIn(ExperimentalMaterial3Api::class)

package com.ayomicakes.app.screen.checkout.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ayomicakes.app.R
import com.ayomicakes.app.database.model.DataPaymentMethod

@Composable
fun PayVADialog(
    onDismiss: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss(true) }) {
        Card {
            Column(
                modifier = Modifier.padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Menunggu Pembayaran", fontSize = 18.sp)
                Text(
                    text = "Silahkan melakukan transaksi pada informasi berikut:",
                    fontSize = 12.sp
                )
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Nama Bank")
                        Image(
                            modifier = Modifier.height(26.dp),
                            painter = painterResource(id = R.drawable.ic_google_logo),
                            contentDescription = "bankName"
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "No. Virtual Account", fontSize = 12.sp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "12314123141152131")
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = Icons.Filled.CopyAll,
                                contentDescription = "Copy VA"
                            )
                        }
                        Text(text = "Jumlah Biaya", fontSize = 12.sp)
                        Text(modifier = Modifier.padding(start = 8.dp), text = "Rp. 20.000")
                    }

                }
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.height(18.dp),
                            imageVector = Icons.Filled.Link,
                            contentDescription = "bankName"
                        )
                        Text(text = "Buka melalui browser")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PayVADialogPreview() {
    PayVADialog(onDismiss = {

    })
}

