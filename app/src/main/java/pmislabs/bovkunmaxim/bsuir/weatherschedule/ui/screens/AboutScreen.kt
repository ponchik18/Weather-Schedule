package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import pmislabs.bovkunmaxim.bsuir.weatherschedule.MainActivity
import pmislabs.bovkunmaxim.bsuir.weatherschedule.R
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.components.ListItem
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.theme.LightBlue
import pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.theme.Purple

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
                        colors = listOf(Color.Cyan, LightBlue, Purple/*...*/)
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
            for(i in MainActivity.aboutAppImages.indices) {
                ListItem(description = descriptions[i], title = titles[i], image = MainActivity.aboutAppImages[i])
            }
        }
    }
}