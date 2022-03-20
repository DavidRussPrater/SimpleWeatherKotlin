package com.example.android.simpleweather.ui.main.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.simpleweather.adapters.TodayHourlyAdapter
import com.example.android.simpleweather.databinding.FragmentTodaysForecastBinding
import com.example.android.simpleweather.persistence.model.CurrentAndToday
import com.example.android.simpleweather.utils.WeatherIconType
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [TodaysForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TodaysForecastFragment : Fragment() {

    private lateinit var mBinding: FragmentTodaysForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTodaysForecastBinding.inflate(inflater).apply {
            mBinding = this
        }.root

    companion object {

        @JvmStatic
        fun newInstance() =
            TodaysForecastFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun render(weather: CurrentAndToday){

        mBinding.detailTodayPrimary.currentTemperature.text = (weather.current?.temp.toString() + "°")
        mBinding.detailTodayPrimary.conditionIcon.setImageResource(WeatherIconType.from(weather.current?.icon).iconID)

        mBinding.detailTodayPrimary.currentCondition.text = weather.current?.description?.capitalizeWords()

        // TODO convert ms to date
        mBinding.detailTodayPrimary.currentTime.text = weather.current?.time
        mBinding.detailTodayPrimary.date.text = weather.current?.time
        mBinding.detailTodayPrimary.temperatureHigh.text = (weather.current?.temp
            .toString() + "°↑")
        mBinding.detailTodayPrimary.temperatureLow.text = (weather.daily.tempMin
            .toString() + "°↓")

        mBinding.detailTodaySecondary.humidity.text = (weather.current?.humidity.toString() + "%")
        mBinding.detailTodaySecondary.pressure.text = weather.current?.pressure.toString()
        mBinding.detailTodaySecondary.cloudCover.text = (weather.current?.clouds.toString() + "%")
        mBinding.detailTodaySecondary.uv.text = weather.current?.uvi.toString()
        mBinding.detailTodaySecondary.visibility.text = weather.current?.visibility?.let {
            visibilityConverter(
                it
            )
        }

        mBinding.detailTodaySun.sunrise.text = weather.daily.sunrise
        mBinding.detailTodaySun.solarNoon.text = weather.daily.solarNoon
        mBinding.detailTodaySun.sunset.text = weather.daily.sunset


        with(mBinding.todayHourlyForecast.hourlyRecyclerView){
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = TodayHourlyAdapter(weather.hourly)
        }
    }




    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")


    private fun visibilityConverter(visibility: Double): String {
        return ("%.2f".format(visibility.times(0.000621371)))
    }

}