@file:OptIn(
    ExperimentalAnimationApi::class, ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    ExperimentalAnimationApi::class
)

package com.ayomicakes.app.component.composable

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavDirections
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.ayomicakes.app.component.homeHost
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.auth.AuthViewModel
import com.ayomicakes.app.screen.auth.signin.SignIn
import com.ayomicakes.app.screen.auth.singup.SignUp
import com.ayomicakes.app.screen.checkout.Checkout
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.screen.landing.Landing
import com.ayomicakes.app.screen.onboarding.OnBoarding
import com.ayomicakes.app.screen.register.RegisterForm
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.landingComposable(
    accountGoogle: GoogleSignInAccount?,
    homeViewModel: HomeViewModel,
    navController: NavHostController
) {
    composable(Navigation.LANDING) {
        Landing(
            account = accountGoogle,
            homeViewModel,
            onLandingSuccessful = {
                navController.navigate(Navigation.HOME) {
                    popUpTo(0)
                }
            }, onLandingFailed = {
                navController.navigate(Navigation.ONBOARD) {
                    popUpTo(0)
                }
            })
    }
}

fun NavGraphBuilder.onBoardingComposable(
    scope: CoroutineScope,
    navController: NavHostController
) {
    composable(Navigation.ONBOARD) {
        OnBoarding {
            scope.launch {
                navController.navigate(Navigation.AUTH)
            }
        }
    }
}

fun NavGraphBuilder.signInComposable(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
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
        SignIn(authViewModel, onSuccessSignIn = {
            navController.navigate(Navigation.HOME) {
                popUpTo(0)
            }
        }, onSignUp = {
            navController.navigateUp()
        })
    }
}

fun NavGraphBuilder.signUpComposable(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    composable(Navigation.SIGN_UP, enterTransition = {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
    }, exitTransition = {
        when (initialState.destination.route) {
            Navigation.SIGN_UP -> {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }
            else -> null
        }
    }) {
        SignUp(authViewModel, onSuccessSignUp = {
            navController.navigate(Navigation.REGISTER_FORM) {
                popUpTo(0)
            }
        }, onSignIn = {
            navController.navigate(Navigation.SIGN_IN)
        })
    }
}

fun NavGraphBuilder.registerFormComposable(
    navController: NavHostController
) {
    composable(Navigation.REGISTER_FORM, enterTransition = {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
    }, exitTransition = {
        when (initialState.destination.route) {
            Navigation.REGISTER_FORM -> {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }
            else -> null
        }
    }) {
        RegisterForm(onSuccessRegister = {
            navController.navigate(Navigation.HOME) {
                popUpTo(0)
            }
        })
    }
}

fun NavGraphBuilder.checkoutComposable(
    homeViewModel: HomeViewModel,
    navController: NavHostController
) {
    composable(Navigation.Checkout.ROUTE_ITEM, enterTransition = {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
    }, exitTransition = {
        when (initialState.destination.route) {
            Navigation.SIGN_UP -> {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }
            else -> null
        }
    }) { navBackStackEntry ->
        val checkoutId = navBackStackEntry.arguments?.getString(Navigation.Checkout.ID)
        Checkout(checkoutId, homeViewModel, onSuccessCreateTransaction = {
            navController.navigate("${HomePageNavigation.ORDER_STATUS_PAGE}?showOnRequest=$it")
//            navController.navigate(NavDirections.)
        })
    }
}