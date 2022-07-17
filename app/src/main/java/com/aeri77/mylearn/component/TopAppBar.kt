package com.aeri77.mylearn.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aeri77.mylearn.component.enums.TopAppBar

@Composable
fun AppBar(
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    actions: @Composable () -> Unit = {
        Icon(imageVector = Icons.Filled.Home, contentDescription = "home", tint = contentColor)
    }, onActions: () -> Unit = {},
    trailingIcons: @Composable BoxScope.() -> Unit = {

    },
    title: String = "",
    topAppbar: TopAppBar = TopAppBar.Large
) {
    val height: Dp = when (topAppbar) {
        TopAppBar.Large -> {
            112.dp
        }
        else -> {
            45.dp
        }
    }
    TopAppBar(
        modifier = Modifier.height(height),
        backgroundColor = backgroundColor,
        contentColor = contentColor
    ) {
        when (topAppbar) {
            TopAppBar.CenterAligned -> {
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)){
                    Box(modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 4.dp)
                        .clip(CircleShape)
                        .clickable {
                            onActions()
                        }) {
                        actions()
                    }
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        text = title,
                        color = contentColor,
                        fontWeight = W400,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    trailingIcons()
                }
            }
            TopAppBar.Large -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 2.dp)
                ) {
                    Box(modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .clickable {
                            onActions()
                        }) {
                        actions()
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .padding(horizontal = 14.dp)
                            .padding(bottom = 16.dp),
                        text = title,
                        color = contentColor,
                        fontWeight = W400,
                        fontSize = 28.sp
                    )
                }
            }
        }
    }
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun TopAppBarPreview() {
    Scaffold(
        topBar = {
            AppBar(
                title = "Home", topAppbar = TopAppBar.CenterAligned
            )
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Text(text = "Preview")
        }
    }
}