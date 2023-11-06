package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.MemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.Daily
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.network.WeatherRepository
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.state.HomeState
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.state.WeatherState

class WeatherViewModel(private val weatherRepository: WeatherRepository, private val memorableDaysDataSource: MemorableDaysDataSource): ViewModel() {
    private val main: CoroutineDispatcher = Dispatchers.Main

    private val io: CoroutineDispatcher = Dispatchers.IO

    private val memorableDays: SnapshotStateList<MemorableDay> = SnapshotStateList()
    private val weatherDays: SnapshotStateList<Daily> = SnapshotStateList()
    private val _weatherState =  MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> =_weatherState.asStateFlow()
    init {
        updateContent()
    }

    fun onClickAdd(memorableDay:MemorableDay) {

        viewModelScope.launch(main) {
            try {
                _weatherState.value = WeatherState.Loading
                memorableDaysDataSource.upsert(memorableDay)
                memorableDays.removeAll(memorableDays);
                updateContent()
            } catch (e:RuntimeException) {
                _weatherState.value = WeatherState.Error(e)
            }
        }
    }

    fun updateContent() {

            viewModelScope.launch(main) {
                memorableDaysDataSource.getMemorableDays().flowOn(io)
                    .catch { e ->
                        _weatherState.value = WeatherState.Error(e)
                    }
                    .collect { list ->
                        run {
                            memorableDays.removeAll(memorableDays);
                            memorableDays.addAll(list)
                        }
                    }
            }
            viewModelScope.launch(main) {
                try {
                    weatherRepository.getLast7DayWeather().flowOn(io)
                        .catch { e ->
                            _weatherState.value = WeatherState.Error(e)
                        }
                        .collect { data ->
                            run {
                                weatherDays.removeAll(weatherDays);
                                weatherDays.add(data.daily)
                                _weatherState.value =
                                    WeatherState.DisplayingDailyWeather(weatherDays, memorableDays)
                            }
                        }
                } catch (e: Exception) {
                    _weatherState.value = WeatherState.Error(e)
                }
            }
    }
}
