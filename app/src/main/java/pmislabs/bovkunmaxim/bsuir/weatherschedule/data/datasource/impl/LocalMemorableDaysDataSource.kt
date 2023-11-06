package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.impl

import kotlinx.coroutines.flow.Flow
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.dao.MemorableDayDao
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.MemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay

class LocalMemorableDaysDataSource(private val dao: MemorableDayDao) : MemorableDaysDataSource {

    override fun getMemorableDays(): Flow<List<MemorableDay>> {
        return dao.getAll()
    }

    override fun getMemorableDay(id: Int): Flow<MemorableDay?> {
       return dao.getById(id)
    }

    override suspend fun upsert(memorableDay: MemorableDay) {
        dao.save(memorableDay)
    }

    override suspend fun delete(id: Int) {
        dao.delete(id)
    }
}
