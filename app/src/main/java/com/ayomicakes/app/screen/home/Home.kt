package com.ayomicakes.app.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FactCheck
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationDestination {

    val route: String

}

sealed class Screens(override val route: String, var image:ImageVector? = null, var title: String = "") : NavigationDestination {

    object ShopsPage : Screens(HomePageNavigation.SHOPS_PAGE, Icons.Filled.Store, "Shops")
    object MessagesPage : Screens(HomePageNavigation.MESSAGES_PAGE, Icons.Filled.Sms, "Messages")
    object CartPage : Screens(HomePageNavigation.CART_PAGE, Icons.Outlined.ShoppingBasket, "My Cart")
    object OrderStatus : Screens(HomePageNavigation.ORDER_STATUS_PAGE, Icons.Outlined.FactCheck, "Order Status")
    object OrderHistoryPage : Screens(HomePageNavigation.CART_PAGE, Icons.Default.Email, "Order History")
    object SettingsPage : Screens(HomePageNavigation.CART_PAGE, Icons.Default.Email, "Sett")

}


object HomePageNavigation {
    const val SHOPS_PAGE = "shops-page"
    const val CART_PAGE = "cart-page"
    const val MESSAGES_PAGE = "messages-page"
    const val CAKES_PAGE = "cakes-page"
    const val CAKES_ID = "cakesId"
    const val ORDER_STATUS_PAGE = "order-status-page"

    const val CAKES_ITEM = "$CAKES_PAGE/{$CAKES_ID}"
    const val ORDER_ID = "orderId"
    const val ORDER_STATUS_SHOW_ON_REQUEST = "showOnRequest={$ORDER_ID}"
    const val ORDER_STATUS_OPTIONAL_SHOW = "$ORDER_STATUS_PAGE?$ORDER_STATUS_SHOW_ON_REQUEST"
}