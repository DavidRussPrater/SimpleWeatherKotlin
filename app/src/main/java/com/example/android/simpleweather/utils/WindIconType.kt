package com.example.android.simpleweather.utils

import com.example.android.simpleweather.R

enum class WindIconType {
    DEFAULT, NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH,
    SOUTH_WEST, WEST, NORTH_WEST
    ;

    val windIconID get() = when(this){
        DEFAULT -> R.drawable.ic_wi_direction_up
        NORTH -> R.drawable.ic_wi_direction_up
        NORTH_EAST -> R.drawable.ic_wi_direction_up_right
        EAST -> R.drawable.ic_wi_direction_right
        SOUTH_EAST -> R.drawable.ic_wi_direction_down_right
        SOUTH -> R.drawable.ic_wi_direction_down
        SOUTH_WEST -> R.drawable.ic_wi_direction_down_left
        WEST -> R.drawable.ic_wi_direction_left
        NORTH_WEST-> R.drawable.ic_wi_direction_up_left

    }

    companion object {
        fun from(windDegree: Int?) = when{
            windDegree in 0..30 -> NORTH
            windDegree in 31..60 -> NORTH_EAST
            windDegree in 61..120 -> EAST
            windDegree in 121..165 -> SOUTH_EAST
            windDegree in 166..210 -> SOUTH
            windDegree in 211..255 -> SOUTH_WEST
            windDegree in 256..300 -> WEST
            windDegree in 301..345 -> NORTH_WEST
            windDegree in 346..359 -> NORTH
            else -> DEFAULT
        }
    }


}