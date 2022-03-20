package com.example.android.simpleweather.persistence.model

import androidx.room.Embedded
import androidx.room.Relation

data class CurrentAndToday(
    @Embedded
    val daily: Daily,
    @Relation(
        parentColumn = "day",
        entityColumn = "day"
    )
    val current: Current?,
    @Relation(
        parentColumn = "day",
        entityColumn = "day"
    )
    val hourly: List<Hourly>
)
