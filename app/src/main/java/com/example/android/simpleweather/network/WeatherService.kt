package com.example.android.simpleweather.network

import com.example.android.simpleweather.models.WeatherResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface WeatherService {

    @GET("2.5/onecall")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String?,
        @Query("exclude") exclude: String,
        @Query("appid") appid: String?

    ): Call<WeatherResponse>


}