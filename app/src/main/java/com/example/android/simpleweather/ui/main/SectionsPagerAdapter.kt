package com.example.android.simpleweather.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.android.simpleweather.R
import com.example.android.simpleweather.ui.main.sevenday.SevenDayForecastFragment
import com.example.android.simpleweather.ui.main.today.TodaysForecastFragment
import com.example.android.simpleweather.ui.main.tomorrow.TomorrowsForecastFragment

private val TAB_TITLES = arrayOf(
        R.string.tab_text_today,
        R.string.tab_text_tomorrow,
        R.string.tab_text_seven_days
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1)
        when(position){
            0 -> (return TodaysForecastFragment())
            1 -> (return TomorrowsForecastFragment())
            else -> {(return SevenDayForecastFragment())}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}