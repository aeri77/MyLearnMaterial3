@file:OptIn(
    ExperimentalAnimationApi::class, ExperimentalAnimationApi::class
)

package com.ayomicakes.app.screen.register

import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.register.MapConfig.DOT
import com.ayomicakes.app.screen.register.MapConfig.GAP
import com.ayomicakes.app.screen.register.MapConfig.getBogorBound
import com.ayomicakes.app.screen.register.component.AddressForm
import com.ayomicakes.app.screen.register.component.UserForm
import com.ayomicakes.app.utils.MapUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import timber.log.Timber
import com.ayomicakes.app.utils.Result
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.maps.android.PolyUtil

@ExperimentalPermissionsApi
@Composable
fun RegisterForm(
    viewModel: RegisterViewModel = hiltViewModel(),
    onSuccessRegister: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val mainColor = MaterialTheme.colorScheme.primary
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.observeAsState()
    val shouldDialogShow = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val address = remember { mutableStateOf("") }
    val locality = remember { mutableStateOf("") }
    val subAdmin = remember { mutableStateOf("") }
    val fullName = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val registerResponse by viewModel.registerResponse.collectAsState(initial = null)

    LaunchedEffect(registerResponse) {
        if (registerResponse is Result.Success) {
            onSuccessRegister()
        }
        if (registerResponse is Result.Error) {
            Toast.makeText(context, (registerResponse as Result.Error).error, Toast.LENGTH_SHORT)
                .show()
        }
    }

    systemUiController.setStatusBarColor(mainColor)
    val listAddress by viewModel.addressList.collectAsState(null)
    val permissions = rememberMultiplePermissionsState(
        permissions =
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    permissions.permissions.forEach {
        when (it.status) {
            is PermissionStatus.Denied -> {
                val textToShow =
                    if ((it.status as PermissionStatus.Denied).shouldShowRationale) {
                        "The Location is important for this app. Please grant the permission."
                    } else {
                        "Location permission required for this feature to be available. " +
                                "Please grant the permission"
                    }
                if (shouldDialogShow.value) {
                    AlertDialog(onDismissRequest = { shouldDialogShow.value = false }, buttons = {
                        Button(onClick = {
                            permissions.launchMultiplePermissionRequest()
                        }) {
                            Text("Request permission")
                        }
                    }, title = {
                        Text(text = "${it.permission} Denied")
                    }, text = {
                        Text(text = textToShow)
                    })
                }
            }
            else -> {
                val scope = rememberCoroutineScope()
                scope.launch {
                    if (shouldDialogShow.value) {
                        viewModel.getLocation(context)
                    }
                }
//                DisposableEffect(mainViewModel.locationCallback) {
//                    mainViewModel.startLocationUpdate(mainViewModel.locationCallback, context)
//                    onDispose {
//                        mainViewModel.stopLocationUpdate()
//                    }
//                }
            }
        }

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(32.dp)
    ) {
        item {
            UserForm(viewModel, fullName, phone)
        }
        item {
            val infiniteTransition = rememberInfiniteTransition()
            val angle by infiniteTransition.animateFloat(
                initialValue = 0F,
                targetValue = 360F,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                )
            )
            Timber.d("address is loading $isLoading")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    interactionSource = interactionSource, onClick = {
                        shouldDialogShow.value = true
                        if (permissions.allPermissionsGranted) {
                            viewModel.getLocation(context = context)
                        }
                    }, enabled = isLoading == false,
                    border = BorderStroke(0.dp, color = Color.Transparent)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(14.dp)
                            .rotate(if (isLoading == false) 0f else angle),
                        imageVector = Icons.Filled.AutoMode,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))
                    Text("Isi Alamat Otomatis")
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AddressForm(
                    listAddress = listAddress,
                    address = address,
                    locality = locality,
                    subAdmin = subAdmin,
                    postalCode = postalCode
                )
            }
        }
        item {
            Box(modifier = Modifier.padding(28.dp)) {
                Crossfade(targetState = registerResponse) {
                    if (it is Result.Loading) {
                        CircularProgressIndicator()
                        return@Crossfade
                    }
                    Button(
                        enabled = address.value.isNotBlank() && locality.value.isNotBlank() && subAdmin.value.isNotBlank() && fullName.value.isNotBlank()
                                && phone.value.isNotBlank() && postalCode.value.isNotBlank(),
                        onClick = {
                            viewModel.postRegisterForm(
                                address.value,
                                locality.value,
                                subAdmin.value,
                                fullName.value,
                                phone.value,
                                postalCode.value,
                                listAddress = listAddress
                            )
                        }) {
                        Text("Save & Continue")
                    }
                }
            }
        }
    }
}

