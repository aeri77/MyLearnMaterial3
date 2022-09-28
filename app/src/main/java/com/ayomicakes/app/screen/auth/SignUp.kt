package com.ayomicakes.app.screen.auth.singup

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.component.buttons.CaptchaButton
import com.ayomicakes.app.component.buttons.SignWithGoogle
import com.ayomicakes.app.component.textfield.PasswordTextField
import com.ayomicakes.app.component.textfield.UsernameTextField
import com.ayomicakes.app.navigation.Navigation
import com.ayomicakes.app.screen.auth.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import timber.log.Timber

@ExperimentalMaterial3Api
@Composable
fun SignUp(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val mainColor = MaterialTheme.colorScheme.primary
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val signUpLoading by viewModel.signLoading.collectAsState()
    val userStore by viewModel.userStore.collectAsState(null)
    val isCaptchaLoading by viewModel.captchaLoading.collectAsState()
    val oauthLoading by viewModel.oauthLoading.collectAsState()
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
        if (captchaToken?.isNotBlank() == true) {
            viewModel.sendCaptcha(captchaToken)
        }
    }

    LaunchedEffect(captchaResponse) {
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
            UsernameTextField(username)
            PasswordTextField(password)
            CaptchaButton(
                isCaptchaSuccess = isCaptchaSuccess,
                isCaptchaLoading = isCaptchaLoading,
                onFailure = {
                    if (it is ApiException) {
                        Timber.e(
                            "Error: ${CommonStatusCodes.getStatusCodeString(it.statusCode)}"
                        )
                    } else {
                        Timber.e("Error: ${it.message}")
                    }
                    isCaptchaSuccess = false
                },
                onSuccess = {
                    captchaToken = it.tokenResult
                })
            Button(
                enabled = username.value.isNotBlank() && password.value.isNotBlank() && isCaptchaSuccess == true && !signUpLoading && !oauthLoading,
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
            Crossfade(targetState = oauthLoading) {
                if(!it){
                    SignWithGoogle("Sign Up With Google") { authResult ->
                        viewModel.verifyOAuth(authResult?.idToken.toString(), context)
                    }
                }
                else CircularProgressIndicator()
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
