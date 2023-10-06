package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.home.HomeViewModel
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.HomeScreenContent

@Composable
fun HomeScreen(snackbarHostState: SnackbarHostState) {
    val viewModel = viewModel<HomeViewModel>();
    HomeScreenContent(
        memorableDays = viewModel.memorableDays,
        onAdd = viewModel::onClickAddOrEditMemorableDay,
        onRemove = viewModel::onClickRemoveMemorableDay,
        snackbarHostState = snackbarHostState
    )
}

