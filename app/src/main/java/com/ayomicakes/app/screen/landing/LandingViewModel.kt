package com.ayomicakes.app.screen.landing

import androidx.lifecycle.ViewModel
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val repository : BaseRepository
) : ViewModel(){

}