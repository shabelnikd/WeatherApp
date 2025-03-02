package com.shabelnikd.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.shabelnikd.weatherapp.R
import com.shabelnikd.weatherapp.databinding.DayWeatherItemBinding
import com.shabelnikd.weatherapp.models.FiveDaysWeatherResponse

class DayItemAdapter(
) : ListAdapter<FiveDaysWeatherResponse.DailyForecast, DayItemAdapter.ViewHolder>(GenericDiffUtil<FiveDaysWeatherResponse.DailyForecast>()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = DayWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val weatherDay = getItem(position)

        with(holder) {
            weatherDay.epochDate?.let {
                binding.tvWeekDay.text = formatTimestampToDayOfWeek(weatherDay.epochDate).replaceFirstChar { it.uppercase() }
            }

            binding.tvDayIcon.setImageResource(imageResByPhrase(weatherDay.day?.iconPhrase))
            binding.tvDayMaxTemp.text = weatherDay.temperature?.maximum?.value.toString()
            binding.tvDayMinTemp.text = weatherDay.temperature?.minimum?.value.toString()
        }

    }

    fun formatTimestampToDayOfWeek(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("EEEE", Locale("ru"))
        return format.format(date)
    }

    fun imageResByPhrase(phrase: String?): Int{
        return when {
            phrase?.lowercase()?.contains("ясно") == true -> R.drawable.clear_day
            phrase?.lowercase()?.contains("преимущественно") == true -> R.drawable.partly_cloudy_day
            phrase?.lowercase()?.contains("облачно") == true -> R.drawable.cloudy
            phrase?.lowercase()?.contains("cнег") == true -> R.drawable.snow
            else -> R.drawable.partly_cloudy_day
        }

    }

    class ViewHolder(val binding: DayWeatherItemBinding) : RecyclerView.ViewHolder(binding.root)

}