package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.home

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.util.UUID

data class MemorableDay (
    var description: String,
    var weather: Weather,
    var date: LocalDate,
    val id: UUID = UUID.randomUUID(),
)

data class Weather (
    var temperature: Int,
    var humidity: Float,
    var isRaining: Boolean,
    var cloudiness: Cloudiness,
)

enum class Cloudiness(val cloudiness: String){
    CLEAR("clear"),PARTLY_CLOUDY("partly cloudy"),CLOUDY("cloudy")
}

class HomeViewModel(): ViewModel() {
    val memorableDays: SnapshotStateList<MemorableDay> = DefaultMemorableDays.toMutableStateList()
    fun onClickRemoveMemorableDay(memorableDay: MemorableDay) = memorableDays.remove(memorableDay)

    fun onClickAddOrEditMemorableDay(memorableDay: MemorableDay, indexToEdit:Int=-1) {

        if (indexToEdit != -1) {
            memorableDays[indexToEdit].weather = memorableDay.weather.copy()
            memorableDays[indexToEdit].date = memorableDay.date
            memorableDays[indexToEdit].description = memorableDay.description
        } else {
            memorableDays.add(memorableDay)
        }
    }

    private companion object{
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
            ),
        )
    }
}
