package com.ayomicakes.app.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.MainViewModel


@Composable
fun MainSnackBar(viewModel: MainViewModel = hiltViewModel()) {
    val isAuth by viewModel.isAuthenticated.collectAsState()
    val userStore by viewModel.userStore.observeAsState()
    if (!isAuth && userStore?.userId != null){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Text("Not Authenticated")
        }
    }
}