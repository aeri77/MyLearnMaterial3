@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.ayomicakes.app.screen.home.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.R
import com.ayomicakes.app.component.NoRippleEffect
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.database.model.CartItem
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.ui.theme.Primary95
import com.ayomicakes.app.utils.Result
import java.util.*

@Composable
fun CakesPage(
    string: String?,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val cakeResponse by homeViewModel.cake.collectAsState(initial = null)

    LaunchedEffect(true) {
        homeViewModel.getCakes(UUID.fromString(string))
    }

    if (cakeResponse is Result.Success) {
        CakeContent(
            cakeItem = (cakeResponse as Result.Success<FullResponse<CakeItem>>).data.result.copy(),
            homeViewModel
        )
    }
}


@Composable
fun CakeContent(cakeItem: CakeItem, homeViewModel: HomeViewModel = hiltViewModel()) {
    Scaffold(
        containerColor = Primary95,
        bottomBar = {
            Surface(
                modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Pesan Sekarang")
                    }
                    FilledIconButton(
                        modifier = Modifier.padding(start = 4.dp),
                        onClick = {
                            homeViewModel.addToCart(1, cartItem = cakeItem)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Outlined.AddShoppingCart,
                            contentDescription = "Tambahkan Keranjang"
                        )
                    }
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.padding(it),
            contentColor = MaterialTheme.colorScheme.primary,
            color = Primary95
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item {
                    Box {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.image_sample_0),
                            contentDescription = ""
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .clip(
                                    RoundedCornerShape(
                                        topStartPercent = 100,
                                        bottomStartPercent = 100
                                    )
                                )
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(12.dp),
                                text = cakeItem.price.toString(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        verticalAlignment = CenterVertically
                    ) {
                        CompositionLocalProvider(LocalRippleTheme provides NoRippleEffect) {
                            Chip(
                                onClick = { /*TODO*/ },
                                colors = ChipDefaults.outlinedChipColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                            ) {

                                Text(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .align(CenterVertically),
                                    text = cakeItem.category ?: ""
                                )
                            }
                        }
                        Text("Stock: ${cakeItem.stock}")
                    }
                }

                item {
                    Text(
                        modifier = Modifier
                            .padding(12.dp),
                        text = cakeItem.cakeName ?: ""
                    )
                }
            }
        }
    }
}