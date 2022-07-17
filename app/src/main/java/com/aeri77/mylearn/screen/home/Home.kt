package com.aeri77.mylearn.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
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
    val homeNavController = rememberAnimatedNavController()
    // icons to mimic drawer destinations
    val items = listOf(HomePage.FirstPage, HomePage.SecondPage)
    val selectedItem = remember { mutableStateOf(items[0]) }

    DefaultBackHandler(backNavElement = ExitDialog {

    })

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            items.forEach { item ->
                NavigationDrawerItem(
                    icon = { Icon(item.image, contentDescription = null) },
                    label = { Text(item.title) },
                    selected = item == selectedItem.value,
                    onClick = {
                        scope.launch {
                            homeNavController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(homeNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                            drawerState.close()
                        }
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
                    AnimatedNavHost(
                        navController = homeNavController,
                        startDestination = HomePageNavigation.FIRST_PAGE
                    ) {
                        composable(HomePageNavigation.FIRST_PAGE) { Landing(navController) }
                        composable(HomePageNavigation.SECOND_PAGE, enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                        }, exitTransition = {
                            when (initialState.destination.route) {
                                Navigation.SIGN_IN -> {
                                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                }
                                else -> null
                            }
                        }) { SignIn(navController) }
                        composable(Navigation.SIGN_UP, enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                        }, exitTransition = {
                            when (initialState.destination.route) {
                                Navigation.SIGN_UP -> {
                                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                }
                                else -> null
                            }
                        }) { SignUp(navController) }
                        composable(Navigation.HOME) { Home(navController) }
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
