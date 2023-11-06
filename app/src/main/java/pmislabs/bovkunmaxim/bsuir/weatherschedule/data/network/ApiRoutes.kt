package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.network

object ApiRoutes {
    val GET_URL:String = "https://api.open-meteo.com/v1/forecast?latitude=53.9&longitude=27.5667&daily=weather_code,temperature_2m_max,rain_sum,wind_speed_10m_max&timezone=Europe%2FMoscow&past_days=7&forecast_days=1"
}