object MapConfig {

    private const val PATTERN_GAP_LENGTH_PX = 10
    val DOT: PatternItem = Dot()
    val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())

    fun getBogorBound(): List<LatLng> {
        return listOf(
            LatLng(-6.578206435388373, 106.8381678780884),
            LatLng(-6.597626561577307, 106.83197524354097),
            LatLng(-6.613485090905631, 106.83154609014684),
            LatLng(-6.634117469278161, 106.84115912719045),
            LatLng(-6.646451033340195, 106.84842005139562),
            LatLng(-6.650429732791094, 106.84902696600562),
            LatLng(-6.661280567931843, 106.84453579791554),
            LatLng(-6.676471333555119, 106.84769175395768),
            LatLng(-6.677918048540216, 106.84489994675168),
            LatLng(-6.679846995455913, 106.84016601254213),
            LatLng(-6.678038608184668, 106.8334899518321),
            LatLng(-6.679244200441486, 106.82972708125011),
            LatLng(-6.671648919649505, 106.82013783041211),
            LatLng(-6.666947020237813, 106.81855985216433),
            LatLng(-6.6650180226813625, 106.81564666204484),
            LatLng(-6.661642258853676, 106.81552527912284),
            LatLng(-6.656337440195141, 106.80872783549084),
            LatLng(-6.646330467173966, 106.81030581347683),
            LatLng(-6.652599919901605, 106.80569326244084),
            LatLng(-6.6516353939187916, 106.80338698692283),
            LatLng(-6.649344637121255, 106.80484358198684),
            LatLng(-6.648018404621318, 106.80302283815682),
            LatLng(-6.646209899991702, 106.79962411634084),
            LatLng(-6.649465203534715, 106.79525433114883),
            LatLng(-6.653082182182851, 106.79246252394283),
            LatLng(-6.653202747407242, 106.79015624833413),
            LatLng(-6.6536850090966375, 106.79027763125616),
            LatLng(-6.6575430855621605, 106.78954933372415),
            LatLng(-6.659833804117663, 106.78602922898615),
            LatLng(-6.658145907034437, 106.7815380605641),
            LatLng(-6.655855180601795, 106.77971731673411),
            LatLng(-6.654528965676854, 106.7713418951161),
            LatLng(-6.65392613952696, 106.77122051219409),
            LatLng(-6.645968765029726, 106.7814166776421),
            LatLng(-6.637046707169393, 106.7856650799121),
            LatLng(-6.628847916866236, 106.78930656757211),
            LatLng(-6.624989615417169, 106.78700029205412),
            LatLng(-6.623542744592817, 106.78700029205412),
            LatLng(-6.622216442610002, 106.7842084848481),
            LatLng(-6.612839077756789, 106.78154921218965),
            LatLng(-6.607895463320617, 106.77924293667166),
            LatLng(-6.6074131568270404, 106.77997123420366),
            LatLng(-6.607654310132612, 106.77523730024564),
            LatLng(-6.6002990814378055, 106.77402347102566),
            LatLng(-6.6002990814378055, 106.77390208810365),
            LatLng(-6.5953553417316195, 106.76928953706764),
            LatLng(-6.5952347620980944, 106.76649772986164),
            LatLng(-6.5953553417316195, 106.76722602739365),
            LatLng(-6.595475921335796, 106.76613358109563),
            LatLng(-6.5834178157685725, 106.76249209343564),
            LatLng(-6.572324099961311, 106.74986826954762),
            LatLng(-6.573409364894382, 106.74416327176188),
            LatLng(-6.576062226717621, 106.74452742052786),
            LatLng(-6.575941642396441, 106.74125008163386),
            LatLng(-6.570635903308906, 106.73809412566186),
            LatLng(-6.568947701728789, 106.74282805961987),
            LatLng(-6.567138907965138, 106.73906518903787),
            LatLng(-6.566535975249653, 106.73603061598786),
            LatLng(-6.555803650771939, 106.73566646722186),
            LatLng(-6.543262214863807, 106.74076454994587),
            LatLng(-6.544226951889706, 106.74707646188988),
            LatLng(-6.551221239580986, 106.75253869337989),
            LatLng(-6.551583010416151, 106.75278145922387),
            LatLng(-6.551583010416151, 106.75690847857187),
            LatLng(-6.547362334352822, 106.76127826376388),
            LatLng(-6.547482925592815, 106.76054996623189),
            LatLng(-6.5441063598633775, 106.76540528311189),
            LatLng(-6.528429147764942, 106.76479836877739),
            LatLng(-6.5157664264768655, 106.76880400520339),
            LatLng(-6.514801634599782, 106.77232410994141),
            LatLng(-6.51275144570132, 106.7720813440974),
            LatLng(-6.510942448541668, 106.78154921201342),
            LatLng(-6.5152840307702435, 106.78252027538942),
            LatLng(-6.515042832742994, 106.78591899720543),
            LatLng(-6.527826168294056, 106.78980325070943),
            LatLng(-6.530841058385894, 106.79308058960343),
            LatLng(-6.544588726851525, 106.79198814330543),
            LatLng(-6.547482924650341, 106.79623654557544),
            LatLng(-6.544226950947231, 106.79939250154743),
            LatLng(-6.545312278223142, 106.80643271160525),
            LatLng(-6.545794644988981, 106.80886037004525),
            LatLng(-6.540247399056891, 106.81080249679727),
            LatLng(-6.5384385011983275, 106.81808547211728),
            LatLng(-6.542779845058885, 106.81796408919526),
            LatLng(-6.543623990873631, 106.82427600113927),
            LatLng(-6.543744583016431, 106.82427600113927),
            LatLng(-6.548447653870764, 106.82743195711127),
            LatLng(-6.55508011436894, 106.82998099847327),
            LatLng(-6.5641242364915255, 106.83313695444528),
            LatLng(-6.576423978912431, 106.83641429333927),
            LatLng(-6.571118244972, 106.84066269560928),
        )
    }
}

