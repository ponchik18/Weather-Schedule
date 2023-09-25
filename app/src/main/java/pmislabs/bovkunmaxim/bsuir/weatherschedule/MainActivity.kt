package pmislabs.bovkunmaxim.bsuir.weatherschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box;
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.*;
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*;
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import pmislabs.bovkunmaxim.bsuir.weatherschedule.MainActivity.Companion.aboutAppImages
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.theme.LightBlue
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.theme.Purple
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.theme.WeatherScheduleTheme

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
            WeatherScheduleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScaffoldLayout ()
                }
            }
        }
    }
}

@Composable
fun ScaffoldLayout(){
    Scaffold(
        topBar = {
            HomeTopAppBar(modifier = Modifier.fillMaxWidth())
        },


    ) {
        paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
            ){
            AboutScreen()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name)
            )
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
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
                for(item in menu_names){
                    DropdownMenuItem(
                        text = {
                            Text(item)
                        },
                        onClick = { /* TODO */ },
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun AboutScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = stringResource(id = R.string.author_photo_link),
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(id = R.string.author_name),
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                        .align(Alignment.Center)

                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = stringResource(id = R.string.about_app),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    brush = Brush.linearGradient(
                        colors = listOf(Cyan, LightBlue, Purple/*...*/)
                    )
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Text(
                text = stringResource(id = R.string.version_name),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray
                ),

            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = stringResource(id = R.string.description),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic

                ),
//                modifier = Modifier.padding(top = 16.dp)

            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            val titles = stringArrayResource(id = R.array.titles)
            val descriptions = stringArrayResource(id = R.array.descriptions)
            for(i in aboutAppImages.indices) {
                ListItem(description = descriptions[i], title = titles[i], image = aboutAppImages[i])
            }
        }
    }
}

@Composable
fun ListItem(description: String, image: ImageVector, title: String){
    Card(shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
        Row(modifier = Modifier.padding(5.dp)) {
            Icon(
                imageVector = image,
                contentDescription = stringResource(R.string.fact),
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = title,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                Text(text = description, style = TextStyle(fontSize = 14.sp))
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun AboutScreenPreview(){
    ScaffoldLayout()
}
