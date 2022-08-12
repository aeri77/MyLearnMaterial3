package com.aeri77.mylearn.screen.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.BuildConfig
import com.aeri77.mylearn.component.AppBar
import com.aeri77.mylearn.component.DefaultBackHandler
import com.aeri77.mylearn.component.ExitDialog
import com.aeri77.mylearn.component.NoRippleEffect
import com.aeri77.mylearn.component.enums.TopAppBar
import com.aeri77.mylearn.navigation.Navigation
import com.aeri77.mylearn.screen.home.page.CheckoutPage
import com.aeri77.mylearn.screen.home.page.MessagesPage
import com.aeri77.mylearn.screen.home.page.ShopsPage
import com.aeri77.mylearn.screen.signup.SignUp
import com.aeri77.mylearn.ui.theme.Primary95
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import timber.log.Timber


@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun Home(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Preview
@Composable
fun HomePreview() {
    Home(rememberNavController())
}
