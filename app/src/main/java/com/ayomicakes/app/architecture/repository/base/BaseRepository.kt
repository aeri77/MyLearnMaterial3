package com.ayomicakes.app.architecture.repository.base

import androidx.datastore.core.DataStore
import com.ayomicakes.app.BuildConfig
import com.ayomicakes.app.database.model.CakeItem
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.config.ApiConfig
import com.ayomicakes.app.network.requests.*
import com.ayomicakes.app.network.responses.*
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.ayomicakes.app.network.services.MapServices
import com.google.android.gms.maps.model.LatLng
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.await
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import com.ayomicakes.app.utils.Result
import com.ayomicakes.app.utils.StringUtils.getBearer
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

interface BaseRepository {
    fun getUserStore(): Flow<UserStore?>
    fun getProfileStore(): Flow<ProfileStore?>
    suspend fun updateUserStore(user: UserStore)
    suspend fun updateProfileStore(profile: ProfileStore)
    suspend fun clearStore()
    suspend fun getAddressByLatLng(latLng: LatLng): Flow<GeoCodeResponse>


}



