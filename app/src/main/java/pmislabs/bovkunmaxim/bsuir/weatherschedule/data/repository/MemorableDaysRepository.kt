package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository

import kotlinx.coroutines.flow.Flow
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay

interface MemorableDaysRepository {
    fun getMemorableDay(id: Int?): Flow<MemorableDay?>
    fun getAllMemorableDay() :Flow<List<MemorableDay>>
    suspend fun  save(memorableDay: MemorableDay?)
    suspend fun delete(id: Int?)
}