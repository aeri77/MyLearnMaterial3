package com.aeri77.mylearn

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val isToolbarHidden = MutableStateFlow(true)

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

    fun setToolbar(isHidden:Boolean) {
        viewModelScope.launch {
            isToolbarHidden.value = isHidden
        }
    }
}