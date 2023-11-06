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
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.MemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.state.HomeState

class HomeViewModel(private val dataSource: MemorableDaysDataSource): ViewModel() {
    private val main: CoroutineDispatcher = Dispatchers.Main

    private val io: CoroutineDispatcher = Dispatchers.IO

    private val memorableDays: SnapshotStateList<MemorableDay> = SnapshotStateList()
    private val _homeState =  MutableStateFlow<HomeState>(HomeState.DisplayingMemorableDay(memorableDays))
    val homeState: StateFlow<HomeState> =_homeState.asStateFlow()
    init {
        viewModelScope.launch(main) {
            dataSource.getMemorableDays().flowOn(io)
                .catch { e ->
                    _homeState.value = HomeState.Error(e)
                }
                .collect{list ->
                    run {
                        memorableDays.removeAll(memorableDays);
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
                dataSource.delete(memorableDay.id)
                memorableDays.removeAll(memorableDays);
                dataSource.getMemorableDays().flowOn(io)
                    .catch { e ->
                        _homeState.value = HomeState.Error(e)
                    }
                    .collect{list ->
                        run {
                            memorableDays.removeAll(memorableDays);
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
                dataSource.upsert(memorableDay)
                memorableDays.removeAll(memorableDays);
                dataSource.getMemorableDays().flowOn(io)
                    .catch { e ->
                        _homeState.value = HomeState.Error(e)
                    }
                    .collect{list ->
                        run {
                            memorableDays.removeAll(memorableDays);
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
}
