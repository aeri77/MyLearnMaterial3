package com.aeri77.mylearn.screen.landing.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun PointDestination() {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(4) { it ->
            if (it == 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FirstPoint()
                    Spacer(Modifier.size(12.dp))
                    PointDescription()
                }
            }
            if (it != 0 && it != 3) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BetweenPoint()
                    Spacer(Modifier.size(12.dp))
                    PointDescription()
                }
            }
            if (it == 3) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LastPoint()
                    Spacer(Modifier.size(12.dp))
                    PointDescription()
                }
            }
        }
    }
}

@Preview
@Composable
fun FirstPoint() {
    Box(Modifier.height(200.dp)) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(20.dp)
                .fillMaxHeight()
                .padding(top = 10.dp)
                .clip(RectangleShape)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(45.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)
        )
    }
}

@Preview
@Composable
fun BetweenPoint() {
    Box(Modifier.height(200.dp)) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(20.dp)
                .fillMaxHeight()
                .clip(RectangleShape)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(45.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)
        )
    }
}

@Preview
@Composable
fun LastPoint() {
    Box(Modifier.height(200.dp)) {

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .width(20.dp)
                .padding(bottom = 10.dp)
                .fillMaxHeight()
                .clip(RectangleShape)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(45.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)
        )
    }
}

@Preview
@Composable
fun PointDestinationPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        PointDestination()
    }
}

@Preview
@Composable
fun PointDescription() {
    Column() {
        Row() {
            Text("Title:Bka asd")
            Spacer(Modifier.size(12.dp))
            Text("Others: 20 Jun 1111")
        }
        val a = Random.nextInt(1, 3)
        if (a == 1) {
            Text("ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe")
        }
        if (a == 2) {
            Text("ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe")
        }

        if (a == 3) {
            Text("ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe \n ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe  plokj Loe ABcd ea FA plokj Loe")
        }
    }
}