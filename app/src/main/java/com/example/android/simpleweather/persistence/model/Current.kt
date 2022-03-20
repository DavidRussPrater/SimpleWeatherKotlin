package com.example.android.simpleweather.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.android.simpleweather.data.model.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "current", foreignKeys = [ForeignKey(entity = Daily::class,
    parentColumns = arrayOf("day"),
    childColumns = arrayOf("day"),
    onDelete = ForeignKey.CASCADE)])
data class Current(

    @PrimaryKey
    val key: Int,
    @ColumnInfo(name = "day")
    val day: Int,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "temp")
    val temp: Int,
    @ColumnInfo(name = "feels_like")
    val feelsLike: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Double,
    @ColumnInfo(name = "humidity")
    val humidity: Double,
    @ColumnInfo(name = "dew_point")
    val dewPoint: Double,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,
    @ColumnInfo(name = "wind_deg")
    val windDeg: Double,
    @ColumnInfo(name = "weather_id")
    val id: Int,
    @ColumnInfo(name = "weather_main")
    val main: String,
    @ColumnInfo(name = "weather_description")
    val description: String,
    @ColumnInfo(name = "weather_icon")
    val icon: String,
    @ColumnInfo(name = "clouds")
    val clouds: Double,
    @ColumnInfo(name = "visibility")
    val visibility: Double,
    @ColumnInfo(name = "uvi")
    val uvi: Double
) {
    companion object {
        fun createToday(weatherResponse: WeatherResponse): Current {
            return Current(
                0,
                0,
                unixDate(weatherResponse.current.currentTime),
                weatherResponse.current.temp.toInt(),
                weatherResponse.current.feelsLike.toInt(),
                weatherResponse.current.pressure,
                weatherResponse.current.humidity,
                weatherResponse.current.dewPoint,
                weatherResponse.current.windSpeed,
                weatherResponse.current.windDeg,
                weatherResponse.current.weather[0].id,
                weatherResponse.current.weather[0].main,
                weatherResponse.current.weather[0].description,
                weatherResponse.current.weather[0].icon,
                weatherResponse.current.clouds,
                weatherResponse.current.visibility,
                weatherResponse.current.uvi
            )
        }

        private fun unixTime(timex: Int) : String {
            val date = Date(timex.times(1000L))
            val sdf = SimpleDateFormat("h:mm a", Locale.US)
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(date)
        }

        private fun unixDate(timex: Int) : String {
            val date = Date(timex.times(1000L))
            val sdf = SimpleDateFormat("MMMM d, y", Locale.US)
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(date)
        }
    }
}
