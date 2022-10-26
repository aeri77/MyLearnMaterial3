package com.ayomicakes.app.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object Navigation {
    const val LANDING = "landing"
    const val ONBOARD = "onboard"
    const val HOME = "home"
    const val AUTH = "auth"
    const val SIGN_IN = "Login"
    const val SIGN_UP = "register"
    const val REGISTER_FORM = "register_form"
    object Checkout {
        const val ROUTE = "checkout"
        const val ID = "checkoutId"
        const val ROUTE_ITEM = "$ROUTE/{$ID}"
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
