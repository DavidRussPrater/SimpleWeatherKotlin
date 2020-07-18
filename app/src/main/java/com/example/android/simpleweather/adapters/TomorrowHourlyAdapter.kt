package com.example.android.simpleweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.simpleweather.databinding.ItemTomorrowHourlyBinding
import com.example.android.simpleweather.models.Hourly
import com.example.android.simpleweather.models.WeatherResponse
import com.example.android.simpleweather.utils.WeatherIconType
import java.text.SimpleDateFormat
import java.util.*

class TomorrowHourlyAdapter(private val weatherResponse: WeatherResponse): RecyclerView.Adapter<TomorrowHourlyAdapter.ViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTomorrowHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = weatherResponse.hourly?.count() ?: 0

    override fun onBindViewHolder(viewHolder: TomorrowHourlyAdapter.ViewHolder, position: Int) {
        weatherResponse.hourly?.get(position)?.let {
            viewHolder.render(it)
        }
    }

    inner class ViewHolder(private val mBinding: ItemTomorrowHourlyBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun render(hourly: Hourly) {
            mBinding.tomorrowsHourlyTime.text = unixTime(hourly.dt).toString()
            mBinding.tomorrowsHourlyTemperature.text = hourly.temp.toString()
            mBinding.tomorrowsHourlyWind.text = hourly.windSpeed.toString()
            mBinding.tomorrowsHourlyConditionIcon.setImageResource(WeatherIconType.from(hourly.weather?.first()?.icon).iconID)
        }

    }

    private fun unixTime(timex: Long) : String? {
        val date = Date(timex.times(1000L))
        val sdf = SimpleDateFormat("h a", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

}