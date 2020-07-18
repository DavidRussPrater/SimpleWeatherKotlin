package com.example.android.simpleweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.simpleweather.R
import com.example.android.simpleweather.adapters.SevenDayAdapter
import com.example.android.simpleweather.databinding.FragmentSevenDayForecastBinding
import com.example.android.simpleweather.models.WeatherResponse
import com.example.android.simpleweather.utils.WeatherIconType
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [SevenDayForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SevenDayForecastFragment : Fragment() {

    private lateinit var mBinding: FragmentSevenDayForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSevenDayForecastBinding.inflate(inflater).apply {
        mBinding = this
    }.root

    companion object {

        @JvmStatic
        fun newInstance() =
            SevenDayForecastFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun render(weatherList: WeatherResponse){

        with(mBinding.sevenDayForecast.sevenDayRecyclerView){
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            adapter = SevenDayAdapter(weatherList)
        }
    }


}