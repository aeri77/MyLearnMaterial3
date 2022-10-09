package com.ayomicakes.app.screen.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.screen.onboarding.enums.PageStateEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    val pagePosition = mutableStateOf(PageStateEnum.First)
    var loading by mutableStateOf(false)
    val string = MutableLiveData("")
    val isSuccess = MutableLiveData(false)

    private val _userStore = MutableLiveData<UserStore>()
    val userStore : LiveData<UserStore> = _userStore

    fun setLoading(){
        viewModelScope.launch {
            loading = !loading
            delay(1200)
            isSuccess.postValue(true)
        }
    }

}