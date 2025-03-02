package com.shabelnikd.weatherapp.adapters


import androidx.recyclerview.widget.DiffUtil
import com.shabelnikd.weatherapp.models.FiveDaysWeatherResponse
import com.shabelnikd.weatherapp.models.TwelveHourWeatherResponse


class GenericDiffUtil<T: Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when {
            oldItem is TwelveHourWeatherResponse && newItem is TwelveHourWeatherResponse -> oldItem.epochDateTime == newItem.epochDateTime
            oldItem is FiveDaysWeatherResponse.DailyForecast && newItem is FiveDaysWeatherResponse.DailyForecast -> oldItem.epochDate == newItem.epochDate
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return when {
            oldItem is TwelveHourWeatherResponse && newItem is TwelveHourWeatherResponse -> oldItem == newItem
            oldItem is FiveDaysWeatherResponse.DailyForecast && newItem is FiveDaysWeatherResponse.DailyForecast -> oldItem == newItem
            else -> false
        }
    }
}