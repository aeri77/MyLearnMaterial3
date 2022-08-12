package com.aeri77.mylearn.screen.landing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.navigation.Navigation
import kotlinx.coroutines.delay

@Composable
fun Landing(navController: NavHostController, viewModel: LandingViewModel = hiltViewModel()) {

    val userStore by viewModel.userStore.observeAsState()
    
    LaunchedEffect(userStore) {
        delay(5000)
        if (userStore?.username?.isNotEmpty() == true) {
            navController.navigate(Navigation.HOME) {
                popUpTo(0)
            }
        } else {
            navController.navigate(Navigation.ONBOARD) {
                popUpTo(0)
            }
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()){
            Icon(modifier = Modifier.size(72.dp).align(Alignment.Center), imageVector = Icons.Filled.Cake, contentDescription = "landing icon")
        }
    }
}

@Preview
@Composable
fun LandingPreview(){
    Landing(navController = rememberNavController())
}