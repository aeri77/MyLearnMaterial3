package com.aeri77.mylearn.screen.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aeri77.mylearn.screen.landing.enums.PageStateEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor() : ViewModel() {
    val pagePosition = mutableStateOf(PageStateEnum.First)
    var loading by mutableStateOf(false)
    val string = MutableLiveData<String>("")

    fun setLoading(){
        loading = !loading
    }
}