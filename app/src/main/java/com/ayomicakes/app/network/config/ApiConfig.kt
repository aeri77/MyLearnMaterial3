@file:OptIn(ExperimentalSerializationApi::class)

package com.ayomicakes.app.network.config

import com.ayomicakes.app.BuildConfig
import com.ayomicakes.app.network.services.AyomiCakeServices
import com.ayomicakes.app.network.services.MapServices
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {

        private const val GOOGLE_MAP_URL =
            "https://maps.googleapis.com/maps/api/"
        // Live
        private const val MAIN_APP_URL = "https://ayomicake-api.herokuapp.com/"
        // Dev
//        private const val MAIN_APP_URL = "http://10.0.2.2:8118/"



        fun getAppServices(): AyomiCakeServices {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val contentType = "application/json".toMediaType()
            val retrofit = Retrofit.Builder()
                .baseUrl(MAIN_APP_URL)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .client(client)
                .build()
            return retrofit.create(AyomiCakeServices::class.java)
        }

        fun getMapServices(): MapServices {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val contentType = "application/json".toMediaType()
            val retrofit = Retrofit.Builder()
                .baseUrl(GOOGLE_MAP_URL)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .client(client)
                .build()

            return retrofit.create(MapServices::class.java)
        }
    }
}