package com.ayomicakes.app.screen.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.helper.LocationHelper
import com.ayomicakes.app.network.requests.AuthRequest
import com.ayomicakes.app.network.requests.CaptchaRequest
import com.ayomicakes.app.network.requests.OAuthRequest
import com.ayomicakes.app.network.responses.CaptchaResponse
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.oauth.GoogleOauth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
) : MainViewModel(
    repository = repository
) {

    private val _captchaResponse: MutableSharedFlow<FullResponse<CaptchaResponse>?> =
        MutableSharedFlow()
    val captchaResponse: MutableSharedFlow<FullResponse<CaptchaResponse>?> = _captchaResponse
    val captchaLoading = MutableStateFlow(false)
    val signLoading = MutableStateFlow(false)
    val oauthLoading = MutableStateFlow(false)
    fun signUp(username: String, password: String) {
        viewModelScope.launch {
            signLoading.emit(true)
            try {
                val authRequest = AuthRequest(
                    username,
                    password
                )
                repository.signUp(authRequest).collectLatest {
                    repository.signIn(authRequest).collectLatest {
                        repository.updateUserStore(
                            user = UserStore(
                                userId = it.result.userId,
                                accessToken = it.result.accessToken,
                                refreshToken = it.result.refreshToken
                            )
                        )
                        signLoading.emit(false)
                    }
                }
            } catch (e: Exception) {
                Timber.d("sign Up error : ${e.message}")
                signLoading.emit(false)
            } finally {
                signLoading.emit(false)
            }
        }
    }

    fun sendCaptcha(captchaToken: String?) {
        viewModelScope.launch {
            captchaLoading.emit(true)
            try {
                repository.sendCaptcha(CaptchaRequest(captchaToken)).collectLatest {
                    _captchaResponse.emit(it)
                    captchaLoading.emit(false)
                }
            } catch (e: Exception) {
                Timber.e(e.message)
                captchaLoading.emit(false)
            } finally {
                captchaLoading.emit(false)
            }
        }
    }

    fun signIn(username: String, password: String) {
        val authRequest = AuthRequest(
            username,
            password
        )
        viewModelScope.launch {
            signLoading.emit(true)
            try {
                repository.signIn(authRequest).collectLatest {
                    repository.updateUserStore(
                        user = UserStore(
                            userId = it.result.userId,
                            accessToken = it.result.accessToken,
                            refreshToken = it.result.refreshToken
                        )
                    )
                }
            } catch (e: Exception) {
                Timber.e(e.message)
                signLoading.emit(false)
            } finally {
                signLoading.emit(false)
            }
        }
    }

    fun verifyOAuth(idToken: String , context : Context) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        viewModelScope.launch {
            oauthLoading.emit(true)
            try {
                repository.verifyOAuth(OAuthRequest(idToken)).collectLatest {
                    repository.updateUserStore(
                        user = UserStore(
                            userId = it.result.userId,
                            accessToken = it.result.accessToken,
                            refreshToken = it.result.refreshToken
                        )
                    )
                    oauthLoading.emit(false)
                }
            } catch (e: Exception) {
                if(e is HttpException){
                    if(account != null){
                        GoogleOauth.getGoogleLoginAuth(context).signOut().addOnSuccessListener {
                            return@addOnSuccessListener
                        }
                    }
                }
                oauthLoading.emit(false)
            } finally {
                oauthLoading.emit(false)
            }
        }
    }

}