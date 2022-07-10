package com.aeri77.mylearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.navigation.Navigation
import com.aeri77.mylearn.screen.landing.Landing
import com.aeri77.mylearn.ui.theme.MyLearnTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyLearnTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = Navigation.landing) {
                    composable(Navigation.landing) { Landing(navController) }
                    composable("profile") { Profile(navController) }
                    composable("friendslist") { FriendsList(navController) }
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