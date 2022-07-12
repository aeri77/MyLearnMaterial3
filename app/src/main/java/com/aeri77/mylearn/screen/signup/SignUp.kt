package com.aeri77.mylearn.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SignUp(navController: NavHostController) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(label = {
                Text("Full Names")
            }, value = "", onValueChange = {

            })
            TextField(label = {
                Text("Username")
            }, value = "", onValueChange = {

            })
            TextField(label = {
                Text("Password")
            }, value = "", onValueChange = {

            })
            Button(onClick = { /*TODO*/ }) {
                Text(text = "SignUp")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Already have Account?")
            }
        }
    }
}