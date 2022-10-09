package com.ayomicakes.app.screen.home.page

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun CakesPage(navController: NavHostController, string: String?) {
    Surface {
        Text(string ?: "")
    }
}