package com.ayomicakes.app.screen.signup

import android.app.Activity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.ui.theme.Crayola
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import timber.log.Timber
import java.util.concurrent.Executor

@ExperimentalMaterial3Api
@Composable
fun SignUp(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val mainColor = MaterialTheme.colorScheme.primary
    val onMainColor = MaterialTheme.colorScheme.onPrimary

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val signUpLoading by viewModel.signUpLoading.collectAsState()
    val captchaLoading by viewModel.captchaLoading.collectAsState()
    val userStore by viewModel.userStore.collectAsState(null)
    var captchaToken by remember { mutableStateOf<String?>(null) }
    var isCaptchaSuccess by remember { mutableStateOf<Boolean?>(null) }
    val captchaResponse by viewModel.captchaResponse.collectAsState(null)

    systemUiController.setStatusBarColor(mainColor)
    mainViewModel.setToolbar(
        isHidden = false,
        isActive = false,
        title = navController.currentDestination?.route?.split("_")?.get(0)
            ?.capitalize(Locale.current) ?: ""
    )
    LaunchedEffect(captchaToken) {
        Timber.d("captcha token : $captchaToken")
        if (captchaToken?.isNotBlank() == true) {
            viewModel.sendCaptcha(captchaToken)
        }
    }
    LaunchedEffect(captchaResponse) {
        Timber.d("captcha response : $captchaResponse")
        if (captchaResponse?.result != null) {
            isCaptchaSuccess = captchaResponse?.result?.success
        }
    }
    LaunchedEffect(userStore) {
        if (userStore != null) {
            if (userStore?.userId != null) {
                if (userStore?.userId?.toString()?.isNotBlank() == true) {
                    navController.navigate(Navigation.REGISTER_FORM) {
                        popUpTo(0)
                    }
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "Welcome, Newcomer!",
                fontSize = 28.sp,
                fontWeight = W600
            )
            TextField(shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(), label = {
                    Text("Username", color = Crayola)
                }, value = username.value,
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ), onValueChange = {
                    username.value = it
                })
            TextField(shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(), label = {
                    Text("Password", color = Crayola)
                }, colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ), value = password.value, onValueChange = {
                    password.value = it
                }, visualTransformation = if (!isPasswordVisible.value) {
                    PasswordVisualTransformation()
                } else VisualTransformation.None, trailingIcon = {
                    Crossfade(isPasswordVisible.value) {
                        if (it) {
                            Icon(
                                modifier = Modifier.clickable {
                                    isPasswordVisible.value = false
                                },
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "visible password"
                            )
                        } else Icon(
                            modifier = Modifier.clickable { isPasswordVisible.value = true },
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide password"
                        )
                    }
                })

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Verify Captcha")
                Crossfade(targetState = captchaLoading) {
                    if (!it) {
                        Checkbox(checked = isCaptchaSuccess == true, onCheckedChange = {
                            SafetyNet.getClient(context)
                                .verifyWithRecaptcha("6LfcrCUiAAAAAFQsk-JAPmqqyHLrM1f_C4diesL2")
                                .addOnSuccessListener(context as Activity) { response ->
                                    val userResponseToken = response.tokenResult
                                    if (response.tokenResult?.isNotEmpty() == true) {
                                        captchaToken = userResponseToken
                                    }
                                }
                                .addOnFailureListener(context) { e ->
                                    if (e is ApiException) {
                                        Timber.e(
                                            "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}"
                                        )
                                    } else {
                                        Timber.e("Error: ${e.message}")
                                    }
                                    isCaptchaSuccess = false
                                }
                        })
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }

            Button(
                enabled = username.value.isNotBlank() && password.value.isNotBlank() && isCaptchaSuccess == true && !signUpLoading,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                onClick = {
                    viewModel.signUp(username.value, password.value)
                }) {
                Crossfade(targetState = signUpLoading) {
                    if (!it) {
                        Text(text = "Sign Up", fontSize = 16.sp)
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }

            Text("or", fontSize = 16.sp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(54.dp)
                        .weight(0.5f),
                    onClick = { /*TODO*/ }) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = null
                    )
                }
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(54.dp)
                        .weight(0.5f),
                    onClick = { /*TODO*/ }) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null
                    )
                }
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(54.dp)
                        .weight(0.5f),
                    onClick = { /*TODO*/ }) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = null
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
        ) {
            Text(text = "Already have an account ?", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                modifier = Modifier.clickable {
                    navController.navigate(Navigation.SIGN_IN)
                },
                text = "Sign In", style = TextStyle(
                    textDecoration = TextDecoration.Underline
                ), color = MaterialTheme.colorScheme.tertiary, fontSize = 16.sp
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun SignUpPreview() {
    SignUp(rememberNavController())
}
