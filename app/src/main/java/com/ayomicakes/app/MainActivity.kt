package com.ayomicakes.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayomicakes.app.component.*
import com.ayomicakes.app.component.scaffold.MainScaffold
import com.ayomicakes.app.component.sidedrawer.MainDrawer
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.ui.theme.MyLearnTheme
import com.ayomicakes.app.ui.theme.Primary95
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(){
//    private val mainViewModel: MainViewModel by viewModels()
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
            // icons to mimic drawer destinations
            val isToolbarHidden by homeViewModel.isToolbarHidden.collectAsState()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            systemUiController.setStatusBarColor(Primary95)
            val navController = rememberAnimatedNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()


            when (navController.currentDestination?.route) {
                Navigation.REGISTER_FORM -> {
                    DefaultBackHandler(backNavElement = RegisterExitDialog {
                        navController.navigate(Navigation.HOME){
                            popUpTo(0)
                        }
                    })
                }
            }
            if(navBackStackEntry?.destination?.route == HomePageNavigation.SHOPS_PAGE){
                DefaultBackHandler(backNavElement = ExitDialog {
                    finish()
                })
            }

            MainEffect(navController = navController, homeViewModel)

            MyLearnTheme {
                MainDrawer(drawerState, navController = navController, homeViewModel = homeViewModel) {
                    MainScaffold(isToolbarHidden,drawerState, navController, homeViewModel)
                }
            }
        }
    }
}

@Composable
fun MainEffect(navController: NavHostController, homeViewModel: HomeViewModel= hiltViewModel() ){
    val profileStore by homeViewModel.profileStore.observeAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val userStore by homeViewModel.userStore.observeAsState()

    LaunchedEffect(navBackStackEntry?.destination?.parent?.route){
        Timber.d("${navBackStackEntry?.destination?.parent?.route}")
        when(navBackStackEntry?.destination?.parent?.route){
            Navigation.HOME -> {
                homeViewModel.getProfile()
            }
        }
    }
    LaunchedEffect(profileStore){
        Timber.d("profile store : ${profileStore?.fullName}")
    }

    LaunchedEffect(userStore){
        if(userStore?.userId == null) {
            navController.navigate(Navigation.LANDING) {
                popUpTo(0)
            }
        }
        if(userStore?.userId != null){
            homeViewModel.isAuthenticated.emit(true)
        }
    }
}