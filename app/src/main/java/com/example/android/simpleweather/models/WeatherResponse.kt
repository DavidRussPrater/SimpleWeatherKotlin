package com.example.android.simpleweather.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherResponse(

    val current: Current?,

    val daily: List<Daily?>?,

    val hourly: List<Hourly?>?,

    val lat: Double?,

    val lon: Double?,

    val timezone: String?,

    val timezoneOffset: Int?
) : Serializable