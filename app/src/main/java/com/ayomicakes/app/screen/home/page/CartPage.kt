package com.ayomicakes.app.screen.home.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.R
import com.ayomicakes.app.database.model.CartItem
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.ui.theme.*

@ExperimentalMaterial3Api
@Composable
fun CartPage(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    viewModel.setToolbar(
        isHidden = false,
        isActive = true,
        title = navController.currentDestination?.route ?: ""
    )

    val cakeCart by viewModel.cakesCart.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Primary95
    ) {
        ConstraintLayout {
            val (items, checkout) = createRefs()

            LazyColumn(
                Modifier
                    .constrainAs(items) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(checkout.top)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .background(Secondary80),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
            ) {
                items(cakeCart.map { CartItem(it.value, it.key) }) { item ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {

                            val (image, close, count, itemDetail, totalPrices) = createRefs()

                            Image(
                                modifier = Modifier
                                    .constrainAs(image) {
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    }
                                    .height(111.dp),
                                painter = painterResource(id = R.drawable.image_sample_0),
                                contentDescription = ""
                            )
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .constrainAs(close) {
                                    end.linkTo(parent.end)
                                    top.linkTo(image.top)
                                }
                                .clickable {
                                    viewModel.removeFromCart(cart = item.item)
                                }) {
                                Icon(
                                    modifier = Modifier.size(18.dp),
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "remove",
                                    tint = Tertiary70
                                )
                            }
                            Column(
                                modifier = Modifier.constrainAs(itemDetail) {
                                    start.linkTo(image.end)
                                    top.linkTo(image.top)
                                    bottom.linkTo(image.bottom)
                                }
                            ) {
                                Text(
                                    text = item.item.cakeName ?: "",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W700,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = item.item.price.toString() ?: "", fontSize = 12.sp,
                                    fontWeight = FontWeight.W600,
                                    color = NeutralVariant50
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .constrainAs(totalPrices) {
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    }
                                    .padding(vertical = 12.dp)
                                    .padding(end = 6.dp),
                                text = "${item.count.toDouble() * (item.item.price ?: 0.0)}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W700,
                                color = NeutralVariant40
                            )
                            Row(
                                modifier = Modifier
                                    .constrainAs(count) {
                                        bottom.linkTo(parent.bottom)
                                        end.linkTo(parent.end)
                                    }
                                    .padding(end = 6.dp)
                                    .clip(RoundedCornerShape(20))
                                    .background(Primary95)
                                    .padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            if(item.count > 1){
                                                viewModel.removeFromCart(1, item.item)
                                            }
                                        }) {
                                    Icon(
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "decrement",
                                        tint = NeutralVariant40
                                    )
                                }
                                Text(
                                    text = item.count.toString(),
                                    fontWeight = W600,
                                    color = NeutralVariant40
                                )
                                Box(
                                    Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            viewModel.addToCart(cartItem = item.item)
                                        }) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "increment",
                                        tint = NeutralVariant40
                                    )
                                }
                            }
                        }
                    }
                }

            }
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .constrainAs(checkout) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total", fontStyle = FontStyle.Italic, fontWeight = FontWeight.W600,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${cakeCart.toList().sumOf { item -> item.second * (item.first.price ?: 0.0) }}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(onClick = {
                    navController.navigate(Navigation.CHECKOUT)
                }, shape = RoundedCornerShape(4.dp)) {
                    Text(text = "CHECKOUT")
                }
            }
        }
    }
}