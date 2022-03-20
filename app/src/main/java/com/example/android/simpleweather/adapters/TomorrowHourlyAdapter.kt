package com.example.android.simpleweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.simpleweather.data.model.HourlyDTO
import com.example.android.simpleweather.data.model.WeatherResponse
import com.example.android.simpleweather.databinding.ItemTomorrowHourlyBinding
import com.example.android.simpleweather.persistence.model.Hourly
import com.example.android.simpleweather.utils.WeatherIconType
import com.example.android.simpleweather.utils.WindIconType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class TomorrowHourlyAdapter(private val hourly: List<Hourly>): RecyclerView.Adapter<TomorrowHourlyAdapter.ViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTomorrowHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = hourly.count() ?: 0

    override fun onBindViewHolder(viewHolder: TomorrowHourlyAdapter.ViewHolder, position: Int) {
        hourly[position].let {
            viewHolder.render(it)
        }
    }

    inner class ViewHolder(private val mBinding: ItemTomorrowHourlyBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun render(hourly: Hourly) {
            mBinding.tomorrowsHourlyTime.text = hourly.time
            mBinding.tomorrowsHourlyTemperature.text = (hourly.temp
                .toString() + "°")
            mBinding.tomorrowsHourlyWind.text = hourly.windSpeed.roundToInt().toString()
            mBinding.tomorrowsHourlyWindIcon.setImageResource(WindIconType.from(hourly.windDeg).windIconID)
            mBinding.tomorrowsHourlyConditionIcon.setImageResource(WeatherIconType.from(hourly.weatherIcon).iconID)
        }

    }

}