package com.example.android.simpleweather.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather(

    val description: String?,

    val icon: String?,

    val id: Int?,

    val main: String?
): Serializable