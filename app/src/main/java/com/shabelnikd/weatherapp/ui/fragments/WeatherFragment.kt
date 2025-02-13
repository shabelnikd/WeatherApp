package com.shabelnikd.weatherapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shabelnikd.weatherapp.R
import com.shabelnikd.weatherapp.adapters.DayItemAdapter
import com.shabelnikd.weatherapp.adapters.HourItemAdapter
import com.shabelnikd.weatherapp.databinding.FragmentWeatherBinding
import com.shabelnikd.weatherapp.models.LocationKeysEnum
import com.shabelnikd.weatherapp.models.TwelveHourWeatherResponse
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val dayAdapter = DayItemAdapter()
    private val hourAdapter = HourItemAdapter()

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        sharedPreferences = context?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        sharedPreferences?.let {
            val currentLocation =
                sharedPreferences?.getInt("currentLocation", LocationKeysEnum.BISHKEK.locationKey)

            val selectedLocation =
                LocationKeysEnum.entries.find { it.locationKey == currentLocation }
            binding.currentCityState.text = selectedLocation?.russianName

            loadData(currentLocation)
            popupInit()
        }
    }

    private fun loadData(locationKey: Int?) {
        locationKey?.let { locationKey ->
            viewModel.getCurrentWeather(locationKey)
            viewModel.getOneDayWeather(locationKey)
            viewModel.getTwelveHoursWeather(locationKey)
            viewModel.getFiveDaysWeather(locationKey)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initialize() {
        binding.rvTodayForecast.adapter = hourAdapter
        binding.rvNextForecast.adapter = dayAdapter

        binding.currentDate.text = getCurrentDateFormatted()

        viewModel.currentWeather.observe(viewLifecycleOwner) { currentWeather ->
            binding.loaderLayout.visibility = View.GONE

            when (currentWeather.first().isDayTime) {
                true -> binding.root.setBackgroundResource(R.drawable.bg_weather_first)
                false -> binding.root.setBackgroundResource(R.drawable.bg_weather_second)
                else -> binding.root.setBackgroundResource(R.drawable.bg_weather_second)
            }

            currentWeather.first().apply {
                binding.tvCurrentWeatherTemp.text = temperature?.metric?.value.toString()
                binding.isPrecipitations.text = weatherText

                when (hasPrecipitation) {
                    true -> binding.imageIcon.setImageResource(R.drawable.image_cloud_rain)
                    false -> binding.imageIcon.setImageResource(R.drawable.image_sun_cloud)
                    else -> Log.w("ALLD", "not value of hasPrecipitations")
                }
            }
        }

        viewModel.currentDayWeather.observe(viewLifecycleOwner) { currentDayWeather ->
            currentDayWeather.dailyForecasts?.first()?.let { currentDayWeatherItem ->
                currentDayWeatherItem.apply {

                    binding.tvMaxTemp.text = "Макс.: " + temperature?.maximum?.value.toString()
                    binding.tvMinTemp.text = "Мин.: " + temperature?.minimum?.value.toString()
                    binding.tvHumidity.text = day?.relativeHumidity?.average.toString() + "%"

                    if (day?.precipitationProbability != null && night?.precipitationProbability != null) {
                        binding.tvPrecipitationsProbability.text =
                            day.precipitationProbability.plus(night.precipitationProbability)
                                .toString() + "%"
                    }

                    binding.tvWind.text = day?.wind?.speed?.value.toString() + " км/ч"
                }
            }

        }

        viewModel.twelveHoursWeather.observe(viewLifecycleOwner) { twelveHours ->
            fun formatTimestampToTime(timestamp: Long): String {
                val date = Date(timestamp * 1000)
                val format = SimpleDateFormat("HH.mm", Locale.getDefault())
                return format.format(date)
            }


            val currentTime = if ("${LocalDateTime.now().hour}".length == 1) "0${LocalDateTime.now().hour}.00" else "${LocalDateTime.now().hour}.00"
            viewModel.currentWeather.observe(viewLifecycleOwner) {currentWeather ->
                val selectedHour = twelveHours.find { formatTimestampToTime(it.epochDateTime!!) == currentTime }
                    ?: TwelveHourWeatherResponse(
                        epochDateTime = Instant.now().toEpochMilli(),
                        temperature = TwelveHourWeatherResponse.Temperature(value = currentWeather.first().temperature?.metric?.value),
                        isDaylight = viewModel.currentWeather.value?.first()?.isDayTime,
                        iconPhrase = viewModel.currentWeather.value?.first()?.weatherText,
                        isSelfCreated = true
                    )

                selectedHour.isSelected = true

                when (selectedHour.isSelfCreated) {
                    true -> {
                        hourAdapter.submitList(listOf(selectedHour) + twelveHours)
                    }
                    false -> {
                        hourAdapter.submitList(twelveHours)
                    }
                }
            }
        }

        viewModel.fiveDaysWeather.observe(viewLifecycleOwner) { fiveDays ->
            dayAdapter.submitList(fiveDays.dailyForecasts)
        }
    }

    private fun popupInit() {
        binding.currentCityState.setOnClickListener {
            context?.let { context ->
                val popup = PopupMenu(context, binding.currentCityState)
                val menu = popup.menu

                for (location in LocationKeysEnum.entries) {
                    menu.add(location.russianName)
                }

                popup.setOnMenuItemClickListener { item ->
                    val selectedLocation =
                        LocationKeysEnum.entries.find { it.russianName == item.title }
                    if (selectedLocation != null) {
                        binding.currentCityState.text = selectedLocation.russianName
                        sharedPreferences?.edit()
                            ?.putInt("currentLocation", selectedLocation.locationKey)
                            ?.apply()
                        loadData(selectedLocation.locationKey)
                    }
                    true
                }
                popup.show()
            }
        }


    }

    fun getCurrentDateFormatted(): String {
        val currentDate = Date()
        return SimpleDateFormat("dd MMMM", Locale("ru", "RU")).format(currentDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}