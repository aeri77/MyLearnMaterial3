@file:OptIn(ExperimentalMaterial3Api::class)

package com.ayomicakes.app.component.sidedrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.BuildConfig
import com.ayomicakes.app.component.NoRippleEffect
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.screen.home.Screens

@Composable
fun MainDrawer(
    drawerState: DrawerState,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onSelectedDrawer: (Screens) -> Unit,
    onLogout: () -> Unit,
    content: @Composable () -> Unit
) {

    val isSideDrawerActive by homeViewModel.isSideDrawerActive.collectAsState()
    val profileStore by homeViewModel.profileStore.observeAsState()
    val items = homeViewModel.screens
    val selectedItem by homeViewModel.selectedScreens.collectAsState()
    val cartCount by homeViewModel.cakesCart.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isSideDrawerActive,
        drawerContent = {
            ConstraintLayout(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .height(132.dp)
                    .fillMaxWidth()
            ) {
                val (image, nameColumn) = createRefs()
                Box(modifier = Modifier
                    .padding(start = 32.dp)
                    .clip(CircleShape)
                    .size(72.dp)
                    .background(Color.White)
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(nameColumn.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }) {
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile picture"
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .constrainAs(nameColumn) {
                            start.linkTo(image.end)
                            end.linkTo(parent.end)
                            top.linkTo(image.top)
                            bottom.linkTo(image.bottom)
                            width = Dimension.fillToConstraints
                        }) {
                    Text(
                        text = profileStore?.fullName ?: "",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(12.dp),
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "wishlist",
                            tint = Color.Red
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            text = "20 Wish List >",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }

            CompositionLocalProvider(
                LocalRippleTheme provides NoRippleEffect
            ) {
                NavigationDrawerItem(
                    label = { Text("Navigation") },
                    selected = false,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
            items.forEach { item ->
                Spacer(modifier = Modifier.size(12.dp))
                NavigationDrawerItem(icon = {
                    Icon(
                        item.image!!,
                        contentDescription = null
                    )
                },
                    label = { Text(item.title) },
                    selected = item == selectedItem,
                    onClick = {
                        onSelectedDrawer(item)
                        homeViewModel.setSelectedScreen(item)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    badge = {
                        if (item.title == Screens.CartPage.title) {
                            if (cartCount.isNotEmpty()) {
                                Text("${cartCount.size}")
                            }
                        }
                    }
                )
            }
            Spacer(
                modifier = Modifier.size(12.dp)
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                thickness = .5.dp
            )
            CompositionLocalProvider(
                LocalRippleTheme provides NoRippleEffect
            ) {
                NavigationDrawerItem(
                    label = { Text("Other") },
                    selected = false,
                    onClick = { },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
            NavigationDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = "logout"
                    )
                },
                onClick = onLogout,
                label = { Text(text = "Logout") },
                selected = false
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp),
                    text = "MyLearn ${BuildConfig.VERSION_NAME}"
                )
            }
        },
        content = content
    )
}