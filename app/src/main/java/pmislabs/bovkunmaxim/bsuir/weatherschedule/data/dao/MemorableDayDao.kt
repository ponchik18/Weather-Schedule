package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay

@Dao
interface MemorableDayDao {

    @Query("SELECT * FROM ${MemorableDay.TableName}")
    fun getAll(): Flow<List<MemorableDay>>

    @Query("SELECT * FROM ${MemorableDay.TableName} WHERE id = :id")
    fun getById(id: Int): Flow<MemorableDay>

    @Upsert
    suspend fun save(e: MemorableDay)

    @Query("DELETE FROM ${MemorableDay.TableName} WHERE id = :id")
    suspend fun delete(id: Int)
}