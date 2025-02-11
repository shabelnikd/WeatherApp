package com.shabelnikd.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shabelnikd.weatherapp.databinding.HourWeatherItemBinding
import com.shabelnikd.weatherapp.models.TwelveHourWeatherResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.shabelnikd.weatherapp.R

class HourItemAdapter(
) : ListAdapter<TwelveHourWeatherResponse, HourItemAdapter.ViewHolder>(GenericDiffUtil<TwelveHourWeatherResponse>()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = HourWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val weatherHour = getItem(position)

        with(holder) {
            binding.tvHourTemp.text = weatherHour.temperature?.value.toString()
            weatherHour.epochDateTime?.let {
                binding.tvHourTime.text = formatTimestampToTime(weatherHour.epochDateTime)
            }
            binding.tvHourIcon.setImageResource(R.drawable.partly_cloudy_day)
        }

    }

    fun formatTimestampToTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("HH.mm", Locale.getDefault())
        return format.format(date)
    }

    class ViewHolder(val binding: HourWeatherItemBinding) : RecyclerView.ViewHolder(binding.root)

}