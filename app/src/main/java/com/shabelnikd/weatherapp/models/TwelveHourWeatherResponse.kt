package com.shabelnikd.weatherapp.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwelveHourWeatherResponse(
    var isSelected: Boolean = false,
    var isSelfCreated: Boolean = false,
    @SerialName("DateTime")
    val dateTime: String? = null,
    @SerialName("EpochDateTime")
    val epochDateTime: Long? = null,
    @SerialName("HasPrecipitation")
    val hasPrecipitation: Boolean? = null,
    @SerialName("IconPhrase")
    val iconPhrase: String? = null,
    @SerialName("IsDaylight")
    val isDaylight: Boolean? = null,
    @SerialName("PrecipitationProbability")
    val precipitationProbability: Int? = null,
    @SerialName("Temperature")
    val temperature: Temperature? = null,
) {
    @Serializable
    data class Temperature(
        @SerialName("Unit")
        val unit: String? = null,
        @SerialName("UnitType")
        val unitType: Int? = null,
        @SerialName("Value")
        val value: Double? = null
    )
}
