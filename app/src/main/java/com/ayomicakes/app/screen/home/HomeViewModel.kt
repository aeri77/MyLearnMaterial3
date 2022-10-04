package com.ayomicakes.app.screen.home

import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.helper.LocationHelper
import com.ayomicakes.app.network.responses.CakeItem
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ayomicakes.app.utils.Result
import kotlinx.coroutines.flow.SharedFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val locationHelper: LocationHelper
) : MainViewModel(repository, locationHelper) {

    private val _cakeResponse: MutableSharedFlow<Result<FullResponse<PageModel<CakeItem>>>> =
        MutableSharedFlow()
    val cakeResponse: SharedFlow<Result<FullResponse<PageModel<CakeItem>>>> = _cakeResponse

    fun getCakes() {
        viewModelScope.launch {
            _cakeResponse.emit(Result.Loading)
            repository.getUserStore().collectLatest {
                repository.getCakes(it).collectLatest { res ->
                    res.refreshToken {
                        _cakeResponse.emit(res)
                    }
                }
            }

        }
    }
    private suspend inline fun <R> Result<R>.refreshToken(crossinline invoke: suspend (Result<R>) -> Unit){
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