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
import com.ayomicakes.app.helper.LocationHelper
import com.ayomicakes.app.network.responses.FullResponse
import com.ayomicakes.app.network.services.AyomiCakeServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val locationHelper: LocationHelper,
    private val mainApi: AyomiCakeServices
) : MainViewModel(repository, locationHelper) {

    val cakes: Flow<PagingData<CakeItem>> =
        Pager(PagingConfig(pageSize = 4)) {
            CakeSource(mainApi)
        }.flow.cachedIn(viewModelScope)
    private val _cake = MutableSharedFlow<com.ayomicakes.app.utils.Result<FullResponse<CakeItem>>>()
    val cake: SharedFlow<com.ayomicakes.app.utils.Result<FullResponse<CakeItem>>> = _cake
    fun getCakes(uuid: UUID) {
        viewModelScope.launch {
            repository.getCakes(uuid).collectLatest {
                _cake.emit(it)
            }
        }
    }
}