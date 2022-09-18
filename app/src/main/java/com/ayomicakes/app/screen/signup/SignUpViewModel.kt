package com.ayomicakes.app.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.datastore.serializer.UserStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {
    fun signUp() {
        viewModelScope.launch {
            repository.updateUserStore(
                UserStore(
                    "Aeri",
                    "Aeri",
                    "Bogor",
                    "088219615853"
                )
            )
        }
    }
}