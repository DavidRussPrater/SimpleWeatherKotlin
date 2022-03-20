package com.example.android.simpleweather.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    val main: String,
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)