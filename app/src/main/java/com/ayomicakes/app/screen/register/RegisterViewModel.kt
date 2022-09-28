package com.ayomicakes.app.screen.register

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.helper.LocationHelper
import com.ayomicakes.app.network.requests.RegisterFormRequest
import com.ayomicakes.app.network.responses.Response
import com.ayomicakes.app.screen.register.component.AutoTextColor
import com.ayomicakes.app.utils.StringUtils.getBearer
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: BaseRepository,
    private var locationHelper: LocationHelper
) : ViewModel() {

    private val _addressList = MutableSharedFlow<List<Address>?>()
    private val _registerResponse = MutableSharedFlow<Response>()
    val registerResponse: SharedFlow<Response> = _registerResponse
    val isLoading = MutableLiveData(false)
    val autoTextColor = MutableLiveData(AutoTextColor.NONE)
    val addressList: SharedFlow<List<Address>?> = _addressList

    /**
     * Deprecation on API 33
     **/
    @Suppress("DEPRECATION")
    fun getAddressByLatLng(context: Context, latLng: LatLng?) {
        autoTextColor.postValue(AutoTextColor.NONE)
        val geocoder = Geocoder(context)
        try {
            val address =
                geocoder.getFromLocation(latLng?.latitude ?: 0.0, latLng?.longitude ?: 0.0, 1)
                    ?.toList()
            viewModelScope.launch {
                autoTextColor.postValue(AutoTextColor.UPDATE)
                delay(2000)
                _addressList.emit(address)
                delay(500)
                autoTextColor.postValue(AutoTextColor.NONE)
                isLoading.postValue(false)
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            isLoading.postValue(false)
            autoTextColor.postValue(AutoTextColor.NONE)
        }
    }

    fun getLocation(context: Context) {
        isLoading.postValue(true)
        locationHelper.fusedLocationClient?.lastLocation?.addOnSuccessListener {
            viewModelScope.launch {
                getAddressByLatLng(context, LatLng(it.latitude, it.longitude))
            }
        }
    }

    fun postRegisterForm(
        address: String,
        locality: String,
        subAdmin: String,
        fullName: String,
        phone: String,
        postalCode: String,
        listAddress: List<Address>?,
        ) {
        viewModelScope.launch {
           try {
               repository.getUserStore().collectLatest {
                   val registerForm = RegisterFormRequest(
                       userId = it?.userId,
                       address = address,
                       locality = locality,
                       subAdminArea = subAdmin,
                       phone = phone,
                       fullName = fullName,
                       postalCode = postalCode,
                       adminArea = listAddress?.get(0)?.adminArea,
                       countryID = listAddress?.get(0)?.countryCode,
                       countryName = listAddress?.get(0)?.countryName,
                       latitude = listAddress?.get(0)?.latitude,
                       longitude = listAddress?.get(0)?.longitude
                   )
                   repository.postRegisterForm(it?.accessToken?.getBearer() ?: "", registerForm).collectLatest { res ->
                       _registerResponse.emit(res)
                   }
               }
           } catch (e : Exception){
               Timber.e(e.message)
           }
        }
    }
}