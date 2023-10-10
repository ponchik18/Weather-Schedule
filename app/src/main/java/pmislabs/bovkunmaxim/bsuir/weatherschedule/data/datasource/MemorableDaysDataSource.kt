package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource

import kotlinx.coroutines.flow.Flow
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.MemorableDay
import java.util.UUID

interface MemorableDaysDataSource {
    fun getMemorableDays(): Flow<List<MemorableDay>>
    fun getMemorableDay(id: UUID): Flow<MemorableDay?>

    suspend fun upsert(memorableDay: MemorableDay)
    suspend fun delete(id: UUID)
}