@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.ayomicakes.app.screen.checkout.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ayomicakes.app.R
import com.ayomicakes.app.database.model.DataPaymentMethod


@Composable
fun SetPaymentMethodDialog(
    onSave: (DataPaymentMethod) -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss(true) }) {
        Card {
            Column(
                modifier = Modifier.padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Pilih Methode Pembayaran", fontSize = 18.sp)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        errorLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    value = "",
                    onValueChange = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    })
                LazyColumn(
                    modifier = Modifier.heightIn(min = 0.dp, max = 400.dp).fillMaxWidth(),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    stickyHeader {
                        Text("Virtual Account")
                    }
                    items(100) {
                        PaymentItem(
                            onClick = {
                                onSave(DataPaymentMethod("VA", "Bank Sendiri"))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentItem(
    bankName: String = "Bank sendiri",
    bankImage: Painter = painterResource(id = R.drawable.ic_google_logo),
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable {
            onClick()
        }
    ) {
        Text(text = bankName)
        Image(modifier = Modifier.height(48.dp), painter = bankImage, contentDescription = bankName)
    }
}

@Preview
@Composable
fun PaymentDialogPreview() {
    MaterialTheme {
        SetPaymentMethodDialog(onSave = {}, onDismiss = {})
    }
}