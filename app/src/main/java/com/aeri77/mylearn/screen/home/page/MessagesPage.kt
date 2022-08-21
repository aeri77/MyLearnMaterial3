package com.aeri77.mylearn.screen.home.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.navigation.NavHostController
import com.aeri77.mylearn.MainViewModel

@Composable
fun MessagesPage(navController: NavHostController, mainViewModel: MainViewModel) {
    mainViewModel.setToolbar(
        isHidden = false,
        isActive = true,
        title = navController.currentDestination?.route?.split("_")?.get(0)?.capitalize(Locale.current) ?: ""
    )
}