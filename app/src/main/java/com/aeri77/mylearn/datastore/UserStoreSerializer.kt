package com.aeri77.mylearn.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.aeri77.mylearn.UserStore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.OutputStream

@Module
@InstallIn(ActivityComponent::class)
object UserStoreSerializer : Serializer<UserStore> {
    override val defaultValue: UserStore
        get() = UserStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserStore {
        val parser = withContext(Dispatchers.IO) {
            kotlin.runCatching {
                UserStore.parseFrom(input)
            }
        }
        return parser.getOrThrow()
    }

    override suspend fun writeTo(t: UserStore, output: OutputStream) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                t.writeTo(output)
            }
        }
    }
}

private const val USER_STORE_FILENAME = "user_store.pb"

val Context.userDataStore: DataStore<UserStore> by dataStore(
    fileName = USER_STORE_FILENAME,
    serializer = UserStoreSerializer
)