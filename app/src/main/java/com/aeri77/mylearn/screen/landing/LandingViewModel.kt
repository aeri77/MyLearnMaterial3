package com.aeri77.mylearn.screen.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeri77.mylearn.UserStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val repository : LandingRepository
) : ViewModel(){

    private val _userStore = MutableLiveData<UserStore>()
    val userStore : LiveData<UserStore> = _userStore


    fun initUserStore(){
        viewModelScope.launch(Dispatchers.IO){
            repository.userStoreData.collectLatest {
                _userStore.postValue(it)
            }
        }
    }

    fun clearUserStore (){
        viewModelScope.launch {
            repository.clearUserStore()
        }
    }
}