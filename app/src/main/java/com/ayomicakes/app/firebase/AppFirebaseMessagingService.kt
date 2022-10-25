@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, ExperimentalPagerApi::class,
    ExperimentalPagerApi::class
)

package com.ayomicakes.app.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import com.ayomicakes.app.MainActivity
import com.ayomicakes.app.R
import com.ayomicakes.app.datastore.serializer.UserStore
import com.ayomicakes.app.network.requests.FCMTokenRequest
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


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
                }
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.collapseKey == PAYMENTS_KEY){
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(message.notification?.title)
                .setContentText(message.notification?.body)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            createNotificationChannel()

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "PAYMENT_CHANNEL"
        const val PAYMENTS_KEY = "payments_key"
        const val CHANNEL_NAME = "Payment"
        const val CHANNEL_DESCRIPTION = "Payment Notification"
        const val NOTIFICATION_ID = 21
    }
}