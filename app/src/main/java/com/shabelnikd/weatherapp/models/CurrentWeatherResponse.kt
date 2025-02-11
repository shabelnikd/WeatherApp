package com.shabelnikd.weatherapp.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CurrentWeatherResponse(
    @SerialName("EpochTime")
    val epochTime: Int? = null,
    @SerialName("HasPrecipitation")
    val hasPrecipitation: Boolean? = null,
    @SerialName("IsDayTime")
    val isDayTime: Boolean? = null,

    @SerialName("LocalObservationDateTime")
    val localObservationDateTime: String? = null,

    @SerialName("PrecipitationType")
    val precipitationType: String? = null,
    @SerialName("Temperature")
    val temperature: Temperature? = null,
    @SerialName("WeatherIcon")
    val weatherIcon: Int? = null,
    @SerialName("WeatherText")
    val weatherText: String? = null
) {
    @Serializable
    data class Temperature(
        @SerialName("Metric")
        val metric: Metric? = null
    ) {
        @Serializable
        data class Metric(
            @SerialName("Unit")
            val unit: String? = null,
            @SerialName("UnitType")
            val unitType: Int? = null,
            @SerialName("Value")
            val value: Double? = null
        )
    }
}
