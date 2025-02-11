package com.shabelnikd.weatherapp.ui.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabelnikd.weatherapp.models.CurrentDayWeatherResponse
import com.shabelnikd.weatherapp.models.CurrentWeatherResponse
import com.shabelnikd.weatherapp.models.FiveDaysWeatherResponse
import com.shabelnikd.weatherapp.models.TwelveHourWeatherResponse
import com.shabelnikd.weatherapp.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _currentWeather = MutableLiveData<List<CurrentWeatherResponse>>()
    val currentWeather: LiveData<List<CurrentWeatherResponse>> = _currentWeather

    private val _twelveHoursWeather = MutableLiveData<List<TwelveHourWeatherResponse>>()
    val twelveHoursWeather: LiveData<List<TwelveHourWeatherResponse>> = _twelveHoursWeather

    private val _fiveDaysWeather = MutableLiveData<FiveDaysWeatherResponse>()
    val fiveDaysWeather: LiveData<FiveDaysWeatherResponse> = _fiveDaysWeather

    private val _currentDayWeather = MutableLiveData<CurrentDayWeatherResponse>()
    val currentDayWeather: LiveData<CurrentDayWeatherResponse> = _currentDayWeather

    fun <T> responseWrapper(response: Result<T>, postedLiveData: MutableLiveData<T>) {

        response.onSuccess { weatherData -> postedLiveData.postValue(weatherData) }
        response.onFailure { exception -> Log.e("ALLD", "Error on ViewModel", exception) }
    }

    fun getCurrentWeather(locationKey: Int) {
        viewModelScope.launch {
            responseWrapper(weatherRepository.getCurrentWeather(locationKey), _currentWeather)
        }
    }

    fun getTwelveHoursWeather(locationKey: Int) {
        viewModelScope.launch {
            responseWrapper(
                weatherRepository.getTwelveHourWeather(locationKey), _twelveHoursWeather
            )
        }
    }

    fun getFiveDaysWeather(locationKey: Int) {
        viewModelScope.launch {
            responseWrapper(weatherRepository.getFiveDaysWeather(locationKey), _fiveDaysWeather)
        }
    }

    fun getOneDayWeather(locationKey: Int) {
        viewModelScope.launch {
            responseWrapper(weatherRepository.getOneDayWeather(locationKey), _currentDayWeather)
        }
    }

}