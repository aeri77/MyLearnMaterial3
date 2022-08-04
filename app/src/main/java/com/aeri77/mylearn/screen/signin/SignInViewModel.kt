package com.aeri77.mylearn.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeri77.mylearn.model.UserData
import com.aeri77.mylearn.screen.landing.LandingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: SignInRepository
) : ViewModel() {

    fun signIn(){
        viewModelScope.launch {
            repository.updateUserStore(
                UserData(
                    "Aeri",
                    "Aeri",
                    "Bogor",
                    "088219615853"
                )
            )
        }
    }
}