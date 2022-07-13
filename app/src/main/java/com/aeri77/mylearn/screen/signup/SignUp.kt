package com.aeri77.mylearn.screen.signup

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.component.AppBar
import com.aeri77.mylearn.ui.theme.Crayola
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalMaterial3Api
@Composable
fun SignUp(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()
    val mainColor = MaterialTheme.colorScheme.primary
    val onMainColor = MaterialTheme.colorScheme.onPrimary

    systemUiController.setStatusBarColor(mainColor)

    Scaffold(
        topBar = {
            AppBar(
                actions = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Back to Landing",
                        tint = onMainColor
                    )
                },
                onActions = {
                    navController.navigateUp()
                },
                title = "Sign In"
            )
        }) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 32.dp),
                        text = "Welcome, Newcomer!",
                        fontSize = 28.sp,
                        fontWeight = W600
                    )
                    TextField(shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth(), label = {
                            Text("Full Names", color = Crayola)
                        }, value = "", colors = TextFieldDefaults.textFieldColors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ), onValueChange = {

                        })
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
                            Timber.d("previous route = ${navController.previousBackStackEntry?.destination?.route}")
                        }) {
                        Text(text = "Sign Up", fontSize = 16.sp)
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
                    Text(text = "Already have an account ?", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Sign In", style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ), color = MaterialTheme.colorScheme.tertiary, fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun SignUpPreview() {
    SignUp(rememberNavController())
}
