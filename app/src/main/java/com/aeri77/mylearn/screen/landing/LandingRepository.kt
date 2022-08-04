package com.aeri77.mylearn.screen.landing

import android.content.Context
import com.aeri77.mylearn.architecture.BaseRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LandingRepository @Inject constructor(
    @ApplicationContext context: Context
) : BaseRepository(
    context = context
) {

}