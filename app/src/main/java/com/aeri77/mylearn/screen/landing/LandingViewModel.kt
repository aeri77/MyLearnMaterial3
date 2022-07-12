package com.aeri77.mylearn.screen.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeri77.mylearn.screen.landing.enums.PageStateEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor() : ViewModel() {
    val pagePosition = mutableStateOf(PageStateEnum.First)
    var loading by mutableStateOf(false)
    val string = MutableLiveData<String>("")
    val isSuccess = MutableLiveData(false)

    fun setLoading(){
        viewModelScope.launch {
            loading = !loading
            delay(3000)
            isSuccess.postValue(true)
        }
    }
}