package pmislabs.bovkunmaxim.bsuir.weatherschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.WeatherTrackerContent

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
        setContent {
            WeatherTrackerContent()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    WeatherTrackerContent()
}
