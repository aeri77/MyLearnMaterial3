package com.aeri77.mylearn.screen.home.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.buildAnnotatedString
import com.aeri77.mylearn.R
import com.aeri77.mylearn.ui.theme.*
import kotlin.math.floor

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun ShopsPage(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Primary99
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
                val count = 100
                val height = floor((count * 240.0 / 2)).dp
                LazyVerticalGrid(
                    modifier = Modifier
                        .height(height)
                        .background(Primary95), columns = GridCells.Fixed(2)
                ) {
                    items(count) {
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
                                        .weight(0.3f)
                                        .fillMaxSize()
                                        .padding(12.dp)
                                ) {
                                    Text(text = "Cake Name")
                                    Text(
                                        text = "Rp.20.000"
                                    )
                                    Box(
                                        Modifier.fillMaxSize(),
                                    ) {
                                        Button(
                                            modifier = Modifier
                                                .align(CenterEnd)
                                                .padding(top = 8.dp),
                                            onClick = {},
                                            elevation = ButtonDefaults.elevatedButtonElevation(
                                                4.dp
                                            )

                                        ) {
                                            Box(modifier = Modifier.fillMaxSize()) {
                                                Icon(
                                                    modifier = Modifier.align(Center),
                                                    imageVector = Icons.Filled.ShoppingCart,
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