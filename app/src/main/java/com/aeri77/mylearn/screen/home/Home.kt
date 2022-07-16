package com.aeri77.mylearn.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.component.AppBar
import com.aeri77.mylearn.component.enums.TopAppBar


@Composable
fun Home(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Home",
                actions = {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary)
                },
                onActions = {

                },
                topAppbar = TopAppBar.CenterAligned,
                secondActions = {
                    Icon(
                        imageVector = Icons.Filled.Search, contentDescription = "Search")
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {

        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home(rememberNavController())
}
