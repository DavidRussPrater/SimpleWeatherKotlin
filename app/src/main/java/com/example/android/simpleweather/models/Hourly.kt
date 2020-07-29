package com.example.android.simpleweather.models

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

    val windDeg: Int?,

    val windSpeed: Double?,

    val weather: List<Weather?>?

): Serializable