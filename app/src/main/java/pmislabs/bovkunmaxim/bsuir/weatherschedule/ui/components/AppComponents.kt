package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pmislabs.bovkunmaxim.bsuir.weatherschedule.R
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.Daily
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.MemorableDay
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.Weather
import pmislabs.bovkunmaxim.bsuir.weatherschedule.data.domain.WeatherType
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens.AboutScreen
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens.HomeScreen
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens.WeatherScreen
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.state.HomeState
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.state.WeatherState
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.theme.WeatherScheduleTheme
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.HomeViewModel
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel.WeatherViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.reflect.KFunction1

@Composable
fun WeatherTrackerContent(homeViewModel: HomeViewModel, weatherViewModel: WeatherViewModel) {
    val navController =  rememberNavController();

    WeatherScheduleTheme(
        dynamicColor = true
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavigation(navController = navController, homeViewModel = homeViewModel, weatherViewModel = weatherViewModel)
        }
    }
}

@Composable
fun ListItem(description: String, image: ImageVector, title: String) {
    Card(
        shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Icon(
                imageVector = image,
                contentDescription = "",
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                Text(text = description, style = TextStyle(fontSize = 14.sp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    var selectedItem by remember { mutableStateOf("home") }
    TopAppBar(title = {
        Text(
            text = stringResource(R.string.app_name)
        )
    }, actions = {
        IconButton(onClick = {
            /** TO DO */
        }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search),
            )

        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite),
            )
        }
        // 4
        IconButton(onClick = { menuExpanded = !menuExpanded }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(R.string.more),
            )
        }
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
        ) {
            val menu_names = stringArrayResource(id = R.array.menu_names)
            for (item in menu_names) {
                DropdownMenuItem(text = {
                    Text(item)
                }, onClick = {
                    menuExpanded = false
                    navController.navigate(item.lowercase())
                    selectedItem = item.lowercase()
                })
            }
        }
    }, modifier = modifier
    )
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    weatherViewModel: WeatherViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            HomeTopAppBar(modifier = Modifier.fillMaxWidth(), navController = navController)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },


        ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = navController, startDestination = "home"
            ) {
                composable("home") { HomeScreen(snackbarHostState, homeViewModel) }
                composable("about") { AboutScreen() }
                composable("weather") {WeatherScreen(snackbarHostState, weatherViewModel)}
            }
        }

    }

}

