package com.ayomicakes.app.screen.home.page

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.utils.Result
import java.util.*

@Composable
fun CakesPage(
    navController: NavHostController,
    string: String?,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Surface {
        val cakeResponse by homeViewModel.cake.collectAsState(initial = null)

        LaunchedEffect(true){
            homeViewModel.getCakes(UUID.fromString(string))
        }

        if (cakeResponse is Result.Success<FullResponse<CakeItem>>) {
            Text(
                text = (cakeResponse as Result.Success<FullResponse<CakeItem>>).data.result.cakeName
                    ?: ""
            )
        }
    }
}