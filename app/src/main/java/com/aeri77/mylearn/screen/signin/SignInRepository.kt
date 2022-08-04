package com.aeri77.mylearn.screen.signin

import android.content.Context
import com.aeri77.mylearn.architecture.BaseRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SignInRepository @Inject constructor(
    @ApplicationContext context: Context
) : BaseRepository(
    context = context
) {

}