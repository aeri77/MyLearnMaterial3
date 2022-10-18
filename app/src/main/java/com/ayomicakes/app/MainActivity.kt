package com.ayomicakes.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayomicakes.app.component.*
import com.ayomicakes.app.component.backhandler.ConfigureBackHandler
import com.ayomicakes.app.component.composable.*
import com.ayomicakes.app.component.scaffold.MainScaffold
import com.ayomicakes.app.component.sidedrawer.MainDrawer
import com.ayomicakes.app.component.snackbar.MainSnackBar
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.navigation.navigateSingleTopTo
import com.ayomicakes.app.oauth.GoogleOauth
import com.ayomicakes.app.screen.auth.signin.SignIn
import com.ayomicakes.app.screen.auth.singup.SignUp
import com.ayomicakes.app.screen.checkout.Checkout
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.screen.landing.Landing
import com.ayomicakes.app.screen.onboarding.OnBoarding
import com.ayomicakes.app.screen.onboarding.OnBoardingViewModel
import com.ayomicakes.app.screen.register.RegisterForm
import com.ayomicakes.app.ui.theme.MyLearnTheme
import com.ayomicakes.app.ui.theme.Primary95
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        val accountGoogle = GoogleSignIn.getLastSignedInAccount(this)
        val signOutGoogle = GoogleOauth.getGoogleLoginAuth(this)

        homeViewModel.initUserStore()
        homeViewModel.initProfileStore()

        setContent {

            val systemUiController = rememberSystemUiController()
            // icons to mimic drawer destinations
            val isToolbarHidden by homeViewModel.isToolbarHidden.collectAsState()
            val cartCount by homeViewModel.cakesCart.collectAsState(null)
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            systemUiController.setStatusBarColor(Primary95)
            val selectedItem = homeViewModel.selectedItems
            val items = homeViewModel.items
            val navController = rememberAnimatedNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val scope = rememberCoroutineScope()

            ConfigureBackHandler(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            ) {
                finish()
            }
            MainEffect(navController = navController, homeViewModel)

            MyLearnTheme {
                MainDrawer(drawerState, homeViewModel = homeViewModel,
                    onSelectedDrawer = { screen ->
                        scope.launch {
                            navController.navigateSingleTopTo(screen.route)
                            drawerState.close()
                        }
                    },
                    onLogout = {
                        scope.launch {
                            drawerState.close()
                        }
                        if (accountGoogle != null) {
                            signOutGoogle.signOut().addOnSuccessListener {
                                homeViewModel.clearStore()
                                return@addOnSuccessListener
                            }
                        }
                        homeViewModel.clearStore()
                        navController.navigate(Navigation.LANDING) {
                            popUpTo(0)
                        }
                    }
                ) {
                    MainScaffold(isToolbarHidden, homeViewModel, onActions = {
                    }, appBarActions = {
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
                    }, snackBarHost = {
                        when (navBackStackEntry?.destination?.parent?.route) {
                            Navigation.HOME -> {
                                MainSnackBar(homeViewModel)
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
                                }
                                .clickable {
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
                    }) {
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = Navigation.LANDING
                        ) {
                            landingComposable(accountGoogle, homeViewModel, navController)
                            onBoardingComposable(scope, navController)
                            signInComposable(navController)
                            signUpComposable(navController)
                            registerFormComposable(navController)
                            checkoutComposable(homeViewModel, navController)
                            homeHost(navController, homeViewModel, selectedItem, items)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainEffect(navController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val profileStore by homeViewModel.profileStore.observeAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val userStore by homeViewModel.userStore.observeAsState()

    LaunchedEffect(navBackStackEntry?.destination?.parent?.route) {
        Timber.d("${navBackStackEntry?.destination?.parent?.route}")
        when (navBackStackEntry?.destination?.parent?.route) {
            Navigation.HOME -> {
                homeViewModel.getProfile()
            }
        }
    }
    LaunchedEffect(profileStore) {
        Timber.d("profile store : ${profileStore?.fullName}")
    }

    LaunchedEffect(userStore) {
        if (userStore?.userId == null) {
            navController.navigate(Navigation.LANDING) {
                popUpTo(0)
            }
        }
        if (userStore?.userId != null) {
            homeViewModel.isAuthenticated.emit(true)
        }
    }
}