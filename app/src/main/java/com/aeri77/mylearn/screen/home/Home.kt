package com.aeri77.mylearn.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.aeri77.mylearn.navigation.Navigation

interface NavigationDestination {

    val route: String

}

sealed class Screens(override val route: String, var image:ImageVector? = null, var title: String = "") : NavigationDestination {

    object ShopsPage : Screens(HomePageNavigation.SHOPS_PAGE, Icons.Default.Favorite, "Shops")
    object MessagesPage : Screens(HomePageNavigation.MESSAGES_PAGE, Icons.Default.Favorite, "Messages")
    object CheckoutPage : Screens(HomePageNavigation.CHECKOUT_PAGE, Icons.Default.Email, "Checkout")
    object OrderHistoryPage : Screens(HomePageNavigation.CHECKOUT_PAGE, Icons.Default.Email, "Order History")
    object SettingsPage : Screens(HomePageNavigation.CHECKOUT_PAGE, Icons.Default.Email, "Sett")

}


object HomePageNavigation {
    const val SHOPS_PAGE = "shops_page"
    const val CHECKOUT_PAGE = "checkout_page"
    const val MESSAGES_PAGE = "messages_page"
}