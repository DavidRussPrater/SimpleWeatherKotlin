package com.example.android.simpleweather.ui.main.today

import androidx.lifecycle.*
import com.example.android.simpleweather.data.WeatherRepository
import com.example.android.simpleweather.data.model.WeatherResponse
import com.example.android.simpleweather.persistence.model.Current
import com.example.android.simpleweather.persistence.model.CurrentAndToday
import com.example.android.simpleweather.persistence.model.Daily
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    fun insertCurrent(lat: Double, long: Double) {
        try {
            viewModelScope.launch {
                weatherRepository.insertWeatherData(lat, long)
            }
        } catch (e: Exception) {
            Timber.i("Error inserting weather: $e")
        }
    }

    val currentWeather: LiveData<CurrentAndToday> = liveData(Dispatchers.IO) {
        try {
            val result = weatherRepository.getCurrentWeather().asLiveData()
            emitSource(result)
        } catch (e: Exception) {
            Timber.d("Network exception $e")
        }
    }
}