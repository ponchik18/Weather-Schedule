package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource

import kotlinx.coroutines.flow.Flow
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay

interface MemorableDaysDataSource {
    fun getMemorableDays(): Flow<List<MemorableDay>>
    fun getMemorableDay(id: Int): Flow<MemorableDay?>

    suspend fun upsert(memorableDay: MemorableDay)
    suspend fun delete(id: Int)
}