@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.ayomicakes.app.component.scaffold

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.component.AppBar
import com.ayomicakes.app.component.MainAnimatedHost
import com.ayomicakes.app.component.enums.TopAppBar
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
                    title = toolbarTitle ?: "",
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