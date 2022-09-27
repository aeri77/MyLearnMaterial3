package com.ayomicakes.app.screen.register.component

import android.location.Address
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.oauth.GoogleOauth
import com.ayomicakes.app.screen.register.RegisterViewModel
import com.ayomicakes.app.ui.theme.Tertiary70
import com.google.android.gms.auth.api.signin.GoogleSignIn
import timber.log.Timber

@Composable
fun UserForm(
    viewModel: RegisterViewModel = hiltViewModel(),
    fullName : MutableState<String> = remember { mutableStateOf("") },
    phone : MutableState<String> = remember { mutableStateOf("") }

) {
    val context = LocalContext.current
    val account = GoogleSignIn.getLastSignedInAccount(context)
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

    LaunchedEffect(account) {
        if(account != null){
            fullName.value = "${account.givenName} ${account.familyName}"
        }
    }

    FormTextField(
        "Nama Lengkap",
        "Masukan Nama Lengkap",
        maxLines = 1,
        textValue = fullName,
        borderColor = if (account != null) colorAnim else Color.Black
    )
    FormTextField(
        "No. Handphone",
        "088xxxxxx",
        maxLines = 1,
        textValue = phone
    )
}