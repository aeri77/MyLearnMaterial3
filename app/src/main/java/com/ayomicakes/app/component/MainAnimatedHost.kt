@file:OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalAnimationApi::class,
    ExperimentalAnimationApi::class, ExperimentalAnimationApi::class
)

package com.ayomicakes.app.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.ayomicakes.app.component.composable.registerFormComposable
import com.ayomicakes.app.component.composable.signInComposable
import com.ayomicakes.app.component.composable.signUpComposable
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.auth.AuthViewModel
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomePageNavigation.CAKES_ID
import com.ayomicakes.app.screen.home.HomePageNavigation.CAKES_ITEM
import com.ayomicakes.app.screen.home.HomePageNavigation.ORDER_ID
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.screen.home.Screens
import com.ayomicakes.app.screen.home.page.*
import com.ayomicakes.app.ui.theme.Primary95
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

@Composable
fun MainAnimatedHost(
    paddingValues: PaddingValues,
    contentHost: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        color = Primary95
    ) {
        contentHost()
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeHost(
    navController: NavHostController,
    homeViewModel: HomeViewModel
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
            ShopsPage(homeViewModel) { cake ->
                if (cake != null) {
                    navController.navigate(
                        "${HomePageNavigation.CAKES_PAGE}/${
                            cake.uid
                        }"
                    )
                }
            }
            homeViewModel.setSelectedScreen(Screens.ShopsPage)
        }
        composable(HomePageNavigation.CART_PAGE, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
        }) {
            CartPage(homeViewModel) {
                homeViewModel.setCheckout(it.checkoutId.toString(), checkoutModel = it)
                navController.navigate("${Navigation.Checkout.ROUTE}/${it.checkoutId}")
            }
            homeViewModel.setSelectedScreen(Screens.CartPage)
        }
        composable(HomePageNavigation.ORDER_STATUS_OPTIONAL_SHOW,
            arguments = listOf(navArgument(ORDER_ID) { defaultValue = "" })
            ,enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
        }) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString(ORDER_ID)
            if(orderId?.isNotBlank() == true){
                val transactionResponse by homeViewModel.getTransactionRequest(orderId).observeAsState()
                OrderStatusPage(transactionResponse)
            } else {
                OrderStatusPage()
            }
            homeViewModel.setSelectedScreen(Screens.OrderStatus)
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
            MessagesPage(homeViewModel)
            homeViewModel.setSelectedScreen(Screens.MessagesPage)
        }

        composable(CAKES_ITEM) { backStackEntry ->
            CakesPage(backStackEntry.arguments?.getString(CAKES_ID), homeViewModel)
        }
    }
}

fun NavGraphBuilder.authHost(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    navigation(
        startDestination = Navigation.SIGN_UP,
        route = Navigation.AUTH
    ) {
        signInComposable(authViewModel, navController)
        signUpComposable(authViewModel, navController)
        registerFormComposable(navController)
    }
}