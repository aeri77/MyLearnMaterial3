package com.aeri77.mylearn.screen.landing

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.R
import com.aeri77.mylearn.component.DefaultBackHandler
import com.aeri77.mylearn.component.ExitDialog
import com.aeri77.mylearn.navigation.Navigation
import com.aeri77.mylearn.screen.landing.component.ButtonNext
import com.aeri77.mylearn.screen.landing.component.ButtonPrev
import com.aeri77.mylearn.screen.landing.component.LandingMainItems
import com.aeri77.mylearn.utils.BackNavElement
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun Landing(navController: NavController, viewModel: LandingViewModel = viewModel()) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxSize()
    ) {
        val (buttonArea, mainItem, indicator, loading) = createRefs()
        val systemUiController = rememberSystemUiController()
        val isLoading = viewModel.loading
        val baseColor = MaterialTheme.colorScheme.primary
        var color by remember { mutableStateOf(baseColor) }
        var pagePosition by remember { viewModel.pagePosition }
        val success by viewModel.isSuccess.observeAsState()

        DefaultBackHandler(backNavElement = ExitDialog {

        })

        LaunchedEffect(success) {
            if (success == true) {
                viewModel.isSuccess.value = false
                scope.launch {
                    navController.navigate(Navigation.signUp)
                    viewModel.loading = false
                }
            }
        }

        HorizontalPager(
            modifier = Modifier
                .constrainAs(mainItem) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(indicator.top)
                    height = Dimension.preferredWrapContent
                }
                .fillMaxSize(),
            count = 3, state = pagerState,
        ) { page ->
            when (pagerState.currentPage) {
                0 -> {
                    color = MaterialTheme.colorScheme.primary
                    systemUiController.setStatusBarColor(
                        color = color
                    )
                }
                1 -> {
                    color = MaterialTheme.colorScheme.secondary
                    systemUiController.setStatusBarColor(
                        color = color
                    )
                }
                2 -> {
                    color = MaterialTheme.colorScheme.tertiary
                    systemUiController.setStatusBarColor(
                        color = color
                    )
                }
            }
            when (page) {
                0 -> {
                    FirstPage(systemUiController)
                }
                1 -> {
                    SecondPage(systemUiController)
                }
                2 -> {
                    ThirdPage(systemUiController)
                }
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .constrainAs(indicator) {
                    top.linkTo(mainItem.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttonArea.top)
                }
                .padding(vertical = 24.dp),
            pagerState = pagerState
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(buttonArea) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Crossfade(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 12.dp), targetState = pagerState.currentPage
            ) {
                if (it != 0) {
                    ButtonPrev(pagerState = pagerState, color)
                }
            }
            ButtonNext(pagerState, color, viewModel)
        }
        Crossfade(modifier = Modifier.constrainAs(loading) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }, targetState = isLoading && pagerState.currentPage != 2) {
            if (it){
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }

}

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Preview
@Composable
fun Preview() {
    Landing(rememberNavController())
}

@Composable
fun FirstPage(systemUiController: SystemUiController) {
    LandingMainItems(
        title = "Cras Augue",
        desc = "Integer id luctus nunc. Mauris finibus gravida nisl, vitae euismod.",
        image = painterResource(id = R.drawable.onboard_image_1),
        backgroundColor = MaterialTheme.colorScheme.onPrimary,
        textColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun SecondPage(systemUiController: SystemUiController) {
    LandingMainItems(
        title = "Nulla ac Metus",
        desc = "Nunc blandit varius porttitor. Etiam rhoncus lorem faucibus nulla ornare, eget tincidunt sem volutpat. In.",
        image = painterResource(id = R.drawable.onboard_image_0),
        backgroundColor = MaterialTheme.colorScheme.onSecondary,
        textColor = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun ThirdPage(systemUiController: SystemUiController) {
    LandingMainItems(
        title = "Quisque pretium",
        desc = "Nullam sit amet pulvinar quam. Aliquam magna nisl, ullamcorper.",
        image = painterResource(id = R.drawable.onboard_image_2),
        backgroundColor = MaterialTheme.colorScheme.onTertiary,
        textColor = MaterialTheme.colorScheme.tertiary
    )
}