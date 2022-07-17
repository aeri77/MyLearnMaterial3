package com.aeri77.mylearn.component

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.aeri77.mylearn.utils.BackNavElement


@Composable
fun DefaultBackHandler(backNavElement: BackNavElement) = BackHandler { backNavElement.tryGoBack() }