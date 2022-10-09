package com.ayomicakes.app.component.snackbar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.utils.Result
import kotlinx.coroutines.launch


@Composable
fun MainSnackBar(viewModel: MainViewModel = hiltViewModel()) {
    val isAuth by viewModel.isAuthenticated.collectAsState()
    val userStore by viewModel.userStore.observeAsState()
    Crossfade(!isAuth && userStore?.userId != null) {
        if (it){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Snackbar(
                    action = {
                        Button(onClick = {
                            viewModel.postRefreshToken(userStore)
                        }) {
                            Text("Lakukan")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) { Text(text = "Autentikasi gagal, Lakukan autentikasi ulang.") }
            }
        }
    }
}