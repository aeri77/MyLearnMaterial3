package com.ayomicakes.app.network.services

import com.ayomicakes.app.network.responses.GeoCodeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapServices {
    @GET("geocode/json?")
    fun getGeocode(
        @Query("latlng", encoded = true) latLng: String,
        @Query("key") key: String
    ): Call<GeoCodeResponse>
}