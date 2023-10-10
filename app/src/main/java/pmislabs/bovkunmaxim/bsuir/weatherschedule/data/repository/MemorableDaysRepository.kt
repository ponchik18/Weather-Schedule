package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository

import kotlinx.coroutines.flow.Flow
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.MemorableDay
import java.util.UUID

interface MemorableDaysRepository {
    fun getMemorableDay(id: UUID?): Flow<MemorableDay?>
    fun getAllMemorableDay() :Flow<List<MemorableDay>>
    suspend fun  save(memorableDay: MemorableDay?)
    suspend fun delete(id: UUID?)
}