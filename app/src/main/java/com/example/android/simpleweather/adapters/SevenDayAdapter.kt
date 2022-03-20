package com.example.android.simpleweather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.simpleweather.R
import com.example.android.simpleweather.data.model.DailyDTO
import com.example.android.simpleweather.data.model.WeatherResponse
import com.example.android.simpleweather.databinding.ItemSevenDayBinding
import com.example.android.simpleweather.utils.WeatherIconType
import kotlinx.android.synthetic.main.item_seven_day.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class SevenDayAdapter(private val weatherResponse: WeatherResponse): RecyclerView.Adapter<SevenDayAdapter.ViewHolder>()  {

    var mExpandedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSevenDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = weatherResponse.daily.count()

    override fun onBindViewHolder(viewHolder: SevenDayAdapter.ViewHolder, position: Int) {
        weatherResponse.daily.get(position).let {
            viewHolder.render(it)
        }

        val isExpanded: Boolean = position == mExpandedPosition;
        viewHolder.itemView.seven_day_secondary.visibility =
            if (isExpanded) View.VISIBLE else View.GONE
        viewHolder.itemView.isActivated = isExpanded
        viewHolder.itemView.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(position)
        }

    }

    inner class ViewHolder(private val mBinding: ItemSevenDayBinding) : RecyclerView.ViewHolder(mBinding.root) {

        val secondaryDetails: Int = R.id.seven_day_secondary

        fun render(daily: DailyDTO) {
            mBinding.sevenDayPrimary.dailyDate.text = unixDate(daily.time)
            mBinding.sevenDayPrimary.dailyConditionDescription.text = daily.weather.first().description.toString().capitalizeWords()
            mBinding.sevenDayPrimary.dailyConditionIcon.setImageResource(WeatherIconType.from(daily.weather.first().icon).iconID)
            mBinding.sevenDayPrimary.dailyTemperatureHigh.text = (daily.temp.max.roundToInt()
                .toString() + "°")
            mBinding.sevenDayPrimary.dailyTemperatureLow.text = (daily.temp.min.roundToInt()
                .toString() + "°")

            mBinding.sevenDaySecondary.humidity.text = (daily.humidity.toString() + "%")
            mBinding.sevenDaySecondary.dewPoint.text = daily.dewPoint.toString()
            mBinding.sevenDaySecondary.pressure.text = daily.pressure.toString()
            mBinding.sevenDaySecondary.cloudCover.text = (daily.clouds.toString() + "%")
            mBinding.sevenDaySecondary.uv.text = daily.uvi.roundToInt().toString()

        }

    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

    private fun unixDate(timex: Int) : String {
        val date = timex.times(1000L).let { Date(it) }
        val sdf = SimpleDateFormat("EEEE, MMMM d", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

}