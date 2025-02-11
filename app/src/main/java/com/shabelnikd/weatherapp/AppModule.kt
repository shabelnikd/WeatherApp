package com.shabelnikd.weatherapp

import android.app.Application
import com.shabelnikd.weatherapp.core.RetrofitClient
import com.shabelnikd.weatherapp.repositories.WeatherRepository
import com.shabelnikd.weatherapp.service.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRetrofitClient(context: Application): RetrofitClient {
        return RetrofitClient.getInstance(context)
    }

    @Provides
    fun provideWeatherApiService(retrofitClient: RetrofitClient): WeatherApiService {
        return retrofitClient.retrofitService
    }

    @Provides
    fun provideWeatherRepository(weatherApiService: WeatherApiService): WeatherRepository {
        return WeatherRepository(weatherApiService)
    }

}