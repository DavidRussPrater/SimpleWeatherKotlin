package com.example.android.simpleweather.models

import java.io.Serializable

data class Current(

    val clouds: Int?,

    val dewPoint: Double?,

    val dt: Long?,

    val feelsLike: Double?,

    val humidity: Int?,

    val pressure: Int?,

    val sunrise: Long?,

    val sunset: Long?,

    val temp: Double?,

    val uvi: Double?,

    val visibility: Double?,

    val weather: List<Weather?>?,

    val windDeg: Int?,

    val windSpeed: Double?
): Serializable