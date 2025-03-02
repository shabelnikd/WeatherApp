package com.shabelnikd.weatherapp.service

import com.shabelnikd.weatherapp.models.CurrentDayWeatherResponse
import com.shabelnikd.weatherapp.models.CurrentWeatherResponse
import com.shabelnikd.weatherapp.models.FiveDaysWeatherResponse
import com.shabelnikd.weatherapp.models.TwelveHourWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApiService {
    @GET("/currentconditions/v1/{locationKey}")
    @Headers("Cache-Control: public, max-age=600")
    suspend fun getCurrentWeather(
        @Path("locationKey") locationKey: Int,
        @Query("details") isDetails: Boolean = true,
    ): List<CurrentWeatherResponse>

    @GET("/forecasts/v1/hourly/12hour/{locationKey}")
    @Headers("Cache-Control: public, max-age=3600")
    suspend fun getTwelveHourWeather(
        @Path("locationKey") locationKey: Int,
        @Query("metric") metricC: Boolean = true
    ) : List<TwelveHourWeatherResponse>


    @GET("forecasts/v1/daily/5day/{locationKey}")
    @Headers("Cache-Control: public, max-age=3600")
    suspend fun getFiveDaysWeather(
        @Path("locationKey") locationKey: Int,
        @Query("metric") metricC: Boolean = true
    ): FiveDaysWeatherResponse

    @GET("forecasts/v1/daily/1day/{locationKey}")
    @Headers("Cache-Control: public, max-age=3600")
    suspend fun getOneDayWeather(
        @Path("locationKey") locationKey: Int,
        @Query("metric") metricC: Boolean = true,
        @Query("details") isDetails: Boolean = true
    ): CurrentDayWeatherResponse

}