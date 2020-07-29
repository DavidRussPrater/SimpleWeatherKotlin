package com.example.android.simpleweather.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FeelsLike (
    val day: Double?,
    val eve: Double?,
    val morn: Double?,
    val night: Double?
): Serializable