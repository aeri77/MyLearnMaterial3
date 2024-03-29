package com.ayomicakes.app.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ayomicakes.app.architecture.repository.home.HomeRepository
import com.ayomicakes.app.architecture.source.CakeSource
import com.ayomicakes.app.architecture.source.CheckoutSource
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.helper.LocationHelper
import com.ayomicakes.app.model.CheckoutModel
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.OrderStatusResponse
import com.ayomicakes.app.network.responses.PaymentTransactionResponse
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.ayomicakes.app.screen.auth.AuthViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: HomeRepository,
    private val locationHelper: LocationHelper,
    private val mainApi: AyomiCakeServices
) : AuthViewModel(repository) {

    val screens =
        listOf(Screens.ShopsPage, Screens.CartPage, Screens.OrderStatus, Screens.MessagesPage)
    private val _selectedScreens = MutableStateFlow(screens[0])
    val selectedScreens = _selectedScreens.asStateFlow()

    private var _location: MutableSharedFlow<LatLng> = MutableSharedFlow()
    val location: SharedFlow<LatLng> = _location.asSharedFlow()

    private val _cakesCart = MutableStateFlow(mapOf<CakeItem, Int>())
    val cakesCart = _cakesCart.asStateFlow()

    val cakes: Flow<PagingData<CakeItem>> =
        Pager(PagingConfig(pageSize = 4)) {
            CakeSource(mainApi)
        }.flow.cachedIn(viewModelScope)

    fun checkouts(userId : UUID?, token : String?): Flow<PagingData<OrderStatusResponse>> {
        return Pager(PagingConfig(pageSize = 10)) {
            CheckoutSource(mainApi, userId, token)
        }.flow.cachedIn(viewModelScope)
    }

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

    fun setSelectedScreen(screens: Screens) {
        viewModelScope.launch {
            _selectedScreens.emit(screens)
        }
    }


    fun setCheckout(checkoutId: String, checkoutModel: CheckoutModel) {
        savedStateHandle[checkoutId] = checkoutModel
    }

    fun getCheckout(checkoutId: String): LiveData<CheckoutModel> =
        savedStateHandle.getLiveData<CheckoutModel>(checkoutId)

    fun setTransactionRequest(ref: String?, transactionResponse: PaymentTransactionResponse) {
        if (ref != null) {
            savedStateHandle[ref] = transactionResponse
        }
    }

    fun getTransactionRequest(ref: String): LiveData<PaymentTransactionResponse> =
        savedStateHandle.getLiveData<PaymentTransactionResponse>(ref)
}