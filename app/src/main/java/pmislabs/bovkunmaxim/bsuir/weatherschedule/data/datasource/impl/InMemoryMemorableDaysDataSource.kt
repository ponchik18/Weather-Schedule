package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.impl

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.MemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.Cloudiness
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.MemorableDay
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity.Weather
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

object InMemoryMemorableDaysDataSource : MemorableDaysDataSource {
    private val DefaultMemorableDays = listOf(
        MemorableDay(
            "This day was productive for me!",
            Weather(
                25, 45.5f, true, Cloudiness.PARTLY_CLOUDY
            ),
            LocalDate.parse("2023-09-23")
        ),
        MemorableDay(
            "Workout is the best thing in the world!",
            Weather(
                30, 50.5f, false, Cloudiness.CLEAR
            ),
            LocalDate.parse("2023-09-24")
        ),
        MemorableDay(
            "I hate this day for its weather",
            Weather(
                10, 60f, true, Cloudiness.CLOUDY
            ),
            LocalDate.parse("2023-09-25")
        )
    )

    private val memorableDays = DefaultMemorableDays.associateBy{it.id}.toMutableMap()
    private val _memorableDayFlow = MutableStateFlow<Map<UUID, MemorableDay>>(memorableDays);

    override fun getMemorableDays(): Flow<List<MemorableDay>> {
        return _memorableDayFlow.asSharedFlow().onStart { delay(1000L) }.map { map -> map.values.toList() };
    }

    override fun getMemorableDay(id: UUID): Flow<MemorableDay?> {
        return _memorableDayFlow.asSharedFlow().onStart { delay(1000L) }.map { memorableDays ->
            memorableDays[id]
        }
    }

    override suspend fun upsert(memorableDay: MemorableDay) {
        randomError()
        memorableDays[memorableDay.id] = memorableDay
        _memorableDayFlow.emit(memorableDays)
    }

    override suspend fun delete(id: UUID) {
        randomError()
        memorableDays.remove(id)
        _memorableDayFlow.emit(memorableDays)
    }

    private fun randomError() {
        val random = Random.nextInt(1, 11) // Generate a random number between 1 and 20
        if (random == 10) {
            throw RuntimeException("Error thrown randomly once every 10 calls.")
        }
    }
}
