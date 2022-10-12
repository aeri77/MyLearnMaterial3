@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.ayomicakes.app.component.scaffold

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayomicakes.app.component.AppBar
import com.ayomicakes.app.component.MainAnimatedHost
import com.ayomicakes.app.component.enums.TopAppBar
import com.ayomicakes.app.component.snackbar.MainSnackBar
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScaffold(
    isToolbarHidden: Boolean,
    drawerState: DrawerState,
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val cartCount by remember { homeViewModel.cakesCart }
    val scope = rememberCoroutineScope()
    val toolbarTitle by homeViewModel.toolbarTitle.collectAsState()
    val selectedItem = homeViewModel.selectedItems
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(topBar = {
        if (!isToolbarHidden) {
            AppBar(title = toolbarTitle, actions = {
                if (navController.currentDestination?.route == selectedItem.value.route) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }, onActions = {
                when (navController.currentDestination?.route) {
                    selectedItem.value.route -> {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                    Navigation.REGISTER_FORM -> {
                        (context as ComponentActivity).onBackPressedDispatcher.onBackPressed()
                    }
                    else -> {
                        navController.navigateUp()
                    }
                }
            }, trailingIcons = {
                if (navController.currentDestination?.parent?.route == Navigation.HOME) {
                    BadgedBox(badge = {
                        if (cartCount.size != 0) {
                            Badge { Text("8") }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBasket,
                            contentDescription = "cart",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }, trailingOnActions = {
                if (navController.currentDestination?.parent?.route == Navigation.HOME) {
                    navController.navigate(HomePageNavigation.CART_PAGE) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }, topAppbar = TopAppBar.CenterAligned
            )
        }
    },
        snackbarHost = {
            when (navBackStackEntry.value?.destination?.parent?.route) {
                Navigation.HOME -> {
                    MainSnackBar(homeViewModel)
                }
            }
        }) {
        MainAnimatedHost(it, navController, homeViewModel)
    }
}