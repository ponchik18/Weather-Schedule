package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.HomeViewModel
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.HomeScreenContent
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.RenderState

@Composable
fun HomeScreen(snackbarHostState: SnackbarHostState) {
    val viewModel = viewModel<HomeViewModel>();
    val homeState = viewModel.homeState.collectAsStateWithLifecycle().value

    RenderState(homeState = homeState, snackbarHostState = snackbarHostState, viewModel = viewModel)
}

