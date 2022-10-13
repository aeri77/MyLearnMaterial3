@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.ayomicakes.app.component.scaffold

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayomicakes.app.component.AppBar
import com.ayomicakes.app.component.MainAnimatedHost
import com.ayomicakes.app.component.enums.TopAppBar
import com.ayomicakes.app.component.snackbar.MainSnackBar
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.navigation.navigateSingleTopTo
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
    val cartCount by homeViewModel.cakesCart.collectAsState(null)
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
                    BadgedBox(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .let {
                            if ((cartCount?.toList()?.size ?: 0) <= 0) {
                                it.clip(CircleShape)
                            } else it
                        }.clickable {
                            if (navController.currentDestination?.parent?.route == Navigation.HOME) {
                                navController.navigateSingleTopTo(HomePageNavigation.CART_PAGE)
                            }
                        }
                        .padding(6.dp), badge = {
                        if ((cartCount?.toList()?.size ?: 0) > 0) {
                            Badge {
                                Text("${cartCount?.toList()?.size}")
                            }
                        }
                    }) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Outlined.ShoppingBasket,
                            contentDescription = "cart",
                            tint = MaterialTheme.colorScheme.primary
                        )
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