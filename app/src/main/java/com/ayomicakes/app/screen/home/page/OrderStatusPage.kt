@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.ayomicakes.app.screen.home.page

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ayomicakes.app.network.responses.PaymentTransactionResponse
import com.ayomicakes.app.screen.checkout.component.PayVADialog
import com.ayomicakes.app.ui.theme.Primary90
import com.ayomicakes.app.ui.theme.Tertiary60

@Preview
@Composable
fun OrderStatusPage(paymentTransactionRes: PaymentTransactionResponse? = null) {
    Column {

        var showPaymentRequest by remember { mutableStateOf(false) }

        LaunchedEffect(paymentTransactionRes) {
            if (paymentTransactionRes != null) {
                showPaymentRequest = true
            }
        }

        if (showPaymentRequest) {
            PayVADialog(onDismiss = {
                showPaymentRequest = false
            }, transactionResponse = paymentTransactionRes)
        }

        LazyColumn(
            contentPadding = PaddingValues(6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(10) {
                OrderStatusCard()
            }
        }
    }
}

@Preview
@Composable
fun OrderStatusCard() {
    Card() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            val (noOrder, createdDate, status, price, pieces, divider) = createRefs()
            Text(
                modifier = Modifier.constrainAs(pieces) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(createdDate.start)
                    width = Dimension.fillToConstraints
                }, text = "6 Makanan", textAlign = TextAlign.Start, fontSize = 12.sp
            )
            DashedLine(modifier = Modifier.constrainAs(divider) {
                top.linkTo(pieces.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(noOrder.top)
                width = Dimension.fillToConstraints
            })
            Text(
                modifier = Modifier.constrainAs(noOrder) {
                    start.linkTo(parent.start)
                    top.linkTo(createdDate.top)
                    end.linkTo(status.start)
                    bottom.linkTo(status.bottom)
                    width = Dimension.fillToConstraints
                }, text = "ORDER-MKMN-07294182312207", fontSize = 14.sp, fontWeight = W600
            )
            Column(modifier = Modifier.constrainAs(price) {
                top.linkTo(noOrder.bottom)
                start.linkTo(noOrder.start)
            }) {
                Text(text = "Total Dibayar", fontSize = 12.sp)
                Text(
                    text = "Rp 20.000",
                    fontWeight = W600,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            Text(
                modifier = Modifier.constrainAs(createdDate) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }, text = "21 Oktober 2022", fontSize = 12.sp, fontStyle = FontStyle.Italic

            )
            Box(modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Primary90)
                .constrainAs(status) {
                    top.linkTo(createdDate.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(noOrder.end)
                    bottom.linkTo(price.bottom)
                }
                .padding(8.dp)) {
                Text(
                    text = "Menunggu\nPembayaran", fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DashedLine(modifier: Modifier) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = Tertiary60,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}