package com.aeri77.mylearn.architecture

import android.content.Context
import android.util.Log
import com.aeri77.mylearn.UserStore
import com.aeri77.mylearn.datastore.userDataStore
import com.aeri77.mylearn.model.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val userStore = context.userDataStore
    val userStoreData = userStore.data.catch {
        exception ->
        when (exception) {
            is IOException -> {
                Timber.e( "Error reading store. ", exception)
                emit(UserStore.getDefaultInstance())
            }
            else -> {
                throw exception
            }
        }
    }

    suspend fun updateUserStore(userData : UserData) =
        userStore.updateData { userStore ->
            userStore.toBuilder()
                .setUsername(userData.username)
                .setFullName(userData.fullName)
                .setAddress(userData.address)
                .setPhone(userData.phone)
                .build()
    }

    suspend fun clearUserStore(){
        userStore.updateData { userStore ->
            userStore.toBuilder().clear().build()
        }
    }
}