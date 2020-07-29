package com.example.android.simpleweather.models

import java.io.Serializable

class Daily (

    val clouds: Int?,

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

    val windDeg: Int?,

    val windSpeed: Double?
): Serializable