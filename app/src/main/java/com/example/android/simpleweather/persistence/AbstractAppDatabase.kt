package com.example.android.simpleweather.persistence


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.simpleweather.persistence.model.Current
import com.example.android.simpleweather.persistence.model.Daily
import com.example.android.simpleweather.persistence.model.Hourly

@Database(entities = [Current::class, Daily::class, Hourly::class], version = 1)
abstract class AbstractAppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}