@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.ayomicakes.app.screen.checkout.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ayomicakes.app.R
import com.ayomicakes.app.screen.register.MapScreen


@Composable
fun SetAddressDialog(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {

        var mapShow by remember { mutableStateOf(false) }
        var bounded by remember {mutableStateOf(false)}

        Card() {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Crossfade(mapShow) {
                    if(it){
                        MapScreen(
                            isBound = { isBound ->
                                bounded = isBound
                            }
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(24.dp))
                        ) {
                            Image(modifier = Modifier.fillMaxSize(),painter = painterResource(id = R.drawable.dummy_image_maps_blur),
                                contentScale = ContentScale.Crop,
                                contentDescription = "maps dummy")
                            Button(modifier = Modifier.align(Alignment.Center), onClick = { mapShow = true }) {
                                Text("Gunakan Google Map")
                            }
                        }
                    }
                }
                Text("Apakah di luar jangkauan : $bounded")
                Text("Pilih alamat tersimpan")
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    item(20) {
                        Chip(onClick = { /*TODO*/ }) {
                            Text("Alamat")
                        }
                    }
                }
            }
        }
    }
}