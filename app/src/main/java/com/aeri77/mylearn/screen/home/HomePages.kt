package com.aeri77.mylearn.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomePages(val route: String, val image:ImageVector, val title: String) {
    object ShopsPage : HomePages(HomePageNavigation.SHOPS_PAGE, Icons.Default.Favorite, "First Page")
    object SecondPage : HomePages(HomePageNavigation.SECOND_PAGE, Icons.Default.Email, "Second Pagea")
}

object HomePageNavigation {
    const val SHOPS_PAGE = "shops_page"
    const val SECOND_PAGE = "second_page"
}