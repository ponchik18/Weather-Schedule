package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.dao.MemorableDayDao
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository.MemorableDaysRepository

class MemorableDaysRepositoryImpl(private val dao: MemorableDayDao) : MemorableDaysRepository {
    override fun getMemorableDay(id: Int?): Flow<MemorableDay?> {
        return if(id == null){
            flowOf(null)
        } else {
            dao.getById(id)
        }
    }

    override fun getAllMemorableDay(): Flow<List<MemorableDay>> {
        return dao.getAll()
    }

    override suspend fun save(memorableDay: MemorableDay?) {
        if(memorableDay != null){
            dao.save(memorableDay)
        }
    }

    override suspend fun delete(id: Int?) {
        if(id != null){
            dao.delete(id)
        }
    }
}