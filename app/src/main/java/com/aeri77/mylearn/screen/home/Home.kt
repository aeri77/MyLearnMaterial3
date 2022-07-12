package com.aeri77.mylearn.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun Home(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Home")
    }
}
