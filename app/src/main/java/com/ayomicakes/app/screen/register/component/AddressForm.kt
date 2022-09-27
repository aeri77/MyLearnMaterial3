package com.ayomicakes.app.screen.register.component

import android.location.Address
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.screen.register.RegisterViewModel
import com.ayomicakes.app.ui.theme.Tertiary70
import timber.log.Timber

@Preview
@Composable
fun FormRegisterPreview() {
//    Column(modifier = Modifier.fillMaxSize().background(Primary95)) {
    LazyColumn() {
        item {
            AddressForm()
        }
    }
}

enum class AutoTextColor {
    NONE,
    UPDATE
}

@Composable
fun AddressForm(
    listAddress: List<Address>? = null,
    viewModel: RegisterViewModel = hiltViewModel(),
    address: MutableState<String> = remember { mutableStateOf("") },
    locality: MutableState<String> = remember { mutableStateOf("") },
    subAdmin: MutableState<String> = remember { mutableStateOf("") },
    postalCode: MutableState<String> = remember { mutableStateOf("") }
) {
    val firstIndexAddress = if (listAddress?.isNotEmpty() == true) listAddress[0] else null
    val autoTextColor by viewModel.autoTextColor.observeAsState()
    val colorAnim by animateColorAsState(
        targetValue =
        if (autoTextColor == AutoTextColor.NONE) {
            Color.Black
        } else {
            Tertiary70
        },
        animationSpec = tween(
            durationMillis = 700
        )
    )
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
        textValue = address,
        borderColor = if (address.value.isNotBlank()) colorAnim else Color.Black
    )
    FormTextField(
        "Kecamatan",
        "Nama Kecamatan",
        maxLines = 1,
        textValue = locality,
        borderColor = if (locality.value.isNotBlank()) colorAnim else Color.Black
    )
    FormTextField(
        "Kota / Kabupaten",
        "Nama Kota / Kabupaten",
        maxLines = 1,
        textValue = subAdmin,
        borderColor = if (subAdmin.value.isNotBlank()) colorAnim else Color.Black
    )
    FormTextField(
        "Kode Pos",
        "6 Digit - Kodepos",
        maxLines = 1,
        textValue = postalCode,
        borderColor = if (postalCode.value.isNotBlank()) colorAnim else Color.Black
    )
}

@Composable
fun FormTextField(
    hint: String = "",
    placeholder: String = "",
    textValue: MutableState<String> = mutableStateOf(""),
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    maxLength: Int = 50,
    borderColor: Color = Color.Black
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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = borderColor
            ),
            value = textValue.value,
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
                Text("${textValue.value.length} / $maxLength ", fontSize = 12.sp)
            }
        }
    }
}