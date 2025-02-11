package com.shabelnikd.weatherapp.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FiveDaysWeatherResponse(
    @SerialName("DailyForecasts")
    val dailyForecasts: List<DailyForecast?>?,
    @SerialName("Headline")
    val headline: Headline?
) {
    @Serializable
    data class DailyForecast(
        @SerialName("Date")
        val date: String?,
        @SerialName("Day")
        val day: Day?,
        @SerialName("EpochDate")
        val epochDate: Long?,
        @SerialName("Temperature")
        val temperature: Temperature?
    ) {
        @Serializable
        data class Day(
            @SerialName("HasPrecipitation")
            val hasPrecipitation: Boolean?,
            @SerialName("IconPhrase")
            val iconPhrase: String?
        )

        @Serializable
        data class Temperature(
            @SerialName("Maximum")
            val maximum: Maximum?,
            @SerialName("Minimum")
            val minimum: Minimum?
        ) {
            @Serializable
            data class Maximum(
                @SerialName("Unit")
                val unit: String?,
                @SerialName("UnitType")
                val unitType: Int?,
                @SerialName("Value")
                val value: Double?
            )

            @Serializable
            data class Minimum(
                @SerialName("Unit")
                val unit: String?,
                @SerialName("UnitType")
                val unitType: Int?,
                @SerialName("Value")
                val value: Double?
            )
        }
    }

    @Serializable
    data class Headline(
        @SerialName("Category")
        val category: String?,
        @SerialName("EffectiveDate")
        val effectiveDate: String?,
        @SerialName("EffectiveEpochDate")
        val effectiveEpochDate: Int?,

        @SerialName("Severity")
        val severity: Int?,
        @SerialName("Text")
        val text: String?
    )
}