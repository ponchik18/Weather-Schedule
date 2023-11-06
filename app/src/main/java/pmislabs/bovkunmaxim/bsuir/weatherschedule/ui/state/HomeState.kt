package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.state

import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay

sealed interface HomeState {
    data object Loading : HomeState
    data object Empty : HomeState
    data class  DisplayingMemorableDay(val memorableDays: List<MemorableDay>) : HomeState
    data class Error(val e: Throwable) : HomeState
}