package com.example.android.simpleweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.simpleweather.databinding.ItemSevenDayBinding
import com.example.android.simpleweather.models.Daily
import com.example.android.simpleweather.models.WeatherResponse
import com.example.android.simpleweather.utils.WeatherIconType
import java.text.SimpleDateFormat
import java.util.*

class SevenDayAdapter(private val weatherResponse: WeatherResponse): RecyclerView.Adapter<SevenDayAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSevenDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = weatherResponse.daily?.count() ?: 0

    override fun onBindViewHolder(viewHolder: SevenDayAdapter.ViewHolder, position: Int) {
        weatherResponse.daily?.get(position)?.let {
            viewHolder.render(it)
        }
    }

    inner class ViewHolder(private val mBinding: ItemSevenDayBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun render(daily: Daily) {
            mBinding.dailyDate.text = unixDate(daily.dt).toString()
            mBinding.dailyConditionDescription.text = daily.weather?.first()?.description.toString().capitalizeWords()
            mBinding.dailyConditionIcon.setImageResource(WeatherIconType.from(daily.weather?.first()?.icon).iconID)
            mBinding.dailyTemperatureHigh.text = daily.temp?.max.toString()
            mBinding.dailyTemperatureLow.text = daily.temp?.min.toString()

        }

    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

    private fun unixDate(timex: Long?) : String? {
        val date = timex?.times(1000L)?.let { Date(it) }
        val sdf = SimpleDateFormat("MMMM d, y", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

}