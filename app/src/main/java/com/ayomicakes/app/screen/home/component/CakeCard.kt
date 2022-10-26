@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.ayomicakes.app.screen.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayomicakes.app.R
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.screen.home.HomePageNavigation

@Composable
fun CakeCard(cakeItem: CakeItem?, onClicked : () -> Unit) {
    Box(
        Modifier
            .width(240.dp)
            .height(380.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clickable {
                   onClicked()
                },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.image_sample_0),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Text(
                    text = cakeItem?.cakeName
                        ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Rp ${cakeItem?.price ?: ""}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("BUY")
                    }
                    FilledIconButton(
                        modifier = Modifier.padding(start = 4.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Outlined.AddShoppingCart,
                            contentDescription = "add to cart"
                        )
                    }
                }
            }
        }
    }
}