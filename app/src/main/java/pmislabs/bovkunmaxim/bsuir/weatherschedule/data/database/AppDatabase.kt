package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.dao.MemorableDayDao
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay
import pmislabs.bovkunmaxim.bsuir.weatherschedule.utli.UUIDConverter

@Database(entities = [MemorableDay::class], version = 2, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun memorableDayDao(): MemorableDayDao
     companion object{
         private var instance: AppDatabase? = null

         @Synchronized
         fun getInstance(context: Context): AppDatabase {
             if(instance == null)
                 instance =  Room.databaseBuilder(
                     context,
                     AppDatabase::class.java,
                     "weather_database"
                 )
                     .fallbackToDestructiveMigration()
                     .build()

             return instance!!

         }
     }
}