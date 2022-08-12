package com.aeri77.mylearn.screen.signin


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.component.AppBar
import com.aeri77.mylearn.navigation.Navigation.HOME
import com.aeri77.mylearn.screen.landing.LandingViewModel
import com.aeri77.mylearn.screen.onboarding.OnBoardingViewModel
import com.aeri77.mylearn.ui.theme.Crayola
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalMaterial3Api
@Composable
fun SignIn(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel(),
    landingViewModel: LandingViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val mainColor = MaterialTheme.colorScheme.primary
    val onMainColor = MaterialTheme.colorScheme.onPrimary
    val userStore by landingViewModel.userStore.observeAsState()
    systemUiController.setStatusBarColor(mainColor)
//
//    Scaffold(
//        topBar = {
//            AppBar(
//                actions = {
//                    Icon(
//                        modifier = Modifier.size(40.dp),
//                        imageVector = Icons.Filled.KeyboardArrowLeft,
//                        contentDescription = "Back to Sign Up",
//                        tint = onMainColor
//                    )
//                },
//                onActions = {
//                    navController.navigateUp()
//                },
//                title = "Sign In"
//            )
//        }) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "Hello Again!",
                fontSize = 28.sp,
                fontWeight = W600
            )
            TextField(shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(), label = {
                    Text("Enter Email", color = Crayola)
                }, value = "",
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ), onValueChange = {

                })
            TextField(shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(), label = {
                    Text("Password", color = Crayola)
                }, colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ), value = "", onValueChange = {

                })
            Button(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                onClick = {
                    viewModel.signIn()
                    navController.navigate(HOME) {
                        popUpTo(0)
                    }
                }) {
                Text(text = "Sign In", fontSize = 16.sp)
            }

            Text("or", fontSize = 16.sp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(54.dp)
                        .weight(0.5f),
                    onClick = { /*TODO*/ }) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = null
                    )
                }
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(54.dp)
                        .weight(0.5f),
                    onClick = { /*TODO*/ }) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null
                    )
                }
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(54.dp)
                        .weight(0.5f),
                    onClick = { /*TODO*/ }) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = null
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
        ) {
            Text(text = "Don't have an account ?", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                modifier = Modifier.clickable {
                    navController.navigateUp()
                },
                text = "Sign Up", style = TextStyle(
                    textDecoration = TextDecoration.Underline
                ), color = MaterialTheme.colorScheme.tertiary, fontSize = 16.sp
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun SignInPreview() {
    SignIn(rememberNavController())
}
