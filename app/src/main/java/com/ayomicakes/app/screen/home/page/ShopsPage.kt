package com.ayomicakes.app.screen.home.page

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.screen.home.Screens
import com.ayomicakes.app.screen.home.component.CakeList
import com.ayomicakes.app.ui.theme.*

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun ShopsPage(viewModel: HomeViewModel = hiltViewModel(), onItemClicked : (CakeItem?) -> Unit) {
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
                CakeList(
                    gridState = gridState,
                    lazyCakeItems = viewModel.cakes.collectAsLazyPagingItems()
                ){ cake ->
                    onItemClicked(cake)
                }
            }
        }
    }
}