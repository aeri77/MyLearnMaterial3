package com.ayomicakes.app.helper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*

class LocationHelper(private val context: Context, private val locationCallback: LocationCallback) {
    internal var fusedLocationClient: FusedLocationProviderClient? = null
    @SuppressLint("MissingPermission")
    fun startLocationUpdate() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient?.requestLocationUpdates(createLocationRequest(), locationCallback,
            Looper.getMainLooper())
    }


    fun stopLocationUpdate() {
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