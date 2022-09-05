package com.ayomicakes.app.screen.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ayomicakes.app.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun RegisterForm(navController: NavHostController,
//                 mainViewModel: MainViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val mainColor = MaterialTheme.colorScheme.primary
    val onMainColor = MaterialTheme.colorScheme.onPrimary

    systemUiController.setStatusBarColor(mainColor)
//    mainViewModel.setToolbar(
//        isHidden = false,
//        isActive = false,
//        title = navController.currentDestination?.route?.split("_")?.get(0)?.capitalize(Locale.current) ?: ""
//    )
    val defaultLoc = LatLng(-6.598268, 106.799374)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLoc, 12f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Circle(center = defaultLoc, radius = 4.7 * 1000, strokeColor = Color.Green.copy(alpha = 0.6f), visible = true, fillColor =  Color.Green.copy(alpha = 0.2f))
    }
}

@Preview
@Composable
fun RegisterFormPreview(){
    RegisterForm(navController = rememberNavController())
}