@Composable
fun ParameterItem(description: String, mainText:String){
    Row{
        Text(
            text = description,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Cyan
            )
        )
        Text(
            text = mainText,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.LightGray
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    memorableDays: List<MemorableDay>,
    onRemove: (MemorableDay) -> Unit,
    onAdd: (MemorableDay, Int) -> Unit,
    snackbarHostState: SnackbarHostState
){
    Box(modifier = Modifier.fillMaxSize()) {
        val sheetState = rememberModalBottomSheetState()
        var selectedMemorableDay by remember { mutableStateOf<MemorableDay?>(null) }
        var isEditing by remember { mutableStateOf(false) }
        var indexToEdit by remember { mutableStateOf(-1) }
        var textSnackbar by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()

    LazyColumn {
        itemsIndexed(memorableDays){ index, memorableDay->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ){

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(modifier = Modifier
                        .padding(bottom = 2.dp)
                        .align(alignment = Alignment.CenterHorizontally)) {
                        val iconId = if (memorableDay.weather.isRaining) R.drawable.baseline_grain_24 else R.drawable.baseline_wb_sunny_24
                        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
                        val formattedDate = memorableDay.date.format(formatter)

                        Text(
                            text = formattedDate, // Add date or header text here
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 2.dp),
                            color = MaterialTheme.colorScheme.primary // Header text color
                        )
                        Icon(
                            painter = painterResource(id = iconId),
                            contentDescription = "",
                        )
                    }
                    ParameterItem("Temperature: ","${memorableDay.weather.temperature}°C")
                    ParameterItem("Wind Speed: ","${memorableDay.weather.windSpeed} km/h")
                    ParameterItem("Cloudiness: ",memorableDay.weather.cloudiness)
                    Text(
                        text = "Description Text:",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary // Customize the text color
                        ),
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(
                        text = memorableDay.description,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp, // Customize line height for better readability
                            color = Color.LightGray // Customize the text color
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {
                            scope.launch {
                                sheetState.show()
                                isEditing = true
                                indexToEdit = index
                                selectedMemorableDay = memorableDay
                                textSnackbar = "Memorable day has been added successfully!"

                            }
                            isEditing = true
                            selectedMemorableDay = memorableDay
//                            textSnackbar ="Memorable day has been edited successfully!";

                        }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = stringResource(R.string.edit),
                            )

                        }
                        IconButton(onClick = {
                            onRemove(memorableDay)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete),
                            )

                        }
                    }
                }
            }


        }
    }
        if(sheetState.isVisible) {
            if (isEditing) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()

                        }
                    },
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceAround) {
                        AddMemorableDay(
                            onAdd, scope, sheetState, snackbarHostState,
                            stringResource(R.string.success_edit), selectedMemorableDay, indexToEdit
                        )
                    }
                }
            } else {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()

                        }
                    },
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceAround) {
                        AddMemorableDay(
                            onAdd, scope, sheetState, snackbarHostState,
                            stringResource(R.string.added_sucess)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemorableDay(
    onAddOrEdit: (MemorableDay, Int) -> Unit,
    scope: CoroutineScope,
    sheetState: SheetState,
    snackbarHostState: SnackbarHostState,
    textSnackbar: String,
    memorableDay: MemorableDay?= null,
    indexToEdit: Int=-1
){
    var description by remember { mutableStateOf(memorableDay?.description ?: "") }

    //var date by remember { mutableStateOf(memorableDay?.date ?: LocalDate.now()) }
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Description input
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weather details inputs
        Button(

            onClick = {

                if(memorableDay != null){
                    val newMemorableDay = memorableDay.copy()
                    newMemorableDay.description =  description
                    onAddOrEdit(newMemorableDay, indexToEdit)
                }
                scope.launch{
                    sheetState.hide()
                        snackbarHostState.showSnackbar(
                            message = textSnackbar,
                            duration = SnackbarDuration.Short
                        )
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }
}

@Composable
fun RenderHomeState(homeState: HomeState, snackbarHostState: SnackbarHostState, viewModel: HomeViewModel) {

    when(homeState) {
        is HomeState.Loading -> Loading()
        is HomeState.Empty -> Empty()
        is HomeState.DisplayingMemorableDay -> HomeScreenContent(
                                memorableDays = homeState.memorableDays,
                                onAdd = viewModel::onClickAddOrEditMemorableDay,
                                onRemove = viewModel::onClickRemoveMemorableDay,
                                snackbarHostState = snackbarHostState
                            )
        is HomeState.Error -> FailureDisplay(homeState.e.message)
    }
}

@Composable
fun RenderWeatherState(weatherState: WeatherState, snackbarHostState: SnackbarHostState, viewModel: WeatherViewModel) {
    val swipeRefreshState = rememberSwipeRefreshState(weatherState is WeatherState.Loading);
    SwipeRefresh(state = swipeRefreshState, onRefresh = viewModel::updateContent) {
        when (weatherState) {
            is WeatherState.Loading -> Loading()
            is WeatherState.DisplayingDailyWeather -> WeatherScreenContent(
                daily = weatherState.weatherDays.get(0),
                memorableDays = weatherState.memorableDays,
                onAdd = viewModel::onClickAdd,
                snackbarHostState = snackbarHostState
            )

            is WeatherState.Error -> FailureDisplay(weatherState.e.message)
        }
    }
}

@Composable
fun WeatherScreenContent(
    daily: Daily,
    snackbarHostState: SnackbarHostState,
    memorableDays: List<MemorableDay>,
    onAdd: (MemorableDay) -> Unit
) {

    LazyColumn {
        itemsIndexed(daily.temperatureMax) { index, temperatureMax ->

            val iconId =
                if (daily.rainSum.get(index) == 0.0) R.drawable.baseline_wb_sunny_24 else R.drawable.baseline_grain_24
            val localDate = LocalDate.parse(daily.time.get(index), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
            val formattedDate = localDate.format(formatter)
            val memorableDay = MemorableDay("", Weather(daily.temperatureMax.get(index), daily.windSpeedMax.get(index), daily.rainSum.get(index) == 0.0, WeatherType.fromWMO(daily.weatherCode.get(index)).weatherDesc),localDate, 0 )
            val isAdded = memorableDays.any { localDate.equals(it.date) }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = formattedDate, // Add date or header text here
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 2.dp),
                            color = MaterialTheme.colorScheme.primary // Header text color
                        )
                        Icon(
                            painter = painterResource(id = iconId),
                            contentDescription = "",
                        )
                    }
                    ParameterItem("Temperature: ", "${daily.temperatureMax.get(index)}°C")
                    ParameterItem("Weather Type: ", "${WeatherType.fromWMO(daily.weatherCode.get(index)).weatherDesc}")
                    //ParameterItem("Cloudiness: ",memorableDay.weather.cloudiness.cloudiness)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ParameterItem("Max wind speed's: ", "${daily.windSpeedMax.get(index)}km/h")
                        if (isAdded) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = stringResource(R.string.delete),
                            )
                        } else {
                            IconButton(onClick = {
//                                scope.launch {
//                                    sheetState.show()
//                                    isEditing = true
//                                    indexToEdit = index
//                                    selectedMemorableDay = memorableDay
//                                    textSnackbar = "Memorable day has been added successfully!"
//
//                                }
//                                isEditing = true
//                                selectedMemorableDay = memorableDay
//                            textSnackbar ="Memorable day has been edited successfully!";
                                onAdd(memorableDay)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = stringResource(R.string.edit),
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
fun Empty() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No data available", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FailureDisplay(errorMessage: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMessage?:stringResource(R.string.error), color = Color.Red)
        Spacer(modifier = Modifier.height(16.dp))

    }
}


