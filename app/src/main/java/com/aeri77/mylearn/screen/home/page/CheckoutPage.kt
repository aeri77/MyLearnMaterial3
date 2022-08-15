package com.aeri77.mylearn.screen.home.page

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.aeri77.mylearn.MainViewModel
import com.aeri77.mylearn.screen.onboarding.component.PointDestination

@Composable
fun CheckoutPage(mainViewModel: MainViewModel) {
    mainViewModel.setToolbar(true)
    PointDestination()
}