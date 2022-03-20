package com.example.android.simpleweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.simpleweather.data.model.HourlyDTO
import com.example.android.simpleweather.data.model.WeatherResponse
import com.example.android.simpleweather.databinding.ItemTodayHourlyBinding
import com.example.android.simpleweather.persistence.model.Hourly
import com.example.android.simpleweather.utils.WeatherIconType
import com.example.android.simpleweather.utils.WindIconType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class TodayHourlyAdapter(private val hourly: List<Hourly>): RecyclerView.Adapter<TodayHourlyAdapter.ViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTodayHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = hourly.count()


    override fun onBindViewHolder(viewHolder: TodayHourlyAdapter.ViewHolder, position: Int) {
        hourly[position].let {
            viewHolder.render(it)
        }
    }

    inner class ViewHolder(private val mBinding: ItemTodayHourlyBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun render(hourly: Hourly) {
            mBinding.hourlyTime.text = hourly.time
            mBinding.hourlyTemperature.text = (hourly.temp
                .toString() + "°")
            mBinding.hourlyWind.text = (hourly.windSpeed.roundToInt().toString() + " mph")
            mBinding.hourlyWindIcon.setImageResource(WindIconType.from(hourly.windDeg).windIconID)
            mBinding.hourlyConditionIcon.setImageResource(WeatherIconType.from(hourly.weatherIcon).iconID)
        }

    }
}