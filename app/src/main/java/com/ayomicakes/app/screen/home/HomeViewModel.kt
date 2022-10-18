package com.ayomicakes.app.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.architecture.repository.home.HomeRepository
import com.ayomicakes.app.architecture.source.CakeSource
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.database.model.CartItem
import com.ayomicakes.app.helper.LocationHelper
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val locationHelper: LocationHelper,
    private val mainApi: AyomiCakeServices
) : MainViewModel(repository) {

    private var _location: MutableSharedFlow<LatLng> = MutableSharedFlow()
    val location: SharedFlow<LatLng> = _location.asSharedFlow()

    private val _cakesCart = MutableStateFlow(mapOf<CakeItem, Int>())
    val cakesCart = _cakesCart.asStateFlow()

    val cakes: Flow<PagingData<CakeItem>> =
        Pager(PagingConfig(pageSize = 4)) {
            CakeSource(mainApi)
        }.flow.cachedIn(viewModelScope)
    private val _cake = MutableSharedFlow<com.ayomicakes.app.utils.Result<FullResponse<CakeItem>>>()
    val cake = _cake.asSharedFlow()

    val getUserAddress = repository.getProfileStore()
    fun getCakes(uuid: UUID) {
        viewModelScope.launch {
            repository.getCakes(uuid).collectLatest {
                _cake.emit(it)
            }
        }
    }

    fun addToCart(count: Int = 1, cartItem: CakeItem) {
        viewModelScope.launch {
            val map = _cakesCart.value.toMutableMap()
            if (map[cartItem] != null) {
                map[cartItem] = (map[cartItem] ?: 0) + count
            }
            if (map[cartItem] == null) {
                map[cartItem] = count
            }
            _cakesCart.emit(map)
        }
    }

    fun removeFromCart(count: Int? = null, cart: CakeItem) {
        viewModelScope.launch {
            val map = _cakesCart.value.toMutableMap()
            if (count == null && map[cart] != null || (map[cart] ?: 0) - (count ?: 0) <= 0) {
                map.remove(cart)
            }
            if (map[cart] != null && count != null) {
                map[cart] = (map[cart] ?: 0) - count
            }
            _cakesCart.emit(map)
        }
    }

    fun getLocation() {
        locationHelper.fusedLocationClient?.lastLocation?.addOnSuccessListener { result ->
            viewModelScope.launch {
                Timber.d("location result :$result")
                _location.emit(
                    LatLng(
                        result.latitude,
                        result.longitude
                    )
                )
            }
        }
    }
}