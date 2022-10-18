@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class
)

package com.ayomicakes.app.screen.checkout.component

import android.Manifest
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.R
import com.ayomicakes.app.database.model.ReceiverAddress
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.screen.register.MapScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@Composable
fun SetAddressDialog(
    viewModel: HomeViewModel = hiltViewModel(),
    onSave: (ReceiverAddress) -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = { }
    ) {

        val profileStore by viewModel.getUserAddress.collectAsState(initial = null)

        var mapShow by remember { mutableStateOf(false) }
        var bounded by remember { mutableStateOf(false) }
        var receiverName by remember { mutableStateOf(profileStore?.fullName ?: "") }
        var receiverPhone by remember {
            mutableStateOf(
                profileStore?.addresses?.get(0)?.phone ?: ""
            )
        }
        var addressRes by remember { mutableStateOf("") }

        val location by viewModel.location.collectAsState(initial = null)
        val permissions = rememberMultiplePermissionsState(
            permissions =
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        LaunchedEffect(location) {
            if (location != null) {
                mapShow = true
            }
        }
        Card(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Column {
                Box() {
                    Crossfade(mapShow) {
                        if (it) {
                            MapScreen(
                                defaultLoc = location,
                                isBound = { isBound ->
                                    bounded = isBound
                                }, addressResult = { address ->
                                    addressRes = if (address != null) {
                                        address.single().getAddressLine(0)
                                    } else "Tidak ditemukan"
                                }
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(24.dp))
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.dummy_image_maps_blur),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "maps dummy"
                                )
                                Button(
                                    modifier = Modifier.align(Alignment.Center),
                                    onClick = {
                                        permissions.permissions.forEach { permission ->
                                            when (permission.status) {
                                                is PermissionStatus.Denied -> {
                                                    permissions.launchMultiplePermissionRequest()
                                                }
                                                else -> {
                                                    viewModel.getLocation()
                                                }
                                            }

                                        }
                                    }) {
                                    Text("Gunakan Google Map")
                                }
                            }
                        }
                    }
                    OutlinedButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { onDismiss(true) },
                        contentPadding = PaddingValues(
                            top = 6.dp,
                            bottom = 6.dp,
                            end = 12.dp,
                            start = 6.dp
                        ),
                        border = BorderStroke(
                            0.dp,
                            Color.Transparent
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ChevronLeft,
                            contentDescription = "close address"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Kembali", fontSize = 12.sp)
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(12.dp)
                ) {

                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Alamat dipilih",
                                    fontSize = 12.sp,
                                    fontWeight = W600
                                )
                            },
                            textStyle = TextStyle(fontSize = 12.sp),
                            value = addressRes,
                            onValueChange = {
                                addressRes = it
                            })
                    }
                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Nama Penerima",
                                    fontSize = 12.sp,
                                    fontWeight = W600
                                )
                            },
                            textStyle = TextStyle(fontSize = 12.sp),
                            value = receiverName,
                            onValueChange = {
                                receiverName = it
                            })
                    }
                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "No. Handphone Penerima",
                                    fontSize = 12.sp,
                                    fontWeight = W600
                                )
                            },
                            textStyle = TextStyle(fontSize = 12.sp),
                            value = receiverPhone,
                            onValueChange = {
                                receiverPhone = it
                            })
                    }
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Chip(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                onClick = { /*TODO*/ }) {
                                Crossfade(targetState = bounded) {
                                    if (it) {
                                        Text(text = "Terjangkau", fontSize = 12.sp)
                                    } else {
                                        Text("Di Luar Jangkauan", fontSize = 12.sp)
                                    }
                                }

                            }
                        }
                    }
                    item {
                        Text(
                            "Pilih alamat tersimpan", fontSize = 12.sp, fontWeight = W600
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (profileStore?.addresses?.isNotEmpty() == true) {
                                items(profileStore?.addresses!!) { item ->
                                    Chip(onClick = {
                                        addressRes = item.address
                                        receiverPhone = item.phone ?: ""
                                    }) {
                                        Text(item.subAdminArea, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                modifier = Modifier.align(Alignment.Center),
                                onClick = {
                                    onSave(
                                        ReceiverAddress(
                                            receiverName,
                                            receiverPhone,
                                            addressRes
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
    }
}