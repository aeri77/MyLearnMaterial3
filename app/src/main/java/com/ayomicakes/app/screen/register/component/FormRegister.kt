package com.ayomicakes.app.screen.register.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.Placeholder
import com.ayomicakes.app.ui.theme.Primary95

@Preview
@Composable
fun FormRegisterPreview() {
//    Column(modifier = Modifier.fillMaxSize().background(Primary95)) {
    LazyColumn() {
        item {
            Box(modifier = Modifier.padding(8.dp)){
                FormRegister()
            }
        }
    }
}


@Composable
fun FormRegister(): List<Unit> {
    return listOf(
        FormTextField("Alamat", "Nama Jalan, RT/RW, No Rumah, Kelurahan..."),
        FormTextField("Kecamatan", "Nama Kecamatan"),
        FormTextField("Kota / Kabupaten", "Nama Kota / Kabupaten"),
        FormTextField("Kode Pos", "6 Digit - Kodepos"),
        FormTextField("Nomor Handphone", "08xxxxxxx")
    )
}

@Composable
fun FormTextField(hint: String = "", placeholder: String = "") {
    Column(
        Modifier.wrapContentHeight().width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isError by remember { mutableStateOf(false) }
        val textValue = remember { mutableStateOf("") }
        val errorText = remember { mutableStateOf("") }
        OutlinedTextField(
            isError = isError,
            placeholder = {
                Text(text = placeholder, color = MaterialTheme.colorScheme.outline)
            },
            label = {
                Text(text = hint)
            },
            value = textValue.value, onValueChange = {
                textValue.value = it
            })
        Crossfade(isError) {
            if (it) {
                Text(text = errorText.value, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}