@Composable
fun MapScreen(
    defaultLoc: LatLng?,
    isMapExpanded: Boolean = false,
    isBound: (Boolean) -> Unit = {},
    addressResult: (List<Address>?) -> Unit = {}
) {
    val cameraPositionState = rememberCameraPositionState {
        this.position = CameraPosition.fromLatLngZoom(LatLng(-6.598268, 106.799374), 16f)
    }
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = true,
                zoomControlsEnabled = true,
                myLocationButtonEnabled = true
            )
        )
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }
    val context = LocalContext.current
    val userMarker = "user_marker"
    val bounds = BoundsLocation(getBogorBound())
    val markerState = rememberMarkerState(
        userMarker
    )
    LaunchedEffect(defaultLoc) {
        if(defaultLoc != null){
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        defaultLoc,
                        16f
                    )
                )
            )
        }
    }
    LaunchedEffect(cameraPositionState.position.target) {
        markerState.position = cameraPositionState.position.target
        isBound(bounds.isInBounds(cameraPositionState.position.target))
    }
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            try{
                addressResult(
                    MapUtils.getLocationByLatLng(
                        cameraPositionState.position.target,
                        context
                    )
                )
            } catch (_: Exception){

            }
        }
    }

    val mapHeight by animateDpAsState(targetValue = if (isMapExpanded) 800.dp else 300.dp)
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(mapHeight)
            .padding(4.dp)
            .clip(RoundedCornerShape(24.dp)),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties
    ) {
        LaunchedEffect(markerState) {
            markerState.showInfoWindow()
        }

        MarkerInfoWindow(
            visible = true,
            state = markerState,
            content = {
                ElevatedButton(
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .padding(12.dp), onClick = {},
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Pilih lokasi ini", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp)
                }
            }, onInfoWindowClick = {
                addressResult(
                    MapUtils.getLocationByLatLng(
                        cameraPositionState.position.target,
                        context
                    )
                )
            }
        )
        Polygon(
            points = getBogorBound(),
            geodesic = true,
            fillColor = Color.Transparent,
            strokeColor = Color(
                182,
                115,
                135
            ),
            strokePattern = listOf(
                GAP, DOT
            )
        )
    }
}

class BoundsLocation(private val bounds: List<LatLng>) {
    fun isInBounds(position: LatLng): Boolean {
        return PolyUtil.containsLocation(position.latitude, position.longitude, bounds, true)
    }
}