package com.example.android.simpleweather.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hourly(

    val clouds: Int?,

    val dewPoint: Double?,

    val dt: Long,

    val feelsLike: Double?,

    val humidity: Int?,

    val pressure: Int?,

    val sunrise: Int?,

    val sunset: Int?,

    val temp: Double?,

    val uvi: Double?,

    val visibility: Int?,

    @SerializedName("wind_deg")
    val windDeg: Int?,
    @SerializedName("wind_speed")
    val windSpeed: Double?,
    val weather: List<Weather?>?

): Serializable