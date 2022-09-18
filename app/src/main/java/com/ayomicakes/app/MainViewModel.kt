package com.ayomicakes.app

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.helper.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
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

    private var locationHelper: LocationHelper? = null
    val isToolbarHidden = MutableStateFlow(true)
    val isSideDrawerActive = MutableStateFlow(false)
    val toolbarTitle = MutableStateFlow("")
    private var location: MutableStateFlow<LatLng> = MutableStateFlow(
        LatLng (0.0, 0.0)
    )
    private val _userStore = MutableLiveData<UserStore>()
    val userStore: LiveData<UserStore> = _userStore

    val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            location.value =
                LatLng(result.lastLocation?.latitude ?: 0.0, result.lastLocation?.longitude ?: 0.0)
        }
    }

    fun initUserStore() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserStore().collectLatest {
                _userStore.postValue(it)
            }
        }
    }

    fun clearUserStore() {
        viewModelScope.launch {
            repository.clearUserStore()
        }
    }

    fun setToolbarHidden(isHidden: Boolean) {
        viewModelScope.launch {
            isToolbarHidden.value = isHidden
        }
    }

    fun setSideDrawerActive(isActive: Boolean) {
        viewModelScope.launch {
            isSideDrawerActive.value = isActive
        }
    }

    fun setToolbarTitle(title: String) {
        viewModelScope.launch {
            toolbarTitle.value = title
        }
    }

    fun setToolbar(isHidden: Boolean, isActive: Boolean, title: String) {
        setToolbarHidden(isHidden)
        setSideDrawerActive(isActive)
        setToolbarTitle(title)
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdate(locationCallback: LocationCallback, context: Context) {
        locationHelper = LocationHelper(context, locationCallback = locationCallback)
        locationHelper?.startLocationUpdate()
    }

    fun stopLocationUpdate() {
        locationHelper?.stopLocationUpdate()
    }

    fun getLiveLocation(): MutableStateFlow<LatLng> {
        return location
    }

    fun getLocation(){
        viewModelScope.launch {
            val lastLocation = locationHelper?.fusedLocationClient?.lastLocation
            if(lastLocation?.isComplete == true){
                location.value = LatLng(lastLocation.result.latitude, lastLocation.result.longitude)
            }
        }
    }
}