package com.example.android.simpleweather.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyDTO(
    @SerialName("dt")
    val time: Int,
    @SerialName("sunrise")
    val sunrise: Int,
    @SerialName("sunset")
    val sunset: Int,
    @SerialName("moonrise")
    val moonrise: Int,
    @SerialName("moonset")
    val moonset: Int,
    @SerialName("moon_phase")
    val moonPhase: Double,
    @SerialName("temp")
    val temp: TempDTO,
    @SerialName("feels_like")
    val feelsLike: FeelsLikeDTO,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double,
    @SerialName("weather")
    val weather: List<WeatherDTO>,
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("pop")
    val precipProbability: Double,
    @SerialName("rain")
    val rain: Double? = null,
    @SerialName("uvi")
    val uvi: Double
)