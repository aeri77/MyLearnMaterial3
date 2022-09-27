package com.ayomicakes.app.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.requests.AuthRequest
import com.ayomicakes.app.network.requests.CaptchaRequest
import com.ayomicakes.app.network.requests.OAuthRequest
import com.ayomicakes.app.network.responses.CaptchaResponse
import com.ayomicakes.app.network.responses.FullResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    val userStore = repository.getUserStore()

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

    fun verifyOAuth(idToken: String) {
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
                Timber.e(e.message)
                oauthLoading.emit(false)
            } finally {
                oauthLoading.emit(false)
            }
        }
    }

}