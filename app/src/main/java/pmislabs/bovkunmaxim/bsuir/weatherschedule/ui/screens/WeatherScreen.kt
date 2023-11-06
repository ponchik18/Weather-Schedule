package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.RenderHomeState
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.RenderWeatherState
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(snackbarHostState: SnackbarHostState, viewModel: WeatherViewModel) {

    val weatherState = viewModel.weatherState.collectAsStateWithLifecycle().value
    RenderWeatherState(weatherState = weatherState, snackbarHostState = snackbarHostState, viewModel = viewModel)
}

