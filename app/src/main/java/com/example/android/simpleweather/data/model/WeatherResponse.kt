package com.example.android.simpleweather.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_offset")
    val timezoneOffset: Int,
    @SerialName("current")
    val current: CurrentDTO,
    @SerialName("hourly")
    val hourly: List<HourlyDTO>,
    @SerialName("daily")
    val daily: List<DailyDTO>
)