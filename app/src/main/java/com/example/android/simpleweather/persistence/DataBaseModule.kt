package com.example.android.simpleweather.persistence

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideTodayDao(appDatabase: AbstractAppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AbstractAppDatabase {
        return Room.databaseBuilder(
            appContext,
            AbstractAppDatabase::class.java,
            "simple_weather"
        ).build()
    }
}