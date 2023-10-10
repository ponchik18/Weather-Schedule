package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.entity

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