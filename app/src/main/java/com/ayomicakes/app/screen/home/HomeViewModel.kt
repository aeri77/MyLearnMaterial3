package com.ayomicakes.app.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ayomicakes.app.MainViewModel
import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.architecture.repository.home.HomeRepository
import com.ayomicakes.app.architecture.source.CakeSource
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.helper.LocationHelper
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.responses.PageModel
import com.ayomicakes.app.network.services.AyomiCakeServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ayomicakes.app.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val locationHelper: LocationHelper,
    private val mainApi: AyomiCakeServices
) : MainViewModel(repository, locationHelper) {

    private val _cakeResponse: MutableSharedFlow<Result<FullResponse<PageModel<CakeItem>>>> =
        MutableSharedFlow()
    val cakeResponse: SharedFlow<Result<FullResponse<PageModel<CakeItem>>>> = _cakeResponse

    val cakes: Flow<PagingData<CakeItem>> =
        Pager(PagingConfig(pageSize = 20)) {
            CakeSource(mainApi)
        }.flow

    fun getCakes(page: Int = 1) {
        viewModelScope.launch {
            _cakeResponse.emit(Result.Loading)
            repository.getUserStore().collectLatest {
                Timber.d("profile store get cakes ")
                repository.getCakes(page).collectLatest { res ->
                    res.refreshToken {
                        _cakeResponse.emit(res)
                    }
                }
            }

        }
    }
}