package com.shabelnikd.weatherapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shabelnikd.weatherapp.R
import com.shabelnikd.weatherapp.adapters.DayItemAdapter
import com.shabelnikd.weatherapp.adapters.HourItemAdapter
import com.shabelnikd.weatherapp.databinding.FragmentWeatherBinding
import com.shabelnikd.weatherapp.models.LocationKeysEnum
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val dayAdapter = DayItemAdapter()
    private val hourAdapter = HourItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initialize()


    }

    private fun loadData() {
        viewModel.getCurrentWeather(LocationKeysEnum.BISHKEK.locationKey)
        viewModel.getOneDayWeather(LocationKeysEnum.BISHKEK.locationKey)
        viewModel.getTwelveHoursWeather(LocationKeysEnum.BISHKEK.locationKey)
        viewModel.getFiveDaysWeather(LocationKeysEnum.BISHKEK.locationKey)
    }

    @SuppressLint("SetTextI18n")
    private fun initialize() {
        binding.rvTodayForecast.adapter = hourAdapter
        binding.rvNextForecast.adapter = dayAdapter

        binding.currentDate.text = getCurrentDateFormatted()
        binding.currentCityState.text = "Бишкек"

        viewModel.currentWeather.observe(viewLifecycleOwner) { currentWeather ->
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
                            day.precipitationProbability.plus(night.precipitationProbability).toString() + "%"
                    }

                    binding.tvWind.text = day?.wind?.speed?.value.toString() + " км/ч"
                }
            }

        }

        viewModel.twelveHoursWeather.observe(viewLifecycleOwner) { twelveHours ->
            hourAdapter.submitList(twelveHours)
        }

        viewModel.fiveDaysWeather.observe(viewLifecycleOwner) { fiveDays ->
            dayAdapter.submitList(fiveDays.dailyForecasts)
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