package com.example.android.simpleweather.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.simpleweather.adapters.TodayHourlyAdapter
import com.example.android.simpleweather.databinding.FragmentTodaysForecastBinding
import com.example.android.simpleweather.models.WeatherResponse
import com.example.android.simpleweather.utils.Conversions
import com.example.android.simpleweather.utils.WeatherIconType
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [TodaysForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodaysForecastFragment : Fragment() {

    private lateinit var mBinding: FragmentTodaysForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTodaysForecastBinding.inflate(inflater).apply {
            mBinding = this
        }.root

        //TODO (2) call setupUI on swipe refresh listener

    companion object {

        @JvmStatic
        fun newInstance() =
            TodaysForecastFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun render(weatherList: WeatherResponse){
        mBinding.detailTodayPrimary.currentTemperature.text = weatherList.current?.temp.toString()
        mBinding.detailTodayPrimary.conditionIcon.setImageResource(WeatherIconType.from(weatherList.current?.weather?.first()?.icon).iconID)

        mBinding.detailTodayPrimary.currentCondition.text = weatherList.current?.weather?.first()?.description?.capitalizeWords()

        mBinding.detailTodayPrimary.currentTime.text = unixTime(weatherList.current?.dt).toString()
        mBinding.detailTodayPrimary.date.text = unixDate(weatherList.current?.dt).toString()
        mBinding.detailTodayPrimary.temperatureHigh.text = weatherList.daily?.first()?.temp?.max.toString()
        mBinding.detailTodayPrimary.temperatureLow.text = weatherList.daily?.first()?.temp?.min.toString()

        mBinding.detailTodaySecondary.humidity.text = (weatherList.current?.humidity.toString() + "%")
        mBinding.detailTodaySecondary.dewPoint.text = weatherList.current?.dewPoint.toString()
        mBinding.detailTodaySecondary.pressure.text = weatherList.current?.pressure.toString()
        mBinding.detailTodaySecondary.cloudCover.text = (weatherList.current?.clouds.toString() + "%")
        mBinding.detailTodaySecondary.uv.text = weatherList.current?.uvi.toString()
        mBinding.detailTodaySecondary.visibility.text = visibiltyConverter(weatherList.current?.visibility).toString()

        mBinding.detailTodaySun.sunrise.text = unixTime(weatherList.daily?.first()?.sunrise).toString()
        mBinding.detailTodaySun.sunset.text = unixTime(weatherList.daily?.first()?.sunset).toString()


        with(mBinding.todayHourlyForecast.hourlyRecyclerView){
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = TodayHourlyAdapter(weatherList)
        }
    }


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

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")


    private fun visibiltyConverter(visibility: Double?): Double? {
        return (visibility?.times(0.000621371))
    }

}