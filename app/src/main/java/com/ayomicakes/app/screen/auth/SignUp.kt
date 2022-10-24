package com.ayomicakes.app.screen.auth.singup

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayomicakes.app.component.buttons.CaptchaButton
import com.ayomicakes.app.component.buttons.SignWithGoogle
import com.ayomicakes.app.component.textfield.PasswordTextField
import com.ayomicakes.app.component.textfield.UsernameTextField
import com.ayomicakes.app.network.requests.AuthRequest
import com.ayomicakes.app.network.requests.OAuthRequest
import com.ayomicakes.app.screen.auth.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber

@ExperimentalMaterial3Api
@Composable
fun SignUp(
    viewModel: AuthViewModel = hiltViewModel(),
    onSuccessSignUp: () -> Unit,
    onSignIn: () -> Unit
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val mainColor = MaterialTheme.colorScheme.primary
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val signUpLoading by viewModel.signLoading.collectAsState()
    val userStore by viewModel.userStore.observeAsState(null)
    val isCaptchaLoading by viewModel.captchaLoading.collectAsState()
    val oauthLoading by viewModel.oauthLoading.collectAsState()
    var captchaToken by remember { mutableStateOf<String?>(null) }
    var isCaptchaSuccess by remember { mutableStateOf<Boolean?>(null) }
    val captchaResponse by viewModel.captchaResponse.collectAsState(null)


    systemUiController.setStatusBarColor(mainColor)

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
        Timber.d("userstore sign up :$userStore")
        if (userStore != null) {
            if (userStore?.userId != null) {
                if (userStore?.userId?.toString()?.isNotBlank() == true) {
                    onSuccessSignUp()
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
                    val firebaseMessaging = FirebaseMessaging.getInstance()
                    firebaseMessaging.isAutoInitEnabled = true
                    firebaseMessaging.token.addOnSuccessListener {
                        val authRequest = AuthRequest(
                            username.value,
                            password.value,
                            it
                        )
                        viewModel.signUp(authRequest)
                    }
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
                if (!it) {
                    SignWithGoogle("Sign Up With Google") { authResult ->
                        val firebaseMessaging = FirebaseMessaging.getInstance()
                        firebaseMessaging.isAutoInitEnabled = true
                        firebaseMessaging.token.addOnSuccessListener { fcm ->
                            val oAuthRequest = OAuthRequest(
                                authResult?.idToken.toString(),
                                fcm
                            )
                            viewModel.verifyOAuth(oAuthRequest, context)
                        }
                    }
                } else CircularProgressIndicator()
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
                    onSignIn()
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
    SignUp(onSuccessSignUp = {

    }, onSignIn = {

    })
}
