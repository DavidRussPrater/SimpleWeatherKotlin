package com.example.android.simpleweather.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.android.simpleweather.data.model.HourlyDTO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Entity(tableName = "hourly", foreignKeys = [ForeignKey(entity = Daily::class,
    parentColumns = arrayOf("day"),
    childColumns = arrayOf("day"),
    onDelete = ForeignKey.CASCADE)])
data class Hourly(

    @PrimaryKey(autoGenerate = true)
    val key: Int,
    @ColumnInfo(name = "day")
    val day: Int,
    @ColumnInfo(name = "dt")
    val time: String,
    @ColumnInfo(name = "temp")
    val temp: Int,
    @ColumnInfo(name = "feels_like")
    val feelsLike: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "dew_point")
    val dewPoint: Double,
    @ColumnInfo(name = "uvi")
    val uvi: Double,
    @ColumnInfo(name = "clouds")
    val clouds: Int,
    @ColumnInfo(name = "visibility")
    val visibility: Int,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,
    @ColumnInfo(name = "wind_deg")
    val windDeg: Int,
    @ColumnInfo(name = "wind_gust")
    val windGust: Double,
    @ColumnInfo(name = "weather_id")
    val weatherId: Int,
    @ColumnInfo(name = "weather_main")
    val weatherMain: String,
    @ColumnInfo(name = "weather_description")
    val weatherDescription: String,
    @ColumnInfo(name = "weather_icon")
    val weatherIcon: String,
    @ColumnInfo(name = "pop")
    val precipProbability: Double
) {
    companion object {
        fun createToday(hourly: HourlyDTO, day: Int): Hourly {
            return Hourly(
                0,
                day,
                unixTime(hourly.time),
                hourly.temp.roundToInt(),
                hourly.feelsLike.toInt(),
                hourly.pressure,
                hourly.humidity,
                hourly.dewPoint,
                hourly.uvi,
                hourly.clouds,
                hourly.visibility,
                hourly.windSpeed,
                hourly.windDeg,
                hourly.windGust,
                hourly.weather[0].id,
                hourly.weather[0].main,
                hourly.weather[0].description,
                hourly.weather[0].icon,
                hourly.precipProbability
            )
        }
        private fun unixTime(timex: Int) : String {
            val date = Date(timex.times(1000L))
            val sdf = SimpleDateFormat("h a", Locale.US)
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(date)
        }
    }
}
