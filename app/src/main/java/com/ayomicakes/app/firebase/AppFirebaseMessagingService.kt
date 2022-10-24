package com.ayomicakes.app.firebase

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.ayomicakes.app.datastore.UserStoreSerializer
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.di.AppModule
import com.ayomicakes.app.network.config.ApiConfig
import com.ayomicakes.app.network.requests.FCMTokenRequest
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class AppFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var userStore: DataStore<UserStore>

    @Inject
    lateinit var mainApi: AyomiCakeServices
    override fun onNewToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userStore.data.collectLatest {
                if (it.userId != null) {
                    val res = mainApi.postFCMToken(FCMTokenRequest(it.userId, token))
                    Timber.d("fcm new token res: ${res.result}")
                }
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.d("fcm message log : $message")
    }
}