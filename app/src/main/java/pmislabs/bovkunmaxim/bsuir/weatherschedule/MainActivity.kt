package pmislabs.bovkunmaxim.bsuir.weatherschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.WeatherTrackerContent
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.HomeViewModel
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {

    companion object {
        val aboutAppImages = listOf(
            Icons.Filled.Face,
            Icons.Filled.Call,
            Icons.Filled.Star,
            Icons.Filled.Star,
            Icons.Filled.Star,
            Icons.Filled.Star,
            Icons.Filled.Star
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeViewModel =  getViewModel<HomeViewModel>()
        val weatherViewModel =  getViewModel<WeatherViewModel>()
        setContent {
            WeatherTrackerContent(homeViewModel, weatherViewModel)
        }
    }
}


/*@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    WeatherTrackerContent(homeViewModel)
}*/
