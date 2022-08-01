package com.aeri77.mylearn.screen.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aeri77.mylearn.BuildConfig
import com.aeri77.mylearn.component.AppBar
import com.aeri77.mylearn.component.DefaultBackHandler
import com.aeri77.mylearn.component.ExitDialog
import com.aeri77.mylearn.component.NoRippleEffect
import com.aeri77.mylearn.component.enums.TopAppBar
import com.aeri77.mylearn.navigation.Navigation
import com.aeri77.mylearn.screen.home.page.ShopsPage
import com.aeri77.mylearn.screen.signin.SignIn
import com.aeri77.mylearn.screen.signup.SignUp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import timber.log.Timber


@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun Home(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val scope = rememberCoroutineScope()
    val homeNavController = rememberAnimatedNavController()
    // icons to mimic drawer destinations
    val items = listOf(HomePages.ShopsPage, HomePages.CheckoutPage)

    val selectedItem = remember { mutableStateOf(items[0]) }

    DefaultBackHandler(backNavElement = ExitDialog {

    })

    ModalNavigationDrawer(
        drawerState = drawerState,
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
                        imageVector = Icons.Filled.Person, contentDescription = "Profile picture"
                    )
                }
                Column(modifier = Modifier
                    .padding(start = 18.dp)
                    .constrainAs(nameColumn) {
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        top.linkTo(image.top)
                        bottom.linkTo(image.bottom)
                        width = Dimension.fillToConstraints
                    }) {
                    Text(text = "Full Name", fontSize = 28.sp, fontWeight = FontWeight.W600, color = MaterialTheme.colorScheme.onSecondary)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(modifier = Modifier.size(12.dp),imageVector = Icons.Filled.Favorite, contentDescription = "wishlist", tint = Color.Red)
                        Spacer(Modifier.size(4.dp))
                        Text(text = "20 Wish List >", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondary, fontStyle = FontStyle.Italic)
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
                NavigationDrawerItem(
                    icon = { Icon(item.image, contentDescription = null) },
                    label = { Text(item.title) },
                    selected = item == selectedItem.value,
                    onClick = {
                        scope.launch {
                            homeNavController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(homeNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                            drawerState.close()
                        }
                        selectedItem.value = item
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
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
                label = { Text(text = "Logout") }, selected = false, onClick = { /*TODO*/ })

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp), text = "MyLearn ${BuildConfig.VERSION_NAME}"
                )
            }
        },
        content = {
            Scaffold(
                topBar = {
                    AppBar(
                        title = "Home",
                        actions = {
                            Icon(
                                imageVector = Icons.Filled.Menu, contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        onActions = {
                            scope.launch {
                                drawerState.open()
                                Timber.d("drawer isOpen = ${drawerState.currentValue}")
                            }
                        },
                        topAppbar = TopAppBar.CenterAligned,
                        trailingIcons = {
                            Icon(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                imageVector = Icons.Filled.Search, contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    )
                }
            ) {
                Surface(
                    modifier = Modifier.padding(it)
                ) {
                    AnimatedNavHost(
                        navController = homeNavController,
                        startDestination = HomePageNavigation.SHOPS_PAGE
                    ) {
                        composable(HomePageNavigation.SHOPS_PAGE) {
                            ShopsPage(navController)
                            selectedItem.value = items[0]
                        }
                        composable(HomePageNavigation.SECOND_PAGE, enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                        }, exitTransition = {
                            when (initialState.destination.route) {
                                Navigation.SIGN_IN -> {
                                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                }
                                else -> null
                            }
                        }) {
                            SignIn(navController)
                            selectedItem.value = items[1]
                        }
                        composable(Navigation.SIGN_UP, enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                        }, exitTransition = {
                            when (initialState.destination.route) {
                                Navigation.SIGN_UP -> {
                                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                }
                                else -> null
                            }
                        }) { SignUp(navController) }
                        composable(Navigation.HOME) { Home(navController) }
                    }
                }
            }
        })
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Preview
@Composable
fun HomePreview() {
    Home(rememberNavController())
}
