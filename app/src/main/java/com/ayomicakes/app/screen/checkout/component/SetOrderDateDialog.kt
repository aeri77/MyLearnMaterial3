@file:OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)

package com.ayomicakes.app.screen.checkout.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.database.model.ReceiverAddress
import com.ayomicakes.app.model.SelectableData
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.utils.IntUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.math.MathUtils.lerp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import kotlin.math.absoluteValue

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun SetOrderDateDialog(
    viewModel: HomeViewModel = hiltViewModel(),
    onSave: (ReceiverAddress) -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss(true) }) {
        val locale = LocalContext.current.resources.configuration.locales.get(0)
        val date = LocalDate.now()
        val first = date.withDayOfMonth(1).dayOfMonth
        val last = date.withDayOfMonth(date.month.length(date.isLeapYear)).dayOfMonth
        val rangeDate = IntUtils.getDayOfMonthRange(first, last).map {
            if (it in 1..10) {
                SelectableData(false, it)
            } else {
                SelectableData(true, it)
            }
        }
        var selectedDate by remember { mutableStateOf(rangeDate.find { item -> item.value == date.dayOfMonth }) }
        val listState = rememberLazyListState()
        LaunchedEffect(selectedDate) {
            if (selectedDate != null) {
                listState.animateScrollToItem(rangeDate.indexOf(selectedDate))
            }
        }
        Card {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text("Pilih Tanggal")
                LazyRow(
                    modifier = Modifier.padding(vertical = 24.dp),
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    flingBehavior = rememberSnapperFlingBehavior(
                        lazyListState = listState,
                        snapOffsetForItem = SnapOffsets.Start,
                    )
                ) {
                    items(rangeDate) { data ->
                        val animatedFloat by animateFloatAsState(targetValue = 1f * if (selectedDate == data) 1.3f else 1f)
                        Box(
                        ) {
                            Card(modifier = Modifier
                                .align(Alignment.Center)
                                .scale(animatedFloat),
                                colors = if (data.isSelected) {
                                    CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )

                                } else {
                                    CardDefaults.cardColors(
                                        containerColor = Color.Gray
                                    )
                                },
                                onClick = {
                                    selectedDate = data
                                }) {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .width(72.dp),
                                    horizontalAlignment = CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = date.withDayOfMonth(data.value).month.getDisplayName(
                                            TextStyle.FULL,
                                            locale
                                        ),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = 10.sp
                                    )
                                    Text(
                                        text = "${data.value}",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = W600,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = date.withDayOfMonth(data.value).dayOfWeek.getDisplayName(
                                            TextStyle.FULL,
                                            locale
                                        ),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Text(text = "${date.dayOfMonth}")
                Text("${listState.firstVisibleItemIndex}")
                Text("${listState.firstVisibleItemScrollOffset}")
            }
        }
    }
}
