package com.example.android.simpleweather.data.api

import com.example.android.simpleweather.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {

    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
    .readTimeout(5, TimeUnit.SECONDS)
    .connectTimeout(5, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.SECONDS)
    .callTimeout(15, TimeUnit.SECONDS)
    .addInterceptor(logging)
    .build()

    val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(
    Json {
        isLenient = true
        ignoreUnknownKeys = true
    }.asConverterFactory("application/json".toMediaType())
    )
    .addConverterFactory(EnumConverterFactory())
    .client(okHttpClient)
    .build()

    fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)

}