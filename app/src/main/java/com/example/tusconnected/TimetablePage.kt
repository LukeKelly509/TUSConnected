package com.example.tusconnected

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme

class TimetablePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Timetable(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Timetable(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { Text("", color = Color.Black) },
            modifier = Modifier
                .background(Color.LightGray)
                .height(80.dp)
                .fillMaxWidth()
                .padding(100.dp)
        )
        val backButton = painterResource(id = R.drawable.backbutton)
        var ifIsClicked by remember { mutableStateOf(false)
        }
        Image(
            painter = backButton,
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(y = -32.dp, x = -35.dp)
                .scale(0.3f)
                .clickable(){
                    ifIsClicked = true
                    navController.navigate("TUSHubPage")
                }
        )

        val accountLogoImage = painterResource(id = R.drawable.accountlogo)
        Image(
            painter = accountLogoImage,
            contentDescription = "Account Logo",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(y = -33.dp, x = 25.dp)
                .scale(0.3f)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(100.dp)
        ) {
            Text(
                text = "TIMETABLE",
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }

            BottomAppBar(
                content = { Text("", color = Color.Black) },
                modifier = Modifier
                    .background(Color.LightGray)
                    .height(80.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(100.dp)
            )

            val contactUsImage = painterResource(id = R.drawable.contactus)
            val picturesImage = painterResource(id = R.drawable.pictures)
            val groupchatImage = painterResource(id = R.drawable.groupchat)
            var ifIsClicked by remember { mutableStateOf(false) }

            Image(
                painter = contactUsImage,
                contentDescription = "Contact Us",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 370.dp, x = 0.dp)
                    .height(50.dp)
                    .width(50.dp)
                    .clickable {
                        ifIsClicked = true
                        navController.navigate("ContactUsPage")
                    }
            )

            Image(
                painter = picturesImage,
                contentDescription = "Pictures",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 370.dp, x = 130.dp)
                    .height(45.dp)
                    .width(45.dp)
                    .clickable {
                        ifIsClicked = true
                        navController.navigate("PicturesPage")
                    }
            )

            Image(
                painter = groupchatImage,
                contentDescription = "Group Chats",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 370.dp, x = -130.dp)
                    .height(60.dp)
                    .width(60.dp)
                    .clickable {
                        ifIsClicked = true
                        navController.navigate("GroupChatPage")
                    }
            )
        }
    }


