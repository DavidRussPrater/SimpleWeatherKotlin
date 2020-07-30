package com.example.android.simpleweather.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Current(

    val clouds: Int?,

    @SerializedName("dew_point")
    val dewPoint: Double?,

    val dt: Long?,

    @SerializedName("feels_like")
    val feelsLike: Double?,

    val humidity: Int?,

    val pressure: Int?,

    val sunrise: Long?,

    val sunset: Long?,

    val temp: Double?,

    val uvi: Double?,

    val visibility: Double?,

    val weather: List<Weather?>?,

    @SerializedName("wind_deg")
    val windDeg: Int?,
    @SerializedName("wind_speed")
    val windSpeed: Double?
): Serializable