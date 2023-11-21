package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.MemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay

sealed interface MemorableDayIntent {
    data class UpdateMemorableDay(val memorableDay: MemorableDay) : MemorableDayIntent
    data class DeleteMemorableDay(val id: Int) : MemorableDayIntent
}

sealed interface MemorableDayState {
    data class DisplayMemorableDay(val memorableDay: MemorableDay) : MemorableDayState
    data class Error(val e: Throwable) : MemorableDayState
    data object Loading : MemorableDayState
    data object RedirectToHome : MemorableDayState
}

sealed interface MemorableDayEvent {
    data object UpdatedMemorableDay : MemorableDayEvent
    data object DeletedMemorableDay : MemorableDayEvent
}

class MemorableDayDetailViewModel(private val dataSource: MemorableDaysDataSource) :
    MVIViewModel<MemorableDayState, MemorableDayIntent, MemorableDayEvent>(initial = MemorableDayState.Loading) {

    fun setDisplayItem(id: Int) {
        viewModelScope.launch {
            val memorableDay = dataSource.getMemorableDay(id)
                .catch { e ->
                    state { MemorableDayState.Error(e) }
                }
                .collect { item ->
                    if (item != null) {
                        state {
                            MemorableDayState.DisplayMemorableDay(item)
                        }
                    }
                }
        }
    }

    override suspend fun reduce(intent: MemorableDayIntent) {
        try {
            state { MemorableDayState.Loading }
            when (intent) {
                is MemorableDayIntent.DeleteMemorableDay -> {
                    val deletedId = intent.id
                    dataSource.delete(deletedId)
                    state { MemorableDayState.RedirectToHome }
                    event(MemorableDayEvent.DeletedMemorableDay)
                }

                is MemorableDayIntent.UpdateMemorableDay -> {
                    val updatedMemorableDay = intent.memorableDay
                    dataSource.upsert(updatedMemorableDay)
                    state { MemorableDayState.DisplayMemorableDay(updatedMemorableDay) }
                    event(MemorableDayEvent.UpdatedMemorableDay)
                }
            }
        } catch (e: RuntimeException) {
            state { MemorableDayState.Error(e) }
        }
    }
}