package com.aeri77.mylearn.screen.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.component.AppBar
import com.aeri77.mylearn.component.DefaultBackHandler
import com.aeri77.mylearn.component.ExitDialog
import com.aeri77.mylearn.component.enums.TopAppBar
import com.aeri77.mylearn.navigation.Navigation
import com.aeri77.mylearn.screen.landing.Landing
import com.aeri77.mylearn.screen.signin.SignIn
import com.aeri77.mylearn.screen.signup.SignUp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import timber.log.Timber


@ExperimentalPagerApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun Home(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItem = remember { mutableStateOf(items[0]) }

    DefaultBackHandler(backNavElement = ExitDialog {

    })

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            items.forEach { item ->
                NavigationDrawerItem(
                    icon = { Icon(item, contentDescription = null) },
                    label = { Text(item.name) },
                    selected = item == selectedItem.value,
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem.value = item
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        },
        content = {
            Scaffold(
                topBar = {
                    AppBar(
                        title = "Home",
                        actions = {
                            Icon(
                                imageVector = Icons.Filled.Menu, contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        onActions = {
                            scope.launch {
                                drawerState.open()
                                Timber.d("drawer isOpen = ${drawerState.currentValue}")
                            }
                        },
                        topAppbar = TopAppBar.CenterAligned,
                        trailingIcons = {
                            Icon(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                imageVector = Icons.Filled.Search, contentDescription = "Search"
                            )
                        }
                    )
                }
            ) {
                Surface(
                    modifier = Modifier.padding(it)
                ) {
                    val homeNavController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = homeNavController,
                        startDestination = Navigation.landing
                    ) {
                        composable(Navigation.landing) { Landing(navController) }
                        composable(Navigation.signIn, enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                        }, exitTransition = {
                            Timber.d("initial state =${initialState.destination.route} ")
                            when (initialState.destination.route) {
                                Navigation.signIn -> {
                                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                }
                                else -> null
                            }
                        }) { SignIn(navController) }
                        composable(Navigation.signUp, enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                        }, exitTransition = {
                            Timber.d("initial state =${initialState.destination.route} ")
                            when (initialState.destination.route) {
                                Navigation.signUp -> {
                                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                }
                                else -> null
                            }
                        }) { SignUp(navController) }
                        composable(Navigation.home) { Home(navController) }
                    }
                }
            }
        })
}

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Preview
@Composable
fun HomePreview() {
    Home(rememberNavController())
}
