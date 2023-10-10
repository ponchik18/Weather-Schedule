package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.MemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.impl.InMemoryMemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.MemorableDay
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository.MemorableDaysRepository
import java.util.UUID

object MemorableDaysRepositoryImpl : MemorableDaysRepository {
    private val dataSource: MemorableDaysDataSource = InMemoryMemorableDaysDataSource
    override fun getMemorableDay(id: UUID?): Flow<MemorableDay?> {
        return if(id == null){
            flowOf(null)
        } else {
            dataSource.getMemorableDay(id)
        }
    }

    override fun getAllMemorableDay(): Flow<List<MemorableDay>> {
        return dataSource.getMemorableDays()
    }

    override suspend fun save(memorableDay: MemorableDay?) {
        if(memorableDay != null){
            dataSource.upsert(memorableDay)
        }
    }

    override suspend fun delete(id: UUID?) {
        if(id != null){
            dataSource.delete(id)
        }
    }
}