package com.ayomicakes.app.screen.register.component

import android.location.Address
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import timber.log.Timber

@Preview
@Composable
fun FormRegisterPreview() {
//    Column(modifier = Modifier.fillMaxSize().background(Primary95)) {
    LazyColumn() {
        item {
            FormRegister()
        }
    }
}


@Composable
fun FormRegister(listAddress: List<Address>? = null) {
    Timber.d("address created : $listAddress")
    val firstIndexAddress = if (listAddress?.isNotEmpty() == true) listAddress[0] else null
    val address = remember { mutableStateOf("") }
    val locality = remember { mutableStateOf("") }
    val subAdmin = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }

    LaunchedEffect(listAddress) {
        Timber.d("address created : $listAddress")
        address.value = firstIndexAddress?.getAddressLine(0) ?: ""
        locality.value = firstIndexAddress?.locality ?: ""
        subAdmin.value = firstIndexAddress?.subAdminArea ?: ""
        postalCode.value = firstIndexAddress?.postalCode ?: ""
    }
    FormTextField(
        "Alamat",
        "Nama Jalan, RT/RW, No Rumah, Kelurahan...",
        maxLines = 3,
        maxLength = 300,
        textValue = address
    )
    FormTextField(
        "Kecamatan",
        "Nama Kecamatan",
        maxLines = 1,
        textValue = locality
    )
    FormTextField(
        "Kota / Kabupaten",
        "Nama Kota / Kabupaten",
        maxLines = 1,
        textValue = subAdmin
    )
    FormTextField(
        "Kode Pos",
        "6 Digit - Kodepos",
        maxLines = 1,
        textValue = postalCode
    )
    FormTextField("Nomor Handphone", "08xxxxxxx", maxLines = 1)
}

@Composable
fun FormTextField(
    hint: String = "",
    placeholder: String = "",
    textValue: MutableState<String> = mutableStateOf(""),
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    maxLength: Int = 50
) {
    Timber.d("address value = ${textValue.value}")
    Column(
        Modifier
            .wrapContentHeight()
            .width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val localFocusManager = LocalFocusManager.current
        val keyboardActions = KeyboardActions(
            onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
        )
        val isError by remember { mutableStateOf(false) }
        val errorText = remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = maxLines,
            isError = isError,
            placeholder = {
                Text(text = placeholder, color = MaterialTheme.colorScheme.outline)
            },
            label = {
                Text(text = hint)
            },
            value = textValue.value ?: "",
            onValueChange = {
                textValue.value = it.take(maxLength)
                if (it.length > maxLength) {
                    localFocusManager.moveFocus(FocusDirection.Down) // Or receive a lambda function
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
            keyboardActions = keyboardActions,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Crossfade(isError) {
                if (it) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp),
                        text = errorText.value,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            }
            if (maxLines >= 3) {
                Text("${textValue.value?.length ?: 0} / $maxLength ", fontSize = 12.sp)
            }
        }
    }
}