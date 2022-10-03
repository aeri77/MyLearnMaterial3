package com.ayomicakes.app.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayomicakes.app.architecture.BaseRepository
import com.ayomicakes.app.network.responses.CakeItem
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PageModel
import com.ayomicakes.app.utils.StringUtils.getBearer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ayomicakes.app.utils.Result
import kotlinx.coroutines.flow.SharedFlow
import retrofit2.HttpException

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: BaseRepository
): ViewModel() {

    private val _cakeResponse:MutableSharedFlow<Result<FullResponse<PageModel<CakeItem>>>> = MutableSharedFlow()
    val cakeResponse : SharedFlow<Result<FullResponse<PageModel<CakeItem>>>> = _cakeResponse

    fun getCakes(){
        viewModelScope.launch {
            repository.getUserStore().collectLatest {
                repository.getCakes(it?.accessToken?.getBearer()).collectLatest { res ->
                    _cakeResponse.emit(res)
                }
            }

        }
    }
}