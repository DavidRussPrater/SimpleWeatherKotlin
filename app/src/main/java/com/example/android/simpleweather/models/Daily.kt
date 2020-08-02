package com.example.android.simpleweather.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Daily (

    val clouds: Int?,

    @SerializedName("dew_point")
    val dewPoint: Double?,

    val dt: Long,

    val feelsLike: FeelsLike?,

    val humidity: Int?,

    val pop: Double?,

    val pressure: Int?,

    val rain: Double?,

    val sunrise: Long?,

    val sunset: Long?,

    val temp: Temp?,

    val uvi: Double?,

    val weather: List<Weather?>?,

    @SerializedName("wind_deg")
    val windDeg: Int?,
    @SerializedName("wind_speed")
    val windSpeed: Double?
): Serializable