package com.example.android.simpleweather.data

import android.content.Context
import com.example.android.simpleweather.data.api.ServiceGenerator
import com.example.android.simpleweather.data.api.WeatherService
import com.example.android.simpleweather.data.model.DailyDTO
import com.example.android.simpleweather.data.model.HourlyDTO
import com.example.android.simpleweather.data.model.WeatherResponse
import com.example.android.simpleweather.persistence.WeatherDao
import com.example.android.simpleweather.persistence.model.Current
import com.example.android.simpleweather.persistence.model.Daily
import com.example.android.simpleweather.persistence.model.Hourly
import com.example.android.simpleweather.utils.Constants
import com.example.android.simpleweather.utils.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    @ApplicationContext private val appContext: Context,
) {

    private var serviceGenerator = ServiceGenerator()
    private val service = serviceGenerator.createService(WeatherService::class.java)

    fun getCurrentWeather() = weatherDao.getCurrentDetails()

    suspend fun insertWeatherData(lat: Double, long: Double) {
        try {
            val weatherResponse = service.getWeather(
                lat,
                long,
                "imperial",
                Constants.EXCLUDE_MINUTELY,
                Constants.APP_ID
            )
            insertDailyList(weatherResponse.daily)
            insertHourlyList(weatherResponse.hourly)
            insertCurrent(weatherResponse)
        } catch (e: Exception) {
            Timber.i("Error inserting today: $e")
        }
    }

    private suspend fun insertCurrent(weatherResponse: WeatherResponse) {
            weatherDao.insertCurrent(Current.createToday(weatherResponse))
    }

    private suspend fun insertDailyList(daily: List<DailyDTO>) {
        daily.forEachIndexed { index, dailyDTO ->
            weatherDao.insertDaily(Daily.createToday(dailyDTO, index))
        }
    }

    private suspend fun insertHourlyList(hourly: List<HourlyDTO>) {
        Timber.i("End of Day ${getEndOfDayEpochSecond()}")
        hourly.forEachIndexed { index, hourlyDTO ->
            Timber.i("Weather Time: ${hourlyDTO.time}")
            if (hourlyDTO.time < getEndOfDayEpochSecond()) {
                weatherDao.insertHourly(Hourly.createToday(hourlyDTO, 0))
            } else {
                weatherDao.insertHourly(Hourly.createToday(hourlyDTO, 1))
            }
        }
    }

    private fun getStartOfDayEpochSecond(): Long {
        val secondInaDay = (60 * 60 * 24).toLong()
        val currentMilliSecond = System.currentTimeMillis() / 1000
        return currentMilliSecond - currentMilliSecond % secondInaDay
    }

    private fun getEndOfDayEpochSecond(): Long {
        val startOfTheDayEpoch: Long = getStartOfDayEpochSecond()
        val secondInaDay = (60 * 60 * 24).toLong()
        return startOfTheDayEpoch + secondInaDay - 1
    }
}