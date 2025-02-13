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
            weatherHour.epochDateTime?.let { epochDateTime ->
                when {
                    weatherHour.isSelected -> binding.tvHourTime.text = "Сейчас"
                    else -> binding.tvHourTime.text = formatTimestampToTime(epochDateTime)
                }
            }
            binding.tvHourIcon.setImageResource(imageResByPhrase(weatherHour.iconPhrase, weatherHour.isDaylight))
        }

    }

    fun formatTimestampToTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("HH.mm", Locale.getDefault())
        return format.format(date)
    }


    fun imageResByPhrase(phrase: String?, isDaylight: Boolean?): Int{
        if (isDaylight == true) {
            return when {
                phrase?.contains("ясно") == true -> R.drawable.clear_day
                phrase?.contains("Преимущественно") == true -> R.drawable.partly_cloudy_day
                phrase?.contains("облачно") == true -> R.drawable.cloudy
                else -> R.drawable.clear_day
            }

        } else {
            return when {
                phrase?.contains("ясно") == true -> R.drawable.clear_night
                phrase?.contains("Преимущественно") == true -> R.drawable.partly_cloudy_night
                phrase?.contains("облачно") == true -> R.drawable.cloudy
                else -> R.drawable.clear_night
            }

        }

    }

    class ViewHolder(val binding: HourWeatherItemBinding) : RecyclerView.ViewHolder(binding.root)

}