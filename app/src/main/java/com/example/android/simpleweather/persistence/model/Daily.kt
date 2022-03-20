package com.example.android.simpleweather.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.android.simpleweather.data.model.DailyDTO
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.*

@Entity(tableName = "daily", indices = [Index(value = ["day"], unique = false)])
data class Daily(

    @PrimaryKey
    @ColumnInfo(name = "day")
    val day: Int,
    @ColumnInfo(name = "dt")
    val time: String,
    @ColumnInfo(name = "sunrise")
    val sunrise: String,
    @ColumnInfo(name = "solarNoon")
    val solarNoon: String,
    @ColumnInfo(name = "sunset")
    val sunset: String,
    @ColumnInfo(name = "moonrise")
    val moonrise: String,
    @ColumnInfo(name = "moonset")
    val moonset: String,
    @ColumnInfo(name = "moon_phase")
    val moonPhase: Double,
    @ColumnInfo(name = "temp_day")
    val tempDay: Int,
    @ColumnInfo(name = "temp_min")
    val tempMin: Int,
    @ColumnInfo(name = "temp_max")
    val tempMax: Int,
    @ColumnInfo(name = "temp_night")
    val tempNight: Int,
    @ColumnInfo(name = "temp_eve")
    val tempEve: Int,
    @ColumnInfo(name = "temp_morn")
    val tempMorn: Int,
    @ColumnInfo(name = "feels_like_day")
    val feelsLikeDay: Int,
    @ColumnInfo(name = "feels_like_night")
    val feelsLikeNight: Int,
    @ColumnInfo(name = "feels_like_eve")
    val feelsLikeEve: Int,
    @ColumnInfo(name = "feels_like_morn")
    val feelsLikeMorn: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "dew_point")
    val dewPoint: Double,
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
    @ColumnInfo(name = "clouds")
    val clouds: Int,
    @ColumnInfo(name = "pop")
    val precipProbability: Double,
    @ColumnInfo(name = "rain")
    val rain: Double? = null,
    @ColumnInfo(name = "uvi")
    val uvi: Double
) {
    companion object {
        fun createToday(daily: DailyDTO, day: Int): Daily {
            return Daily(
                day,
                unixDate(daily.time ),
                unixTime(daily.sunrise),
                unixTime((daily.sunrise + daily.sunset)/2),
                unixTime(daily.sunset),
                unixTime(daily.moonrise),
                unixTime(daily.moonset),
                daily.moonPhase,
                daily.temp.day.toInt(),
                daily.temp.min.toInt(),
                daily.temp.max.toInt(),
                daily.temp.night.toInt(),
                daily.temp.eve.toInt(),
                daily.temp.morn.toInt(),
                daily.feelsLike.day.toInt(),
                daily.feelsLike.night.toInt(),
                daily.feelsLike.eve.toInt(),
                daily.feelsLike.morn.toInt(),
                daily.pressure,
                daily.humidity,
                daily.dewPoint,
                daily.windSpeed,
                daily.windDeg,
                daily.windGust,
                daily.weather[0].id,
                daily.weather[0].main,
                daily.weather[0].description,
                daily.weather[0].icon,
                daily.clouds,
                daily.precipProbability,
                daily.rain,
                daily.uvi
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
