package com.ayomicakes.app.screen.signin

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    fun signIn(){
        viewModelScope.launch {
            repository.updateUserStore(
                UserStore(
                    UUID.randomUUID(),
                    "adada231231",
                    "d21312415415151"
                )
            )
        }
    }
}