package com.aeri77.mylearn.screen.home.page

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.aeri77.mylearn.MainViewModel

@Composable
fun MessagesPage(navController: NavHostController, mainViewModel: MainViewModel) {
    mainViewModel.setToolbar(true)
}