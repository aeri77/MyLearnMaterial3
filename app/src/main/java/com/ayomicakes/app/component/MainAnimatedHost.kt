@file:OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class, ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class
)

package com.ayomicakes.app.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.auth.signin.SignIn
import com.ayomicakes.app.screen.auth.singup.SignUp
import com.ayomicakes.app.screen.checkout.Checkout
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomePageNavigation.CAKES_ID
import com.ayomicakes.app.screen.home.HomePageNavigation.CAKES_ITEM
import com.ayomicakes.app.screen.home.HomePageNavigation.CAKES_PAGE
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.screen.home.Screens
import com.ayomicakes.app.screen.home.page.CakesPage
import com.ayomicakes.app.screen.home.page.CartPage
import com.ayomicakes.app.screen.home.page.MessagesPage
import com.ayomicakes.app.screen.home.page.ShopsPage
import com.ayomicakes.app.screen.landing.Landing
import com.ayomicakes.app.screen.onboarding.OnBoarding
import com.ayomicakes.app.screen.register.RegisterForm
import com.ayomicakes.app.ui.theme.Primary95
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@Composable
fun MainAnimatedHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val items = homeViewModel.items
    val selectedItem = remember { homeViewModel.selectedItems }
    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        color = Primary95
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = Navigation.LANDING
        ) {
            composable(Navigation.LANDING) {
                Landing(navController, homeViewModel)
            }
            composable(Navigation.ONBOARD) {
                OnBoarding(navController, mainViewModel = homeViewModel)
            }
            composable(Navigation.SIGN_IN, enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
            }, exitTransition = {
                when (initialState.destination.route) {
                    Navigation.SIGN_IN -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                    }
                    else -> null
                }
            }) {
                SignIn(
                    navController, mainViewModel = homeViewModel
                )
            }
            composable(Navigation.SIGN_UP, enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            }, exitTransition = {
                when (initialState.destination.route) {
                    Navigation.SIGN_UP -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                    }
                    else -> null
                }
            }) { SignUp(navController, homeViewModel) }
            composable(Navigation.REGISTER_FORM, enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            }, exitTransition = {
                when (initialState.destination.route) {
                    Navigation.REGISTER_FORM -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                    }
                    else -> null
                }
            }) { RegisterForm(navController) }
            composable(Navigation.CHECKOUT, enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            }, exitTransition = {
                when (initialState.destination.route) {
                    Navigation.SIGN_UP -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                    }
                    else -> null
                }
            }) { Checkout(navController, mainViewModel = homeViewModel) }
            homeHost(navController, homeViewModel, selectedItem, items)
        }
    }
}

fun NavGraphBuilder.homeHost(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    selectedItem: MutableState<Screens>,
    items: List<Screens>
) {
    navigation(
        startDestination = HomePageNavigation.SHOPS_PAGE,
        route = Navigation.HOME
    ) {
        composable(
            HomePageNavigation.SHOPS_PAGE,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
            }) {
            ShopsPage(navController = navController, homeViewModel)
            selectedItem.value = items[0]
        }
        composable(HomePageNavigation.CART_PAGE, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
        }) {
            CartPage(navController, homeViewModel)
            selectedItem.value = items[1]
        }

        composable(
            HomePageNavigation.MESSAGES_PAGE,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            },
            exitTransition = {
                when (initialState.destination.route) {
                    Navigation.SIGN_UP -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                    }
                    else -> null
                }
            }) {
            MessagesPage(navController, homeViewModel)
            selectedItem.value = items[2]
        }

        composable(CAKES_ITEM) { backStackEntry ->
            CakesPage(navController, backStackEntry.arguments?.getString(CAKES_ID), homeViewModel)
        }
    }
}