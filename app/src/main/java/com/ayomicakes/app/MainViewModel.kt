package com.ayomicakes.app

import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.datastore.serializer.UserStore
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    val isToolbarHidden = MutableStateFlow(true)
    val isSideDrawerActive = MutableStateFlow(false)
    val toolbarTitle = MutableStateFlow("")
    private val _userStore = MutableLiveData<UserStore>()
    val userStore : LiveData<UserStore> = _userStore

    fun initUserStore(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getUserStore().collectLatest {
                _userStore.postValue(it)
            }
        }
    }

    fun clearUserStore (){
        viewModelScope.launch {
            repository.clearUserStore()
        }
    }

    fun setToolbarHidden(isHidden:Boolean) {
        viewModelScope.launch {
            isToolbarHidden.value = isHidden
        }
    }
    fun setSideDrawerActive(isActive: Boolean){
        viewModelScope.launch {
            isSideDrawerActive.value = isActive
        }
    }
    fun setToolbarTitle(title: String){
        viewModelScope.launch {
            toolbarTitle.value = title
        }
    }
    fun setToolbar(isHidden: Boolean, isActive:Boolean, title: String){
        setToolbarHidden(isHidden)
        setSideDrawerActive(isActive)
        setToolbarTitle(title)
    }
}