package com.example.android.simpleweather.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyDTO(
    @SerialName("dt")
    val time: Int,
    @SerialName("temp")
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("uvi")
    val uvi: Double,
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("visibility")
    val visibility: Int,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double,
    @SerialName("weather")
    val weather: List<WeatherDTO>,
    @SerialName("pop")
    val precipProbability: Double
)