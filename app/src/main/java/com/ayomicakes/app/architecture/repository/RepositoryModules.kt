package com.ayomicakes.app.architecture.repository

import android.content.Context
import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.auth.AuthRepositoryImpl
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.architecture.repository.base.BaseRepositoryImpl
import com.ayomicakes.app.architecture.repository.home.HomeRepository
import com.ayomicakes.app.architecture.repository.home.HomeRepositoryImpl
import com.ayomicakes.app.database.dao.CakeDao
import com.ayomicakes.app.database.database.CakeDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {

    @Binds
    fun provideBaseRepositoryImpl(repository: BaseRepositoryImpl): BaseRepository

    @Binds
    fun provideAuthRepositoryImpl(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideHomeRepositoryImpl(repository: HomeRepositoryImpl): HomeRepository
}