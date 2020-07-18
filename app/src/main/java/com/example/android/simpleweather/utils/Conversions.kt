package com.example.android.simpleweather.utils

import java.text.SimpleDateFormat
import java.util.*

object Conversions {
    fun unixTime(timex: Long?) : String? {
        val date = timex?.times(1000L)?.let { Date(it) }
        val sdf = SimpleDateFormat("h:mm a", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    fun unixDate(timex: Long?) : String? {
        val date = timex?.times(1000L)?.let { Date(it) }
        val sdf = SimpleDateFormat("MMMM d, y", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    fun visibiltyConverter(visibility: Double?): Double? {
        return (visibility?.times(0.000621371))
    }
}