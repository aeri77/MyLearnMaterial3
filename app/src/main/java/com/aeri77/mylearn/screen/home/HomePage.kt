package com.aeri77.mylearn.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomePage(val route: String, val image:ImageVector, val title: String) {
    object FirstPage : HomePage(HomePageNavigation.FIRST_PAGE, Icons.Default.Favorite, "First Page")
    object SecondPage : HomePage(HomePageNavigation.SECOND_PAGE, Icons.Default.Email, "Second Pagea")
}

object HomePageNavigation {
    const val FIRST_PAGE = "first_page"
    const val SECOND_PAGE = "second_page"
}