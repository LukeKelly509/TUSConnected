package com.example.tusconnected

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme

class TUSHubPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TUSHub(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TUSHub(navController: NavController) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { Text("Your Title", color = Color.Black) },
            modifier = Modifier
                .background(Color.LightGray)
                .height(80.dp)
                .fillMaxWidth()
                .padding(100.dp)
        )
        val logoImage = painterResource(id = R.drawable.tuslogo)
        Image(
            painter = logoImage,
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(y = -75.dp, x = -150.dp)
                .scale(0.2f)
        )

        val accountLogoImage = painterResource(id = R.drawable.accountlogo)
        Image(
            painter = accountLogoImage,
            contentDescription = "Account Logo",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(y = -75.dp, x = 25.dp)
                .scale(0.3f)
        )
    }

//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(100.dp),
//    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to the TUSHub",
            textAlign = TextAlign.Center,
            color = Color.Black,
            style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(modifier = Modifier.padding(bottom = 16.dp))

        val buttonItems = listOf(
            "TIMETABLE" to "TimetablePage",
            "CAMPUS MAP" to "CampusMapPage",
            "NEWS FEED" to "NewsFeedPage",
            "ABOUT US" to "AboutUSPage"
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(buttonItems.take(2)) { (text, destination) ->
                        Card(
                            modifier = Modifier
                                .width(160.dp)
                                .height(160.dp)
                                .clickable { navController.navigate(destination) }
                                .padding(8.dp),
                        ) {
                            Image(
                                painter = painterResource(getImageResourceForText(text)),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.FillBounds
//                                    .align(Alignment.Start)
                            )


                            Text(
                                text = text,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )

                        }
                    }
                        }
                    }

            item {
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(buttonItems.takeLast(2)) { (text, destination) ->
                        Card(
                            modifier = Modifier
                                .width(160.dp)
                                .height(160.dp)
                                .clickable { navController.navigate(destination) }
                                .padding(8.dp),
                        ) {
                            Image(
                                painter = painterResource(getImageResourceForText(text)),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Start),
                            )

                            Text(
                                text = text,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
//                                modifier = Modifier.align(Alignment.Bottom)
                            )
                        }
                    }
                }
            }
            }
        }
    }

fun getImageResourceForText(text: String): Int {
    return when (text) {
        "TIMETABLE" -> R.drawable.timetable
        "CAMPUS MAP" -> R.drawable.campusmap
        "NEWS FEED" -> R.drawable.news
        "ABOUT US" -> R.drawable.aboutus
        else -> R.drawable.tuslogo
    }
}
