package com.example.android.simpleweather.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.simpleweather.databinding.ItemTodayHourlyBinding
import com.example.android.simpleweather.models.Hourly
import com.example.android.simpleweather.models.WeatherResponse
import com.example.android.simpleweather.utils.WeatherIconType
import org.jetbrains.annotations.NotNull
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class TodayHourlyAdapter(private val weatherResponse: WeatherResponse): RecyclerView.Adapter<TodayHourlyAdapter.ViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTodayHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = weatherResponse.hourly?.count() ?: 0


    override fun onBindViewHolder(viewHolder: TodayHourlyAdapter.ViewHolder, position: Int) {
        weatherResponse.hourly?.get(position)?.let {
            viewHolder.render(it)
        }
    }

    inner class ViewHolder(private val mBinding: ItemTodayHourlyBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun render(hourly: Hourly) {
            mBinding.hourlyTime.text = unixTime(hourly.dt).toString()
            Log.i("Time", unixTime(hourly.dt).toString())
            mBinding.hourlyTemperature.text = (hourly.temp?.roundToInt()
                .toString() + "°")
            mBinding.hourlyWind.text = hourly.windSpeed.toString()
            mBinding.hourlyConditionIcon.setImageResource(WeatherIconType.from(hourly.weather?.first()?.icon).iconID)
        }

    }

    private fun unixTime(timex: Long) : String? {
        val date = Date(timex.times(1000L))
        val sdf = SimpleDateFormat("h a", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

}