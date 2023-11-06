package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = MemorableDay.TableName)
data class MemorableDay (
    var description: String,
    @Embedded var weather: Weather,
    var date: LocalDate,
    @PrimaryKey(autoGenerate = true)
    val id: Int
){
    companion object {
        const val TableName = "memorable_day"
    }
}
data class Weather (
    var temperature: Double,
    var windSpeed: Double,
    var isRaining: Boolean,
    var cloudiness: String,
)