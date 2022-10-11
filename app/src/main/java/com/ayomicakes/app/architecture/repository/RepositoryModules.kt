package com.ayomicakes.app.architecture.repository

import com.ayomicakes.app.architecture.repository.auth.AuthRepository
import com.ayomicakes.app.architecture.repository.auth.AuthRepositoryImpl
import com.ayomicakes.app.architecture.repository.base.BaseRepository
import com.ayomicakes.app.architecture.repository.base.BaseRepositoryImpl
import com.ayomicakes.app.architecture.repository.home.HomeRepository
import com.ayomicakes.app.architecture.repository.home.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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