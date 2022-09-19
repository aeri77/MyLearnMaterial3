package com.ayomicakes.app.helper

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    internal var fusedLocationClient: FusedLocationProviderClient? = null

    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdate(locationCallback: LocationCallback) {
        fusedLocationClient?.requestLocationUpdates(
            createLocationRequest(), locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdate(locationCallback: LocationCallback) {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val INTERVAL: Long = 5000
        private const val FASTEST_INTERVAL: Long = 1000

        fun createLocationRequest(): LocationRequest {
            return LocationRequest.create().apply {
                interval = INTERVAL
                fastestInterval = FASTEST_INTERVAL
                priority = Priority.PRIORITY_HIGH_ACCURACY
            }
        }
    }
}