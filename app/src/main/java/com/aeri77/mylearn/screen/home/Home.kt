package com.aeri77.mylearn.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationDestination {

    val route: String

}

sealed class Screens(override val route: String, var image:ImageVector? = null, var title: String = "") : NavigationDestination {

    object ShopsPage : Screens(HomePageNavigation.SHOPS_PAGE, Icons.Filled.Store, "Shops")
    object MessagesPage : Screens(HomePageNavigation.MESSAGES_PAGE, Icons.Filled.Sms, "Messages")
    object CartPage : Screens(HomePageNavigation.CART_PAGE, Icons.Outlined.ShoppingBasket, "My Cart")
    object OrderHistoryPage : Screens(HomePageNavigation.CART_PAGE, Icons.Default.Email, "Order History")
    object SettingsPage : Screens(HomePageNavigation.CART_PAGE, Icons.Default.Email, "Sett")

}


object HomePageNavigation {
    const val SHOPS_PAGE = "shops_page"
    const val CART_PAGE = "cart_page"
    const val MESSAGES_PAGE = "messages_page"
}