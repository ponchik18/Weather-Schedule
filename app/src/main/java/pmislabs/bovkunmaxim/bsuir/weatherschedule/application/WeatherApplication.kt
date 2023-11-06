package pmislabs.bovkunmaxim.bsuir.weatherschedule.application

import android.app.Application
import android.content.Context
import androidx.room.Room
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.dao.MemorableDayDao
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.database.AppDatabase
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.MemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.datasource.impl.LocalMemorableDaysDataSource
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.network.WeatherRepository
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository.MemorableDaysRepository
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.repository.impl.MemorableDaysRepositoryImpl
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.HomeViewModel
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.WeatherViewModel

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        fun AppDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather_database"
        )
            .fallbackToDestructiveMigration()
            .build()


        val databaseModule = module {
 /*           single { AppDatabase.getInstance(context = get()) }*/
            single { AppDatabase.getInstance(get()).memorableDayDao() }
            //single<MemorableDaysRepository> { MemorableDaysRepositoryImpl(get()) }
            single<MemorableDaysDataSource> { LocalMemorableDaysDataSource(get()) }
            single { WeatherRepository() }
            viewModel { HomeViewModel(get())}
            viewModel {WeatherViewModel(get(), get())}
        }
        val appModule = module {
            includes(databaseModule)
        }

        startKoin {
            androidContext(this@WeatherApplication)
            modules(databaseModule)
        }
    }

}







