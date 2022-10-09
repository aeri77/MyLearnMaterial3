package com.ayomicakes.app

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.helper.LocationHelper
import com.ayomicakes.app.network.requests.RefreshRequest
import com.ayomicakes.app.screen.home.Screens
import com.ayomicakes.app.utils.StringUtils.getBearer
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ayomicakes.app.utils.Result
import timber.log.Timber

@HiltViewModel
open class MainViewModel @Inject constructor(
    private val repository: AuthRepository ,
    private var locationHelper: LocationHelper
) : ViewModel() {

    val isToolbarHidden = MutableStateFlow(true)
    val isSideDrawerActive = MutableStateFlow(false)
    val toolbarTitle = MutableStateFlow("")
    private var location: MutableSharedFlow<LatLng> = MutableSharedFlow()
    private val _userStore = MutableLiveData<UserStore>()
    private val _profileStore = MutableLiveData<ProfileStore>()
    val isAuthenticated = MutableStateFlow(false)
    val userStore: LiveData<UserStore> = _userStore
    val profileStore: LiveData<ProfileStore> = _profileStore
    val items = listOf(Screens.ShopsPage, Screens.CartPage, Screens.MessagesPage)
    val selectedItems = mutableStateOf(items[0])

    val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            viewModelScope.launch {
                location.emit(
                    LatLng(
                        result.lastLocation?.latitude ?: 0.0,
                        result.lastLocation?.longitude ?: 0.0
                    )
                )
            }
        }
    }

    open fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserStore().collectLatest {
                if (it?.accessToken != null) {
                    Timber.d("profile store launched")
                    repository.getProfile(it.accessToken.getBearer(), it.userId)
                        .collectLatest { res ->
                            res.refreshToken {
                                if (res is Result.Success) {
                                    repository.updateProfileStore(res.data.result)
                                }
                            }
                        }
                }
            }
        }
    }

    fun initUserStore() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserStore().collectLatest {
                _userStore.postValue(it)
            }
        }
    }

    fun initProfileStore() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProfileStore().collectLatest {
                _profileStore.postValue(it)
            }
        }
    }

    fun clearStore() {
        viewModelScope.launch {
            repository.clearStore()
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
    fun startLocationUpdate(locationCallback: LocationCallback) {
        locationHelper.startLocationUpdate(locationCallback)
    }

    fun stopLocationUpdate(locationCallback: LocationCallback) {
        locationHelper.stopLocationUpdate(locationCallback)
    }

    open fun postRefreshToken(userStore: UserStore?) {
        viewModelScope.launch {
            repository.postRefreshToken(userStore, RefreshRequest(userStore?.userId))
                .collectLatest {
                    if (it is Result.Success) {
                        repository.updateUserStore(it.data.result)
                    }
                }
        }
    }

    suspend inline fun <R> Result<R>.refreshToken(crossinline invoke: suspend (Result<R>) -> Unit) {
        if (this is Result.Success) {
            invoke(this)
        }
        if (this is Result.Error) {
            if (this.errCode == 401) {
                invoke(this.copy(errCode = 401))
                isAuthenticated.emit(false)
            }
        }
    }
}