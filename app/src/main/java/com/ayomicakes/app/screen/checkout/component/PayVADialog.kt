@file:OptIn(ExperimentalMaterial3Api::class)

package com.ayomicakes.app.screen.checkout.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ayomicakes.app.R
import com.ayomicakes.app.component.HyperlinkText
import com.ayomicakes.app.network.responses.PaymentTransactionResponse

@Composable
fun PayVADialog(
    onDismiss: (Boolean) -> Unit,
    transactionResponse: PaymentTransactionResponse?
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
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "No. Virtual Account", fontSize = 12.sp)
                        val clipboardManager: ClipboardManager = LocalClipboardManager.current
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                                .clickable {
                                    if (transactionResponse?.vaNumber?.isNotEmpty() == true) {
                                        clipboardManager.setText(AnnotatedString(transactionResponse.vaNumber))
                                    }
                                },
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = transactionResponse?.vaNumber ?: "-")
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = Icons.Filled.CopyAll,
                                contentDescription = "Copy VA"
                            )
                        }
                        Text(text = "Jumlah Biaya", fontSize = 12.sp)
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Rp. ${transactionResponse?.amount ?: "-"}"
                        )
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
                        HyperlinkText(
                            fullText = "Buka melalui browser",
                            hyperLinks = mutableMapOf(
                                "Buka melalui browser" to "${transactionResponse?.paymentUrl}"
                            ),
                            textStyle = TextStyle(
                                textAlign = TextAlign.Center,
                                color = Gray
                            ),
                            linkTextColor = MaterialTheme.colorScheme.tertiary,
                            fontSize = 18.sp
                        )
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

    }, transactionResponse = PaymentTransactionResponse())
}

