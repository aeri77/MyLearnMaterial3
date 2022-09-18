package com.ayomicakes.app.screen.register

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.network.responses.GeoCodeResponse
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    private val _geoCodeResponse = MutableSharedFlow<GeoCodeResponse>()
    private val _addressList = MutableLiveData<List<Address>>()

    val geoCodeResponse: SharedFlow<GeoCodeResponse> = _geoCodeResponse
    val addressList: LiveData<List<Address>> = _addressList

    fun getAddressByLatLng(latLng: LatLng) {
        viewModelScope.launch {
            try {
                Timber.d("okhttp latlng = $latLng.")
                repository.getAddressByLatLng(latLng).collectLatest {
                    _geoCodeResponse.emit(it)
                }
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
    }

    /**
     * Deprecation on API 33
     **/
    @Suppress("DEPRECATION")
    fun getAddressByLatLng(context: Context, latLng: LatLng) {
        val geocoder = Geocoder(context)
        try {
          _addressList.postValue(geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1))
        } catch(e :Exception){
            Timber.e(e.message)
        }
    }
}