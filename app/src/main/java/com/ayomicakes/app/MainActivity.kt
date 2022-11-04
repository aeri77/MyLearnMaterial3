package com.ayomicakes.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.navigation.NavBackStackEntry
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
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.ui.theme.MyLearnTheme
import com.ayomicakes.app.ui.theme.Primary95
import com.google.accompanist.navigation.animation.AnimatedNavHost
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

        homeViewModel.initUserStore()
        homeViewModel.initProfileStore()

        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(Primary95)

            val accountGoogle = GoogleSignIn.getLastSignedInAccount(this)
            val signOutGoogle = GoogleOauth.getGoogleLoginAuth(this)

            val navController = rememberAnimatedNavController()

            val isToolbarHidden by homeViewModel.isToolbarHidden.collectAsState()
            val cartCount by homeViewModel.cakesCart.collectAsState(null)
            val selectedScreen by homeViewModel.selectedScreens.collectAsState()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ConfigureBackHandler(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            ) {
                finish()
            }

            ConfigureAppBar(homeViewModel, navBackStackEntry)

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
                            signOutGoogle.signOut().addOnSuccessListener{}
                        }
                        homeViewModel.removeFCM()
                        navController.navigate(Navigation.LANDING) {
                            popUpTo(0)
                        }
                    }
                ) {
                    MainScaffold(isToolbarHidden, homeViewModel, onActions = {
                        when (navBackStackEntry?.destination?.route) {
                            selectedScreen.route -> {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                            Navigation.REGISTER_FORM -> {
                                onBackPressedDispatcher.onBackPressed()
                            }
                            else -> {
                                navController.navigateUp()
                            }
                        }
                    }, appBarActions = {
                        when (navBackStackEntry?.destination?.route) {
                            in homeViewModel.screens.map { it.route } -> {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            else -> {
                                Icon(
                                    imageVector = Icons.Filled.ChevronLeft,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }, snackBarHost = {
                        when (navBackStackEntry?.destination?.parent?.route) {
                            Navigation.HOME -> {
                                MainSnackBar(homeViewModel)
                            }
                        }
                    }, trailingIcons = {
                        if (navBackStackEntry?.destination?.parent?.route == Navigation.HOME) {
                            BadgedBox(modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .let {
                                    if ((cartCount?.toList()?.size ?: 0) <= 0) {
                                        it.clip(CircleShape)
                                    } else it
                                }
                                .clickable {
                                    if (navBackStackEntry?.destination?.parent?.route == Navigation.HOME) {
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
                            authHost(homeViewModel, navController)
                            onBoardingComposable(scope, navController)
                            checkoutComposable(homeViewModel, navController)
                            homeHost(navController, homeViewModel)
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

@Composable
fun ConfigureAppBar(mainViewModel: MainViewModel, navBackStackEntry: NavBackStackEntry?) {
    if (navBackStackEntry?.destination?.parent?.route == Navigation.HOME) {
        mainViewModel.setToolbar(
            isHidden = false,
            isActive = true,
            title = navBackStackEntry.destination.route
        )
        return
    }
    when (navBackStackEntry?.destination?.route) {
        Navigation.ONBOARD -> {
            mainViewModel.setToolbar(
                isHidden = true,
                isActive = false,
                title = navBackStackEntry.destination.route
            )
        }
        Navigation.LANDING -> {
            mainViewModel.setToolbar(
                isHidden = true,
                isActive = false,
                title = navBackStackEntry.destination.route
            )
        }
        else -> {
            mainViewModel.setToolbar(
                isHidden = false,
                isActive = false,
                title = navBackStackEntry?.destination?.route
            )
        }
    }
}