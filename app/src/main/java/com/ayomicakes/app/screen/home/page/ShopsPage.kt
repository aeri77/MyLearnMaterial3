package com.ayomicakes.app.screen.home.page

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.R
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.ui.theme.*
import kotlin.math.floor
import com.ayomicakes.app.utils.Result

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun ShopsPage(navController:NavHostController,mainViewModel: MainViewModel = hiltViewModel(), homeViewModel: HomeViewModel = hiltViewModel()) {
    mainViewModel.setToolbar(
        isHidden = false,
        isActive = true,
        title = navController.currentDestination?.route?.split("_")?.get(0)?.capitalize(Locale.current) ?: ""
    )
    val gridState = rememberLazyGridState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Primary95
    ) {
        LazyColumn(
            modifier = Modifier
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary95)
                ) {
                    AnimatedVisibility(
                        visible = gridState.firstVisibleItemIndex == 0,
                        enter = slideInVertically() + expandVertically(
                            // Expand from the top.
                            expandFrom = Alignment.Top
                        ) + fadeIn(
                            // Fade in with the initial alpha of 0.3f.
                            initialAlpha = 0.3f
                        ), exit = slideOutVertically() + shrinkVertically() + fadeOut()
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = buildAnnotatedString {
                                val offset = Offset(1f, 1f)
                                val shadow = Shadow(Neutral90, offset, 2f)
                                withStyle(style = ParagraphStyle(lineHeight = 32.sp)) {
                                    withStyle(
                                        style = SpanStyle(
                                            color = Primary60,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 30.sp,
                                            shadow = shadow
                                        )
                                    ) {
                                        append("Would you like\n")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.W400,
                                            color = Neutral80,
                                            fontSize = 30.sp,
                                            shadow = shadow
                                        )
                                    ) {
                                        append("to taste\n")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = Neutral10,
                                            fontSize = 30.sp,
                                            shadow = shadow
                                        )
                                    ) {
                                        append("it?")
                                    }
                                }
                            }
                        )

                    }
                    Spacer(modifier = Modifier.size(12.dp))

                    Box(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .align(
                                CenterHorizontally
                            )
                    ) {
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
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                disabledLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                errorLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
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
                    }
                }
            }
            item {

                val cakesResponse by homeViewModel.cakeResponse.collectAsState(initial = null)

                LaunchedEffect(true){
                    homeViewModel.getCakes()
                }

                val count = 20
                val height = floor((count * 240.0 / 2)).dp

                Crossfade(targetState = cakesResponse) {res ->
                    if(res is Result.Success){
                        LazyVerticalGrid(
                            state = gridState,
                            modifier = Modifier
                                .height(height)
                                .background(Primary95), columns = GridCells.Fixed(2)
                        ) {
                            items(res.data.result.perPage ?: 0) { index ->
                                Box(
                                    Modifier
                                        .width(240.dp)
                                        .height(380.dp)
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(12.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .weight(0.7f)
                                                .fillMaxWidth(),
                                            painter = painterResource(id = R.drawable.image_sample_0),
                                            contentDescription = ""
                                        )
                                        Column(
                                            modifier = Modifier
                                                .weight(0.35f)
                                                .fillMaxSize()
                                                .padding(12.dp)
                                        ) {
                                            Text(
                                                text = res.data.result.result?.get(index)?.cakeName ?: "",
                                                fontSize = 18.sp,
                                                fontWeight = W700,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "Rp${res.data.result.result?.get(index)?.price ?: ""}",
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
                                                        .fillMaxWidth()
                                                        .weight(0.7f),
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
                        }
                    }
                }
            }
        }
    }
}