package com.example.android.simpleweather.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TempDTO(
    @SerialName("day")
    val day: Double,
    @SerialName("min")
    val min: Double,
    @SerialName("max")
    val max: Double,
    @SerialName("night")
    val night: Double,
    @SerialName("eve")
    val eve: Double,
    @SerialName("morn")
    val morn: Double
)