package com.shabelnikd.weatherapp.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwelveHourWeatherResponse(
    @SerialName("DateTime")
    val dateTime: String?,
    @SerialName("EpochDateTime")
    val epochDateTime: Long?,
    @SerialName("HasPrecipitation")
    val hasPrecipitation: Boolean?,
    @SerialName("IconPhrase")
    val iconPhrase: String?,
    @SerialName("IsDaylight")
    val isDaylight: Boolean?,
    @SerialName("PrecipitationProbability")
    val precipitationProbability: Int?,
    @SerialName("Temperature")
    val temperature: Temperature?,
) {
    @Serializable
    data class Temperature(
        @SerialName("Unit")
        val unit: String?,
        @SerialName("UnitType")
        val unitType: Int?,
        @SerialName("Value")
        val value: Double?
    )
}
