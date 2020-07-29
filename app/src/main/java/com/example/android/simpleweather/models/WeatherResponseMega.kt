package com.example.android.simpleweather.models


import com.google.gson.annotations.SerializedName

data class WeatherResponseMega(
    @SerializedName("current")
    val current: Current?,
    @SerializedName("daily")
    val daily: List<Daily?>?,
    @SerializedName("hourly")
    val hourly: List<Hourly?>?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int?
) {
    data class Current(
        @SerializedName("clouds")
        val clouds: Int?,
        @SerializedName("dew_point")
        val dewPoint: Double?,
        @SerializedName("dt")
        val dt: Int?,
        @SerializedName("feels_like")
        val feelsLike: Double?,
        @SerializedName("humidity")
        val humidity: Int?,
        @SerializedName("pressure")
        val pressure: Int?,
        @SerializedName("sunrise")
        val sunrise: Int?,
        @SerializedName("sunset")
        val sunset: Int?,
        @SerializedName("temp")
        val temp: Double?,
        @SerializedName("uvi")
        val uvi: Double?,
        @SerializedName("visibility")
        val visibility: Int?,
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("wind_deg")
        val windDeg: Int?,
        @SerializedName("wind_speed")
        val windSpeed: Double?
    ) {
        data class Weather(
            @SerializedName("description")
            val description: String?,
            @SerializedName("icon")
            val icon: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("main")
            val main: String?
        )
    }

    data class Daily(
        @SerializedName("clouds")
        val clouds: Int?,
        @SerializedName("dew_point")
        val dewPoint: Double?,
        @SerializedName("dt")
        val dt: Int?,
        @SerializedName("feels_like")
        val feelsLike: FeelsLike?,
        @SerializedName("humidity")
        val humidity: Int?,
        @SerializedName("pop")
        val pop: Double?,
        @SerializedName("pressure")
        val pressure: Int?,
        @SerializedName("rain")
        val rain: Double?,
        @SerializedName("sunrise")
        val sunrise: Int?,
        @SerializedName("sunset")
        val sunset: Int?,
        @SerializedName("temp")
        val temp: Temp?,
        @SerializedName("uvi")
        val uvi: Double?,
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("wind_deg")
        val windDeg: Int?,
        @SerializedName("wind_speed")
        val windSpeed: Double?
    ) {
        data class FeelsLike(
            @SerializedName("day")
            val day: Double?,
            @SerializedName("eve")
            val eve: Double?,
            @SerializedName("morn")
            val morn: Double?,
            @SerializedName("night")
            val night: Double?
        )

        data class Temp(
            @SerializedName("day")
            val day: Double?,
            @SerializedName("eve")
            val eve: Double?,
            @SerializedName("max")
            val max: Double?,
            @SerializedName("min")
            val min: Double?,
            @SerializedName("morn")
            val morn: Double?,
            @SerializedName("night")
            val night: Double?
        )

        data class Weather(
            @SerializedName("description")
            val description: String?,
            @SerializedName("icon")
            val icon: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("main")
            val main: String?
        )
    }

    data class Hourly(
        @SerializedName("clouds")
        val clouds: Int?,
        @SerializedName("dew_point")
        val dewPoint: Double?,
        @SerializedName("dt")
        val dt: Int?,
        @SerializedName("feels_like")
        val feelsLike: Double?,
        @SerializedName("humidity")
        val humidity: Int?,
        @SerializedName("pop")
        val pop: Double?,
        @SerializedName("pressure")
        val pressure: Int?,
        @SerializedName("rain")
        val rain: Rain?,
        @SerializedName("temp")
        val temp: Double?,
        @SerializedName("visibility")
        val visibility: Int?,
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("wind_deg")
        val windDeg: Int?,
        @SerializedName("wind_speed")
        val windSpeed: Double?
    ) {
        data class Rain(
            @SerializedName("1h")
            val h: Double?
        )

        data class Weather(
            @SerializedName("description")
            val description: String?,
            @SerializedName("icon")
            val icon: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("main")
            val main: String?
        )
    }
}