package com.shabelnikd.weatherapp.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDayWeatherResponse(
    @SerialName("DailyForecasts")
    val dailyForecasts: List<DailyForecast?>?,
//    @SerialName("Headline")
//    val headline: Headline?
) {
    @Serializable
    data class DailyForecast(
        @SerialName("Date")
        val date: String?,
        @SerialName("Day")
        val day: Day?,

        @SerialName("EpochDate")
        val epochDate: Int?,

        @SerialName("Night")
        val night: Night?,

        @SerialName("Temperature")
        val temperature: Temperature?
    ) {

        @Serializable
        data class Day(
            @SerialName("HasPrecipitation")
            val hasPrecipitation: Boolean?,
            @SerialName("Icon")
            val icon: Int?,
            @SerialName("IconPhrase")
            val iconPhrase: String?,
            @SerialName("PrecipitationProbability")
            val precipitationProbability: Int?,
            @SerialName("RainProbability")
            val rainProbability: Int?,
            @SerialName("RelativeHumidity")
            val relativeHumidity: RelativeHumidity?,
            @SerialName("SnowProbability")
            val snowProbability: Int?,
            @SerialName("Wind")
            val wind: Wind?,
        ) {

            @Serializable
            data class RelativeHumidity(
                @SerialName("Average")
                val average: Int?,
                @SerialName("Maximum")
                val maximum: Int?,
                @SerialName("Minimum")
                val minimum: Int?
            )

            @Serializable
            data class Wind(
                @SerialName("Speed")
                val speed: Speed?
            ) {

                @Serializable
                data class Speed(
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
        data class Night(

            @SerialName("HasPrecipitation")
            val hasPrecipitation: Boolean?,
            @SerialName("IceProbability")
            val iceProbability: Int?,
            @SerialName("Icon")
            val icon: Int?,
            @SerialName("IconPhrase")
            val iconPhrase: String?,
            @SerialName("PrecipitationProbability")
            val precipitationProbability: Int?,
            @SerialName("RainProbability")
            val rainProbability: Int?,
            @SerialName("RelativeHumidity")
            val relativeHumidity: RelativeHumidity?,
            @SerialName("SnowProbability")
            val snowProbability: Int?,
            @SerialName("Wind")
            val wind: Wind?,
        ) {

            @Serializable
            data class RelativeHumidity(
                @SerialName("Average")
                val average: Int?,
                @SerialName("Maximum")
                val maximum: Int?,
                @SerialName("Minimum")
                val minimum: Int?
            )

            @Serializable
            data class Wind(
                @SerialName("Speed")
                val speed: Speed?
            ) {

                @Serializable
                data class Speed(
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
}