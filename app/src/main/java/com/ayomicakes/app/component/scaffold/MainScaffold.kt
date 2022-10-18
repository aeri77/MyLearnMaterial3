@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.ayomicakes.app.component.scaffold

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.component.AppBar
import com.ayomicakes.app.component.MainAnimatedHost
import com.ayomicakes.app.component.enums.TopAppBar
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.navigation.navigateSingleTopTo
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.HomeViewModel

@Composable
fun MainScaffold(
    isToolbarHidden: Boolean,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onActions: () -> Unit,
    appBarActions: @Composable () -> Unit,
    snackBarHost: @Composable () -> Unit,
    trailingIcons: @Composable BoxScope.() -> Unit,
    scaffoldContent: @Composable () -> Unit
) {
    val toolbarTitle by homeViewModel.toolbarTitle.collectAsState()
    Scaffold(
        topBar = {
            if (!isToolbarHidden) {
                AppBar(
                    title = toolbarTitle,
                    actions = appBarActions,
                    onActions = onActions,
                    trailingIcons = trailingIcons,
                    topAppbar = TopAppBar.CenterAligned
                )
            }
        },
        snackbarHost = snackBarHost
    ) {
        MainAnimatedHost(it){
            scaffoldContent()
        }
    }
}