package com.example.android.simpleweather.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeelsLikeDTO(
    @SerialName("day")
    val day: Double,
    @SerialName("night")
    val night: Double,
    @SerialName("eve")
    val eve: Double,
    @SerialName("morn")
    val morn: Double
)