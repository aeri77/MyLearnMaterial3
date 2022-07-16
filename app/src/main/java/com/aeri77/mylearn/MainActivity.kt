package com.aeri77.mylearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.aeri77.mylearn.navigation.Navigation
import com.aeri77.mylearn.screen.home.Home
import com.aeri77.mylearn.screen.landing.Landing
import com.aeri77.mylearn.screen.signin.SignIn
import com.aeri77.mylearn.screen.signup.SignUp
import com.aeri77.mylearn.ui.theme.MyLearnTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setContent {
            val navController = rememberAnimatedNavController()
            MyLearnTheme {
                // A surface container using the 'background' color from the theme
                AnimatedNavHost(
                    navController = navController,
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