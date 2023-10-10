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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.Cloudiness
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.MemorableDay
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.Weather
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository.MemorableDaysRepository
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository.impl.MemorableDaysRepositoryImpl
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.state.HomeState
import java.time.LocalDate

class HomeViewModel(): ViewModel() {
    val main: CoroutineDispatcher = Dispatchers.Main

    val io: CoroutineDispatcher = Dispatchers.IO
    private val repository : MemorableDaysRepository = MemorableDaysRepositoryImpl
    val memorableDays: SnapshotStateList<MemorableDay> = SnapshotStateList()
    private val _homeState =  MutableStateFlow<HomeState>(HomeState.DisplayingMemorableDay(memorableDays))
    val homeState: StateFlow<HomeState> =_homeState.asStateFlow()
    init {
        viewModelScope.launch(main) {
            repository.getAllMemorableDay().flowOn(io)
                .catch { e ->
                    _homeState.value = HomeState.Error(e)
                }
                .collect{list ->
                    run {
                        memorableDays.addAll(list)
                        if(memorableDays.isEmpty()){
                            _homeState.value = HomeState.Empty
                        } else {
                            _homeState.value = HomeState.DisplayingMemorableDay(memorableDays)
                        }
                    }
                }

        }
    }
    fun onClickRemoveMemorableDay(memorableDay: MemorableDay) {
        viewModelScope.launch(main) {
            try {
                _homeState.value = HomeState.Loading
                repository.delete(memorableDay.id)
                memorableDays.removeAll(memorableDays);
                repository.getAllMemorableDay().flowOn(io)
                    .catch { e ->
                        _homeState.value = HomeState.Error(e)
                    }
                    .collect{list ->
                        run {
                            memorableDays.addAll(list)
                            if(memorableDays.isEmpty()){
                                _homeState.value = HomeState.Empty
                            } else {
                                _homeState.value = HomeState.DisplayingMemorableDay(memorableDays)
                            }
                        }
                    }
            } catch (e:RuntimeException) {
                _homeState.value = HomeState.Error(e)
            }
        }
    }

    fun onClickAddOrEditMemorableDay(memorableDay: MemorableDay, indexToEdit:Int=-1) {

        viewModelScope.launch(main) {
            try {
                _homeState.value = HomeState.Loading
                repository.save(memorableDay)
                memorableDays.removeAll(memorableDays);
                repository.getAllMemorableDay().flowOn(io)
                    .catch { e ->
                        _homeState.value = HomeState.Error(e)
                    }
                    .collect{list ->
                        run {
                            memorableDays.addAll(list)
                            if(memorableDays.isEmpty()){
                                _homeState.value = HomeState.Empty
                            } else {
                                _homeState.value = HomeState.DisplayingMemorableDay(memorableDays)
                            }
                        }
                    }
            } catch (e:RuntimeException) {
                _homeState.value = HomeState.Error(e)
            }
        }
    }

    private suspend fun fetchAllItem() {
        val gettingMemorableDay = repository.getAllMemorableDay()
        gettingMemorableDay.collect { list -> memorableDays.addAll(list) }
        if(memorableDays.isEmpty()){
            _homeState.emit(HomeState.Empty)
        } else {
            _homeState.emit(HomeState.DisplayingMemorableDay(memorableDays))
        }
    }



    private companion object{
        private val DefaultMemorableDays = listOf(
            MemorableDay(
                "This day was productive for me!",
                Weather(
                    25, 45.5f, true, Cloudiness.PARTLY_CLOUDY
                ),
                LocalDate.parse("2023-09-23")
            ),
            MemorableDay(
                "Workout is the best thing in the world!",
                Weather(
                    30, 50.5f, false, Cloudiness.CLEAR
                ),
                LocalDate.parse("2023-09-24")
            ),
            MemorableDay(
                "I hate this day for its weather",
                Weather(
                    10, 60f, true, Cloudiness.CLOUDY
                ),
                LocalDate.parse("2023-09-25")
            ),
        )
    }
}
