package com.example.android.simpleweather.utils

import android.content.Context
import android.content.SharedPreferences
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class Settings {
    val unitChoice = "UnitChoice"
    val timeZoneOffset = "TimeZoneOffSet"

    @Retention(RetentionPolicy.SOURCE)
    internal annotation class SettingDef

    private fun Settings() {}

    private var sharedPrefs: SharedPreferences? = null
    private var sharedPrefsEditor: SharedPreferences.Editor? = null

    private val SHARED_PREFS_NAME = "SHARED_PREFS"

    fun Initialize(context: Context) {
        if (sharedPrefs == null)  {
            sharedPrefs = context.getSharedPreferences(
                SHARED_PREFS_NAME, Context.MODE_PRIVATE
            )
        }

        if (sharedPrefsEditor == null) {
            sharedPrefsEditor = sharedPrefs?.edit()
        }
    }

    fun getSetting(key: String): String? {
        return sharedPrefs?.getString(key, "")
    }

    fun setSetting(key: String, value: String) {
        sharedPrefsEditor?.putString(key, value)
        sharedPrefsEditor?.apply()
    }

    fun removeSetting(key: String) {
        sharedPrefsEditor?.remove(key)
        sharedPrefsEditor?.apply()
    }

    /**
     * Get the stored driver id
     */
    fun getUnitPreference(): String? {
        return sharedPrefs?.getString(
            unitChoice,
            Constants.IMPERIAL_UNIT
        )
    }

    /**
     * Set the driver id
     */
    fun setUnitPreference(unit: String) {
        sharedPrefsEditor?.putString(unitChoice, unit)
        sharedPrefsEditor?.apply()
    }

    /**
     * Get the stored driver id
     */
    fun getTimeZoneOffSet(): Int {
        return sharedPrefs!!.getInt(timeZoneOffset, 0)
    }

    /**
     * Set the driver id
     */
    fun setTimeZoneOffSet(time: Int) {
        sharedPrefsEditor!!.putInt(timeZoneOffset, time)
        sharedPrefsEditor!!.apply()
    }

}