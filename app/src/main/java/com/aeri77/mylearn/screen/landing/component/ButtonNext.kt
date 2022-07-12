package com.aeri77.mylearn.screen.landing.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aeri77.mylearn.screen.landing.LandingViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

private enum class OnboardButtonState {
    Next,
    Continue
}

@ExperimentalPagerApi
@Composable
fun ButtonNext(pagerState: PagerState, color: Color, viewModel: LandingViewModel = viewModel()) {
    val isLoading = viewModel.loading
    val scope = rememberCoroutineScope()

    var buttonState by remember { mutableStateOf(OnboardButtonState.Next) }
    val transition = updateTransition(targetState = buttonState, label = "")
    val sizeState by transition.animateDp(label = "") { state ->
        when (state) {
            OnboardButtonState.Next -> 96.dp
            OnboardButtonState.Continue ->if(isLoading) 96.dp else 192.dp
        }
    }
    val colorState by transition.animateColor(label = "") { state ->
        when (state) {
            OnboardButtonState.Next -> {
                Color.Transparent
            }
            OnboardButtonState.Continue -> {
                MaterialTheme.colorScheme.primary
            }
        }
    }

    buttonState = when (pagerState.currentPage) {
        2 -> {
            OnboardButtonState.Continue
        }
        else -> {
            OnboardButtonState.Next
        }
    }

    Button(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .padding(horizontal = 12.dp)
            .height(60.dp)
            .width(sizeState),
        enabled = !(isLoading && buttonState == OnboardButtonState.Continue),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorState
        ), onClick = {
            when (pagerState.currentPage) {
                2 -> {
                    viewModel.setLoading()
                }
                else -> {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        }) {
        var visibilityState by remember { mutableStateOf(false) }
        visibilityState = when (buttonState) {
            OnboardButtonState.Next -> false
            OnboardButtonState.Continue -> true
        }
        if (buttonState != OnboardButtonState.Continue) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "next",
                colorFilter = ColorFilter.tint(color)
            )
        }
        if (!isLoading) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 46.dp)
                    .fillMaxWidth().animateContentSize(),
                text = "Daftar",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        } else {
            AnimatedVisibility(visible = visibilityState) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Preview
@Composable
private fun ButtonNextPreview() {
    val pageState = rememberPagerState()
    Box {
        ButtonNext(pagerState = pageState, color = Color.Blue)
    }
}