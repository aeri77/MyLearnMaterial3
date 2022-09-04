package com.ayomicakes.app.screen.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun ButtonPrev(pagerState: PagerState, color: Color) {
    val scope = rememberCoroutineScope()
    Button(
        modifier = Modifier
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ), onClick = {
            when (pagerState.currentPage) {
                0 -> {}
                else -> {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            }
        }) {
        Image(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "next",
            colorFilter = ColorFilter.tint(color)
        )
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun ButtonPrevPreview() {
    val pageState = rememberPagerState()
    ButtonPrev(pagerState = pageState, MaterialTheme.colorScheme.onTertiary)
}