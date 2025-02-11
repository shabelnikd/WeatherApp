package com.shabelnikd.weatherapp.repositories

import com.shabelnikd.weatherapp.models.CurrentDayWeatherResponse
import com.shabelnikd.weatherapp.models.CurrentWeatherResponse
import com.shabelnikd.weatherapp.models.FiveDaysWeatherResponse
import com.shabelnikd.weatherapp.models.TwelveHourWeatherResponse
import com.shabelnikd.weatherapp.service.WeatherApiService
import com.shabelnikd.weatherapp.utils.safeApiCall
import javax.inject.Inject
import kotlin.Result

class WeatherRepository @Inject constructor(private val apiService: WeatherApiService) {

    suspend fun getCurrentWeather(locationKey: Int): Result<List<CurrentWeatherResponse>> =
        safeApiCall({ apiService.getCurrentWeather(locationKey) }, "Error fetching current weather")


    suspend fun getTwelveHourWeather(locationKey: Int): Result<List<TwelveHourWeatherResponse>> =
        safeApiCall({ apiService.getTwelveHourWeather(locationKey) }, "Error fetching twelve hour weather")


    suspend fun getFiveDaysWeather(locationKey: Int): Result<FiveDaysWeatherResponse> =
        safeApiCall({ apiService.getFiveDaysWeather(locationKey) }, "Error fetching five days weather")


    suspend fun getOneDayWeather(locationKey: Int): Result<CurrentDayWeatherResponse> =
        safeApiCall({ apiService.getOneDayWeather(locationKey) }, "Error fetching one day weather")

}