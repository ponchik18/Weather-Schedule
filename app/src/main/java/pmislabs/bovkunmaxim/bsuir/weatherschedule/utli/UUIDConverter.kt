package pmislabs.bovkunmaxim.bsuir.weatherschedule.utli

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class UUIDConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromUUID(uuid: UUID?): String? {
            return uuid?.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toUUID(uuid: String?): UUID? {
            return UUID.fromString(uuid)
        }

        private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

        @TypeConverter
        @JvmStatic
        fun fromLocalDate(localDate: LocalDate): String {
            return localDate.format(formatter)
        }

        @TypeConverter
        @JvmStatic
        fun toLocalDate(dateString: String): LocalDate {
            return LocalDate.parse(dateString, formatter)
        }
    }
}