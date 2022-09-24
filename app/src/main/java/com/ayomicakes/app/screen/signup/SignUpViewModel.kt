package com.ayomicakes.app.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.requests.AuthRequest
import com.ayomicakes.app.network.requests.CaptchaRequest
import com.ayomicakes.app.network.responses.AuthResponse
import com.ayomicakes.app.network.responses.CaptchaResponse
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    val userStore = repository.getUserStore()

    private val _captchaResponse: MutableSharedFlow<FullResponse<CaptchaResponse>?> =
        MutableSharedFlow()
    val captchaResponse: MutableSharedFlow<FullResponse<CaptchaResponse>?> = _captchaResponse
    val captchaLoading = MutableStateFlow(false)
    val signUpLoading = MutableStateFlow(false)
    fun signUp(username: String, password: String) {
        viewModelScope.launch {
            signUpLoading.emit(true)
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
                        signUpLoading.emit(false)
                    }
                }
            } catch (e: Exception) {
                Timber.d("sign Up error : ${e.message}")
                signUpLoading.emit(false)
            } finally {
                signUpLoading.emit(false)
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

}