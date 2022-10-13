package com.ayomicakes.app.screen.checkout

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.MainViewModel

@Composable
fun Checkout(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    mainViewModel.setToolbar(
        isHidden = false,
        isActive = false,
        title = navController.currentDestination?.route ?: ""
    )
}