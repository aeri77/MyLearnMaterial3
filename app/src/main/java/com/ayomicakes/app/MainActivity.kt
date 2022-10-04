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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayomicakes.app.component.*
import com.ayomicakes.app.component.enums.TopAppBar
import com.ayomicakes.app.component.snackbar.MainSnackBar
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.oauth.GoogleOauth
import com.ayomicakes.app.screen.checkout.Checkout
import com.ayomicakes.app.screen.home.HomePageNavigation
import com.ayomicakes.app.screen.home.Screens
import com.ayomicakes.app.screen.home.page.CartPage
import com.ayomicakes.app.screen.home.page.MessagesPage
import com.ayomicakes.app.screen.home.page.ShopsPage
import com.ayomicakes.app.screen.landing.Landing
import com.ayomicakes.app.screen.onboarding.OnBoarding
import com.ayomicakes.app.screen.register.RegisterForm
import com.ayomicakes.app.screen.auth.signin.SignIn
import com.ayomicakes.app.screen.auth.singup.SignUp
import com.ayomicakes.app.screen.home.HomeViewModel
import com.ayomicakes.app.ui.theme.MyLearnTheme
import com.ayomicakes.app.ui.theme.Primary95
import com.ayomicakes.app.utils.Result
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(){
//    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        homeViewModel.initUserStore()
        homeViewModel.initProfileStore()

        setContent {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            val systemUiController = rememberSystemUiController()
            // icons to mimic drawer destinations
            val items = listOf(Screens.ShopsPage, Screens.CartPage, Screens.MessagesPage)
            val isToolbarHidden by homeViewModel.isToolbarHidden.collectAsState()
            val isSideDrawerActive by homeViewModel.isSideDrawerActive.collectAsState()
            val toolbarTitle by homeViewModel.toolbarTitle.collectAsState()
            val account = GoogleSignIn.getLastSignedInAccount(context)
            val selectedItem = remember { mutableStateOf(items[0]) }
            systemUiController.setStatusBarColor(Primary95)
            val navController = rememberAnimatedNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val profileStore by homeViewModel.profileStore.observeAsState()
            val userStore by homeViewModel.userStore.observeAsState()


            when (navController.currentDestination?.route) {
                Navigation.REGISTER_FORM -> {
                    DefaultBackHandler(backNavElement = RegisterExitDialog {
                        navController.navigate(Navigation.HOME){
                            popUpTo(0)
                        }
                    })
                }
                else -> {
                    DefaultBackHandler(backNavElement = ExitDialog {

                    })
                }
            }

            LaunchedEffect(navBackStackEntry?.destination?.parent?.route){
                Timber.d("${navBackStackEntry?.destination?.parent?.route}")
                when(navBackStackEntry?.destination?.parent?.route){
                    Navigation.HOME -> {
                        homeViewModel.getProfile()
                    }
                }
            }
            LaunchedEffect(profileStore){
                Timber.d("profile store : ${profileStore?.fullName}")
            }
            LaunchedEffect(userStore){
                if(userStore?.userId == null) {
                    navController.navigate(Navigation.LANDING) {
                        popUpTo(0)
                    }
                }
            }
            MyLearnTheme {
                ModalNavigationDrawer(drawerState = drawerState,
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
                            NavigationDrawerItem(label = { Text("Navigation") },
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
                            NavigationDrawerItem(label = { Text("Other") },
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
                                if(account != null){
                                    GoogleOauth.getGoogleLoginAuth(context).signOut().addOnSuccessListener {
                                        homeViewModel.clearStore()
                                        return@addOnSuccessListener
                                    }
                                }
                                homeViewModel.clearStore()
                                navController.navigate(Navigation.LANDING) {
                                    popUpTo(0)
                                }
                            },
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
                    content = {
                        Scaffold(topBar = {
                            if (!isToolbarHidden) {
                                AppBar(title = toolbarTitle, actions = {
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
                                }, onActions = {
                                    when (navController.currentDestination?.route) {
                                        selectedItem.value.route -> {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                        Navigation.REGISTER_FORM -> {
                                            onBackPressedDispatcher.onBackPressed()
                                        }
                                        else -> {
                                            navController.navigateUp()
                                        }
                                    }
                                }, trailingIcons = {
                                    if (navController.currentDestination?.parent?.route == Navigation.HOME) {
                                        Icon(
                                            imageVector = Icons.Outlined.ShoppingBasket,
                                            contentDescription = "cart",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }, trailingOnActions = {
                                    if (navController.currentDestination?.parent?.route == Navigation.HOME) {
                                        navController.navigate(HomePageNavigation.CART_PAGE) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }, topAppbar = TopAppBar.CenterAligned
                                )
                            }
                        },
                        snackbarHost = {
                            MainSnackBar(homeViewModel)
                        }) {
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
                                            navController, homeViewModel
                                        )
                                    }
                                    composable(Navigation.ONBOARD) {
                                        OnBoarding(
                                            navController, mainViewModel = homeViewModel
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
                                            navController, mainViewModel = homeViewModel
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
                                    }) { SignUp(navController, homeViewModel) }
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
                                    }) { Checkout(navController, mainViewModel = homeViewModel) }
                                    navigation(
                                        startDestination = HomePageNavigation.SHOPS_PAGE,
                                        route = Navigation.HOME
                                    ) {
                                        composable(
                                            HomePageNavigation.SHOPS_PAGE,
                                            enterTransition = {
                                                slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                                            }) {
                                            ShopsPage(navController = navController, homeViewModel)
                                            selectedItem.value = items[0]
                                        }
                                        composable(HomePageNavigation.CART_PAGE, enterTransition = {
                                            slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
                                        }) {
                                            CartPage(navController, homeViewModel)
                                            selectedItem.value = items[1]
                                        }

                                        composable(HomePageNavigation.MESSAGES_PAGE,
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
                                            MessagesPage(navController, homeViewModel)
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