package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val daily_units: DailyUnits,
    val daily: Daily
)

@Serializable
data class DailyUnits(
    val time: String,
    @SerialName("weather_code")
    val weatherCode: String,
    @SerialName("temperature_2m_max")
    val temperatureMax: String,
    @SerialName("rain_sum")
    val rainSum: String,
    @SerialName("wind_speed_10m_max")
    val windSpeedMax: String
)

@Serializable
data class Daily(
    val time: List<String>,
    @SerialName("weather_code")
    val weatherCode: List<Int>,
    @SerialName("temperature_2m_max")
    val temperatureMax: List<Double>,
    @SerialName("rain_sum")
    val rainSum: List<Double>,
    @SerialName("wind_speed_10m_max")
    val windSpeedMax: List<Double>
)
