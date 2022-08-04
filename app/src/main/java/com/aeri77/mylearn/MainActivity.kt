package com.aeri77.mylearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.aeri77.mylearn.navigation.Navigation
import com.aeri77.mylearn.screen.home.Home
import com.aeri77.mylearn.screen.landing.Landing
import com.aeri77.mylearn.screen.landing.LandingViewModel
import com.aeri77.mylearn.screen.signin.SignIn
import com.aeri77.mylearn.screen.signup.SignUp
import com.aeri77.mylearn.ui.theme.MyLearnTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val landingViewModel: LandingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        landingViewModel.initUserStore()

        setContent {
            val navController = rememberAnimatedNavController()
            val userStore by landingViewModel.userStore.observeAsState()
            LaunchedEffect(userStore) {
                if (userStore?.username?.isNotEmpty() == true) {
                    navController.navigate(Navigation.HOME) {
                        popUpTo(0)
                    }
                }
            }
            MyLearnTheme {
                // A surface container using the 'background' color from the theme
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Navigation.LANDING
                ) {
                    composable(Navigation.LANDING) { Landing(navController) }
                    composable(Navigation.SIGN_IN, enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                    }, exitTransition = {
                        Timber.d("initial state =${initialState.destination.route} ")
                        when (initialState.destination.route) {
                            Navigation.SIGN_IN -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                            }
                            else -> null
                        }
                    }) { SignIn(navController, landingViewModel = landingViewModel) }
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
                    composable(Navigation.HOME, enterTransition = {
                        fadeIn()
                    }) { Home(navController) }
                }
            }
        }
    }
}

@Composable
fun Profile(navController: NavController) {
    Button(onClick = { navController.navigate("friendslist") }) {
        Text(text = "Navigate next")
    }
}

@Composable
fun FriendsList(navController: NavController) {
//    Button(onClick = { navController.navigate("friendslist") }) {
    Text(text = "FriendList")
//    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyLearnTheme {
        Greeting("Android")
    }
}