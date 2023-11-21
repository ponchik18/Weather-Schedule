package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.RenderHomeState
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,
    homeViewModel: HomeViewModel,
    navController: NavHostController,
    scope: CoroutineScope
) {

    val homeState = homeViewModel.homeState.collectAsStateWithLifecycle().value
    RenderHomeState(homeState = homeState, snackbarHostState = snackbarHostState, viewModel = homeViewModel, navController = navController)
}

