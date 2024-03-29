package com.ayomicakes.app.screen.landing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.delay
import timber.log.Timber


@Composable
fun Landing(
    account: GoogleSignInAccount?,
    mainViewModel: MainViewModel = hiltViewModel(),
    onLandingSuccessful: () -> Unit,
    onLandingFailed: () -> Unit
) {

    val userStore by mainViewModel.userStore.observeAsState()

//    mainViewModel.setToolbar(
//        isHidden = true,
//        isActive = false,
//        title = navController.currentDestination?.route ?: ""
//    )

    LaunchedEffect(userStore) {
        delay(1700)
        if (userStore?.userId?.toString()?.isNotBlank() == true || account != null) {
            onLandingSuccessful()
            return@LaunchedEffect
        }
        onLandingFailed()
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.Center),
                imageVector = Icons.Filled.Cake,
                contentDescription = "landing icon"
            )
        }
    }
}

@Preview
@Composable
fun LandingPreview() {
    Landing(account = null, onLandingFailed = {}, onLandingSuccessful = {})
}