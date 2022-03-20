package com.example.android.simpleweather.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.simpleweather.persistence.model.Current
import com.example.android.simpleweather.persistence.model.CurrentAndToday
import com.example.android.simpleweather.persistence.model.Daily
import com.example.android.simpleweather.persistence.model.Hourly
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrent(current: Current)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(daily: Daily)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourly(hourly: Hourly)

    @Query("SELECT * FROM daily WHERE day = 0")
    fun getCurrentDetails(): Flow<CurrentAndToday>

    @Query("SELECT * FROM daily WHERE `day` = 0")
    fun getToday(): Flow<Daily>

    @Query("SELECT * FROM daily WHERE `day` = 1")
    fun getTomorrow(): Flow<Daily>

    @Query("SELECT * FROM daily")
    fun getSevenDay(): Flow<List<Daily>>

    @Query("SELECT * FROM hourly")
    fun getTodayHourly(): Flow<List<Hourly>>

    @Query("SELECT * FROM hourly WHERE `day` = 1")
    fun getTomorrowHourly(): Flow<List<Hourly>>

}