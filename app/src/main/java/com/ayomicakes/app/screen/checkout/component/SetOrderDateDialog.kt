@file:OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class
)

package com.ayomicakes.app.screen.checkout.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.database.model.DateSend
import com.ayomicakes.app.database.model.ReceiverAddress
import com.ayomicakes.app.model.SelectableData
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.utils.IntUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun SetOrderDateDialog(
    viewModel: HomeViewModel = hiltViewModel(),
    onSave: (DateSend) -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss(true) }) {
        val locale = LocalContext.current.resources.configuration.locales.get(0)
        val date = LocalDateTime.now()
        val first = date.withDayOfMonth(1).dayOfMonth
        val last = date.withDayOfMonth(date.month.length(date.toLocalDate().isLeapYear)).dayOfMonth
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
        var hour by remember { mutableStateOf("") }
        var minute by remember { mutableStateOf("") }
        Card {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                    text = "Pilih Tanggal Pemesanan",
                    fontSize = 12.sp,
                    fontWeight = W600
                )
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

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "Masukan Jam Pemesanan",
                    fontSize = 12.sp,
                    fontWeight = W600
                )

                Row(
                    modifier = Modifier.padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextField(
                        modifier = Modifier.width(52.dp),
                        value = hour, onValueChange = {
                            hour = it
                        },
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp,
                            fontWeight = W600
                        )
                    )
                    Text(":", fontSize = 18.sp, fontWeight = W600)
                    TextField(
                        modifier = Modifier.width(52.dp),
                        value = minute,
                        onValueChange = {
                            minute = it
                        },
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp,
                            fontWeight = W600
                        )
                    )
                    Text("WIB", fontSize = 18.sp, fontWeight = W600, letterSpacing = 4.sp)
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)) {
                    Button(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = {
                            onSave(
                                DateSend(
                                    date.withDayOfMonth(selectedDate?.value ?: 1)
                                )
                            )
                        }) {
                        Text("Simpan", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
