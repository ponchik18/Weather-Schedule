package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.state

import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.Daily
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay

sealed interface WeatherState {
    data object Loading : WeatherState
    data class  DisplayingDailyWeather(val weatherDays: List<Daily>, val memorableDays: List<MemorableDay>) : WeatherState
    data class Error(val e: Throwable) : WeatherState
}