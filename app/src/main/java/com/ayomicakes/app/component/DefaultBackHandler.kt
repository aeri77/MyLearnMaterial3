package com.ayomicakes.app.component

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.ayomicakes.app.utils.BackNavElement


@Composable
fun DefaultBackHandler(backNavElement: BackNavElement) = BackHandler { backNavElement.tryGoBack() }