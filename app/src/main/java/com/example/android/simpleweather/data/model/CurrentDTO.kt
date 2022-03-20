package com.example.android.simpleweather.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDTO(
    @SerialName("dt")
    val currentTime: Int,
    @SerialName("sunrise")
    val sunrise: Int,
    @SerialName("sunset")
    val sunset: Int,
    @SerialName("temp")
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("pressure")
    val pressure: Double,
    @SerialName("humidity")
    val humidity: Double,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("uvi")
    val uvi: Double,
    @SerialName("clouds")
    val clouds: Double,
    @SerialName("visibility")
    val visibility: Double,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_deg")
    val windDeg: Double,
    @SerialName("weather")
    val weather: List<WeatherDTO>
)