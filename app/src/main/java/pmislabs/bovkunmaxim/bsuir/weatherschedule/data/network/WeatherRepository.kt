package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.WeatherData
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.network.ApiClient.client

class WeatherRepository {
    suspend fun getLast7DayWeather(): Flow<WeatherData> {
        val weatherDateStr = client.get(ApiRoutes.GET_URL).body<String>()
        val weatherData = Json.decodeFromString<WeatherData>(weatherDateStr)
        return flowOf(weatherData);
    }
}