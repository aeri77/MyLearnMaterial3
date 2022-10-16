@file:Suppress("DEPRECATION")

package com.ayomicakes.app.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng

object MapUtils {
    fun getLocationByLatLng(latLng: LatLng, context: Context, maxResult: Int = 1) : List<Address>?{
        val geocoder = Geocoder(context)
        return geocoder.getFromLocation(latLng.latitude, latLng.longitude, maxResult)
            ?.toList()
    }
}