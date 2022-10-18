package com.ayomicakes.app.component.backhandler

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.ayomicakes.app.component.DefaultBackHandler
import com.ayomicakes.app.component.ExitDialog
import com.ayomicakes.app.component.RegisterExitDialog
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.home.HomePageNavigation

@Composable
fun ConfigureBackHandler(
    navController: NavHostController,
    navBackStackEntry : NavBackStackEntry?,
    onExitApp : () -> Unit
){
    when (navController.currentDestination?.route) {
        Navigation.REGISTER_FORM -> {
            DefaultBackHandler(backNavElement = RegisterExitDialog {
                navController.navigate(Navigation.HOME) {
                    popUpTo(0)
                }
            })
        }
    }
    if (navBackStackEntry?.destination?.route == HomePageNavigation.SHOPS_PAGE) {
        DefaultBackHandler(backNavElement = ExitDialog(onNavIconClicked = onExitApp))
    }
}