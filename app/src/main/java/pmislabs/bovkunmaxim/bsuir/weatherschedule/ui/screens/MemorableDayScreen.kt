package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.RenderMemorableDayDetailState
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.MemorableDayDetailViewModel

@Composable
fun MemorableDayScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: MemorableDayDetailViewModel,
    memorableId: Int,
    navController: NavHostController,
    scope: CoroutineScope
) {
    viewModel.setDisplayItem(memorableId)
    val memorableDayState = viewModel.states.collectAsStateWithLifecycle().value
    RenderMemorableDayDetailState(memorableDayState = memorableDayState, snackbarHostState = snackbarHostState, viewModel = viewModel, navController, scope)
}

