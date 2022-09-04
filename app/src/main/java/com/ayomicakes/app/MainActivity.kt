package com.ayomicakes.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ShoppingBasket
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
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ayomicakes.app.component.AppBar
import com.ayomicakes.app.component.DefaultBackHandler
import com.ayomicakes.app.component.ExitDialog
import com.ayomicakes.app.component.NoRippleEffect
import com.ayomicakes.app.component.enums.TopAppBar
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.checkout.Checkout
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.Screens
import com.ayomicakes.app.screen.home.page.CartPage
import com.ayomicakes.app.screen.home.page.MessagesPage
import com.ayomicakes.app.screen.home.page.ShopsPage
import com.ayomicakes.app.screen.landing.Landing
import com.ayomicakes.app.screen.onboarding.OnBoarding
import com.ayomicakes.app.screen.register.RegisterForm
import com.ayomicakes.app.screen.signin.SignIn
import com.ayomicakes.app.screen.signup.SignUp
import com.ayomicakes.app.ui.theme.MyLearnTheme
import com.ayomicakes.app.ui.theme.Primary95
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        mainViewModel.initUserStore()
        setContent {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val systemUiController = rememberSystemUiController()
            // icons to mimic drawer destinations
            val items = listOf(Screens.ShopsPage, Screens.CartPage, Screens.MessagesPage)
            val isToolbarHidden by mainViewModel.isToolbarHidden.collectAsState()
            val isSideDrawerActive by mainViewModel.isSideDrawerActive.collectAsState()
            val toolbarTitle by mainViewModel.toolbarTitle.collectAsState()
            val selectedItem = remember { mutableStateOf(items[0]) }

            DefaultBackHandler(backNavElement = ExitDialog {

            })
            systemUiController.setStatusBarColor(Primary95)
            val navController = rememberAnimatedNavController()

            MyLearnTheme {
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
                            Column(modifier = Modifier
                                .padding(start = 18.dp)
                                .constrainAs(nameColumn) {
                                    start.linkTo(image.end)
                                    end.linkTo(parent.end)
                                    top.linkTo(image.top)
                                    bottom.linkTo(image.bottom)
                                    width = Dimension.fillToConstraints
                                }) {
                                Text(
                                    text = "Full Name",
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
                            NavigationDrawerItem(
                                icon = { Icon(item.image!!, contentDescription = null) },
                                label = { Text(item.title) },
                                selected = item == selectedItem.value,
                                onClick = {
                                    scope.launch {
                                        navController.navigate(item.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
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
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                mainViewModel.clearUserStore()
                                navController.navigate(Navigation.LANDING) {
                                    popUpTo(0)
                                }
                            },
                            label = { Text(text = "Logout") }, selected = false
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
                    content = {
                        Scaffold(
                            topBar = {
                                if (!isToolbarHidden) {
                                    AppBar(
                                        title = toolbarTitle,
                                        actions = {
                                            if (navController.currentDestination?.route == selectedItem.value.route) {
                                                Icon(
                                                    imageVector = Icons.Filled.Menu,
                                                    contentDescription = "Menu",
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = Icons.Filled.ChevronLeft,
                                                    contentDescription = "Back",
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        },
                                        onActions = {
                                            if (navController.currentDestination?.route == selectedItem.value.route) {
                                                scope.launch {
                                                    drawerState.open()
                                                }
                                            } else {
                                                navController.navigateUp()
                                            }

                                        },
                                        trailingIcons = {
                                            if (navController.currentDestination?.parent?.route == Navigation.HOME) {
                                                Icon(
                                                    imageVector = Icons.Outlined.ShoppingBasket,
                                                    contentDescription = "cart",
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        },
                                        trailingOnActions = {
                                            if (navController.currentDestination?.parent?.route == Navigation.HOME) {
                                                navController.navigate(HomePageNavigation.CART_PAGE) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        },
                                        topAppbar = TopAppBar.CenterAligned
                                    )
                                }
                            }
                        ) {
                            Surface(
                                modifier = Modifier
                                    .padding(it)
                                    .fillMaxSize()
                            ) {
                                // A surface container using the 'background' color from the theme
                                AnimatedNavHost(
                                    navController = navController,
                                    startDestination = Navigation.LANDING
                                ) {
                                    composable(Navigation.LANDING) {
                                        Landing(
                                            navController,
                                            mainViewModel
                                        )
                                    }
                                    composable(Navigation.ONBOARD) {
                                        OnBoarding(
                                            navController,
                                            mainViewModel = mainViewModel
                                        )
                                    }
                                    composable(Navigation.SIGN_IN, enterTransition = {
                                        slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                                    }, exitTransition = {
                                        Timber.d("initial state =${initialState.destination.route} ")
                                        when (initialState.destination.route) {
                                            Navigation.SIGN_IN -> {
                                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                            }
                                            else -> null
                                        }
                                    }) {
                                        SignIn(
                                            navController,
                                            mainViewModel = mainViewModel
                                        )
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
                                    }) { SignUp(navController, mainViewModel) }
                                    composable(Navigation.REGISTER_FORM, enterTransition = {
                                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                                    }, exitTransition = {
                                        when (initialState.destination.route) {
                                            Navigation.REGISTER_FORM -> {
                                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                            }
                                            else -> null
                                        }
                                    }) { RegisterForm(navController) }
                                    composable(Navigation.CHECKOUT, enterTransition = {
                                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                                    }, exitTransition = {
                                        when (initialState.destination.route) {
                                            Navigation.SIGN_UP -> {
                                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                            }
                                            else -> null
                                        }
                                    }) { Checkout(navController, mainViewModel = mainViewModel) }
                                    navigation(
                                        startDestination = HomePageNavigation.SHOPS_PAGE,
                                        route = Navigation.HOME
                                    ) {
                                        composable(HomePageNavigation.SHOPS_PAGE,
                                            enterTransition = {
                                                slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                                            }) {
                                            ShopsPage(navController = navController, mainViewModel)
                                            selectedItem.value = items[0]
                                        }
                                        composable(
                                            HomePageNavigation.CART_PAGE,
                                            enterTransition = {
                                                slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                                            }) {
                                            CartPage(navController, mainViewModel)
                                            selectedItem.value = items[1]
                                        }

                                        composable(
                                            HomePageNavigation.MESSAGES_PAGE,
                                            enterTransition = {
                                                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                                            },
                                            exitTransition = {
                                                when (initialState.destination.route) {
                                                    Navigation.SIGN_UP -> {
                                                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                                                    }
                                                    else -> null
                                                }
                                            }) {
                                            MessagesPage(navController, mainViewModel)
                                            selectedItem.value = items[2]
                                        }
                                    }
                                }
                            }
                        }
                    })
            }
        }
    }
}

@Composable
fun Profile(navController: NavController) {
    Button(onClick = { navController.navigate("friendslist") }) {
        Text(text = "Navigate next")
    }
}

@Composable
fun FriendsList(navController: NavController) {
//    Button(onClick = { navController.navigate("friendslist") }) {
    Text(text = "FriendList")
//    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyLearnTheme {
        Greeting("Android")
    }
}