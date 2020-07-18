package com.example.android.simpleweather.utils

import com.example.android.simpleweather.R

enum class WeatherIconType {
    DEFAULT, CLEAR_SKY, FEW_CLOUDS, SCATTER_CLOUDS, BROKEN_CLOUDS, SHOWER_RAIN,
    RAIN, THUNDERSTORM, SNOW, MIST, NIGHT_CLEAR_SKY, NIGHT_FEW_CLOUDS, NIGHT_SCATTER_CLOUDS, NIGHT_BROKEN_CLOUDS, NIGHT_SHOWER_RAIN,
    NIGHT_RAIN, NIGHT_THUNDERSTORM, NIGHT_SNOW, NIGHT_MIST
    ;

    val iconID get() = when(this){
        DEFAULT -> R.drawable.wi_alien
        CLEAR_SKY -> R.drawable.wi_day_sunny
        FEW_CLOUDS -> R.drawable.wi_day_cloudy
        SCATTER_CLOUDS -> R.drawable.wi_cloudy
        BROKEN_CLOUDS -> R.drawable.wi_day_cloudy_windy
        SHOWER_RAIN -> R.drawable.wi_day_showers
        RAIN -> R.drawable.wi_day_rain
        THUNDERSTORM -> R.drawable.wi_day_thunderstorm
        SNOW-> R.drawable.wi_day_snow
        MIST -> R.drawable.wi_day_haze
        NIGHT_CLEAR_SKY -> R.drawable.wi_night_clear
        NIGHT_FEW_CLOUDS -> R.drawable.wi_night_cloudy
        NIGHT_SCATTER_CLOUDS -> R.drawable.wi_cloudy
        NIGHT_BROKEN_CLOUDS -> R.drawable.wi_night_alt_cloudy_windy
        NIGHT_SHOWER_RAIN -> R.drawable.wi_night_showers
        NIGHT_RAIN -> R.drawable.wi_night_rain
        NIGHT_THUNDERSTORM -> R.drawable.wi_night_snow_thunderstorm
        NIGHT_SNOW -> R.drawable.wi_night_snow
        NIGHT_MIST -> R.drawable.wi_night_fog
    }

    companion object {
        fun from(iconID: String? ) = when(iconID){
            "01d" -> CLEAR_SKY
            "02d" -> FEW_CLOUDS
            "03d" -> SCATTER_CLOUDS
            "04d" -> BROKEN_CLOUDS
            "09d" -> SHOWER_RAIN
            "010d" -> RAIN
            "011d" -> THUNDERSTORM
            "013d" -> SNOW
            "50d" -> MIST
            "01n" -> NIGHT_CLEAR_SKY
            "02n" -> NIGHT_FEW_CLOUDS
            "03n" -> NIGHT_SCATTER_CLOUDS
            "04n" -> NIGHT_BROKEN_CLOUDS
            "09n" -> NIGHT_SHOWER_RAIN
            "010n" -> NIGHT_RAIN
            "011n" -> NIGHT_THUNDERSTORM
            "013n" -> NIGHT_SNOW
            "50n" -> NIGHT_MIST
            else -> DEFAULT
        }
    }

}