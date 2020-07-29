package com.example.android.simpleweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.android.simpleweather.databinding.FragmentTomorrowsForecastBinding
import com.example.android.simpleweather.models.WeatherResponse
import com.example.android.simpleweather.utils.WeatherIconType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 * Use the [TomorrowsForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TomorrowsForecastFragment : Fragment() {

    private lateinit var mBinding: FragmentTomorrowsForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTomorrowsForecastBinding.inflate(inflater).apply {
        mBinding = this
    }.root

    companion object {

        @JvmStatic
        fun newInstance() =
            TomorrowsForecastFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    fun render(weatherList: WeatherResponse){

        mBinding.detailTomorrowPrimary.tomorrowsDate.text =  unixDate(weatherList.daily?.get(1)?.dt).toString()
        mBinding.detailTomorrowPrimary.tomorrowsTemperatureHigh.text = ((weatherList.daily?.get(1)?.temp?.max?.roundToInt())
            .toString() + "°↑")
        mBinding.detailTomorrowPrimary.tomorrowsTemperatureLow.text = (weatherList.daily?.get(1)?.temp?.min?.roundToInt()
            .toString() + "°↓")
        mBinding.detailTomorrowPrimary.tomorrowsCurrentCondition.text = weatherList.daily?.get(1)?.weather?.first()?.description?.capitalizeWords()
        mBinding.detailTomorrowPrimary.tomorrowsConditionIcon.setImageResource(WeatherIconType.from(weatherList.daily?.get(1)?.weather?.first()?.icon).iconID)
        mBinding.detailTomorrowPrimary.tomorrowsPrecipitation.text = ("Precipitation " + weatherList.daily?.get(1)?.rain.toString() + "%")

        mBinding.detailTomorrowSecondary.tomorrowsHumidity.text = (weatherList.daily?.get(1)?.humidity.toString() + "%")
        mBinding.detailTomorrowSecondary.tomorrowsDewPoint.text = weatherList.daily?.get(1)?.dewPoint.toString()
        mBinding.detailTomorrowSecondary.tomorrowsPressure.text = weatherList.daily?.get(1)?.pressure.toString()
        mBinding.detailTomorrowSecondary.tomorrowsCloudCover.text = (weatherList.daily?.get(1)?.clouds.toString() + "%")
        mBinding.detailTomorrowSecondary.tomorrowsUv.text = weatherList.daily?.get(1)?.uvi.toString()
        mBinding.detailTomorrowSecondary.tomorrowsVisibility.text = visibilityConverter(weatherList.current?.visibility).toString()

        mBinding.detailTomorrowSun.tomorrowsSunrise.text = unixTime(weatherList.daily?.get(1)?.sunrise).toString()
        mBinding.detailTomorrowSun.tomorrowsSunset.text = unixTime(weatherList.daily?.get(1)?.sunset).toString()


        with(mBinding.tomorrowsHourlyForecast.tomorrowsHourlyRecyclerView){
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.RecyclerView.HORIZONTAL,
                false
            )
            adapter = com.example.android.simpleweather.adapters.TomorrowHourlyAdapter(weatherList)
        }
    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

    private fun unixTime(timex: Long?) : String? {
        val date = timex?.times(1000L)?.let { Date(it) }
        val sdf = SimpleDateFormat("h:mm a", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun unixDate(timex: Long?) : String? {
        val date = timex?.times(1000L)?.let { Date(it) }
        val sdf = SimpleDateFormat("MMMM d, y", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun getUnit(value: String):String? {
        var value = "°C"
        if ("US" == value || "LR" == value || "MM" == value){
            value = "°F"
        }
        return value
    }

    private fun visibilityConverter(visibility: Double?): String? {
        return ("%.2f".format(visibility?.times(0.000621371)))
    }
}