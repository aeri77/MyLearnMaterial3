package com.ayomicakes.app.architecture

import androidx.datastore.core.DataStore
import com.ayomicakes.app.datastore.serializer.UserStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface BaseRepository {
    fun getUserStore(): Flow<UserStore>

    suspend fun updateUserStore(user: UserStore)

    suspend fun clearUserStore()
}
@Singleton
class BaseRepositoryImpl @Inject constructor(
    private val userStore: DataStore<UserStore>
) : BaseRepository {

    override fun getUserStore(): Flow<UserStore> {
        return userStore.data.catch { exception ->
            when (exception) {
                is IOException -> {
                    Timber.e("Error reading store. ", exception)
                    emit(UserStore())
                }
                else -> {
                    throw exception
                }
            }
        }
    }

    override suspend fun updateUserStore(user: UserStore) {
        userStore.updateData { store ->
            store.copy(
                username = user.username,
                address = user.address,
                fullName = user.fullName,
                phone = user.phone
            )
        }
    }

    override suspend fun clearUserStore() {
        userStore.updateData {
            UserStore()
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun provideBaseRepositoryImpl(repository: BaseRepositoryImpl): BaseRepository
}