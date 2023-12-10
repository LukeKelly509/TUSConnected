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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.Date

class NewsFeedPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsFeed(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeed(navController: NavHostController) {
    val firebase = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    data class itemsForNews(
        val title: String,
        val time: Date,
        val description: String
    )
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        TopAppBar(
            title = { Text("", color = Color.Black) },
            modifier = Modifier
                .background(Color.LightGray)
                .height(80.dp)
                .fillMaxWidth()
                .padding(100.dp)
        )
        val backButton = painterResource(id = R.drawable.backbutton)
        var ifIsClicked by remember {
            mutableStateOf(false)
        }

        Image(
            painter = backButton,
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(y = -32.dp, x = -35.dp)
                .scale(0.3f)
                .clickable() {
                    ifIsClicked = true
                    navController.navigate("TUSHubPage")
                }
        )

        Text(
            text = "NEWS PAGE",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
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
        var itemsUsed by remember { mutableStateOf(emptyList<itemsForNews>()) }

        firestore.collection("news")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val items = result.documents.map { document ->
                    val title = document.getString("title") ?: ""
                    val timestamp = document.getTimestamp("timestamp")?.toDate() ?: Date()
                    val description = document.getString("description") ?: ""
                    itemsForNews(title, timestamp, description)
                }
                itemsUsed = items
            }
            .addOnFailureListener { exception ->
            }


        val tusImage = painterResource(id = R.drawable.loginpageimage)
        Image(
            painter = tusImage,
            contentDescription = "TUS College Image",
            modifier = Modifier
                .align(Alignment.TopCenter)
//                .offset(y = -33.dp, x = 25.dp)
//                .scale(0.3f)
                .fillMaxSize()
                .height(200.dp)
                .padding(bottom = 435.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
                .align(Alignment.CenterStart)
                .offset(y = 310.dp)
        ) {
            if (itemsUsed.isNotEmpty()) {
                items(itemsUsed) { item ->
                    Text(
                        text = item.title,
                        color = Color.Red,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)
                    )

                    Text(
                        text = "${item.time}",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Text(
                        text = item.description,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }
            } else {
//                Text(
//                    text = "No News, Lack of it infact, crazy",
//                    color = Color.Black,
//                    style = MaterialTheme.typography.headlineMedium,
//                    modifier = Modifier
//                        .background(color = Color.LightGray)
//                        .fillMaxWidth()
//                        .padding(top = 8.dp, bottom = 8.dp)
//                )
            }
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
        val addNewsImage = painterResource(id = R.drawable.database)
        var ifIsClicked by remember { mutableStateOf(false)
        }

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

//        Image(
//            painter = picturesImage,
//            contentDescription = "Pictures",
//            modifier = Modifier
//                .align(Alignment.Center)
//                .offset(y = 370.dp, x = 130.dp)
//                .height(45.dp)
//                .width(45.dp)
//                .clickable {
//                    ifIsClicked = true
//                    navController.navigate("PicturesPage")
//                }
//        )

        val logoutImage = painterResource(id = R.drawable.logout)
        var ifClicked by remember { mutableStateOf(false) }
        Image(
            painter = logoutImage,
            contentDescription = "Logout Image",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(y = 375.dp, x = -50.dp)
                .height(60.dp)
                .width(60.dp)
                .clickable() {
                    ifClicked = true
                    firebase.signOut()
                    navController.navigate("LoginPage")
                }
        )

        Image(
            painter = addNewsImage,
            contentDescription = "Adding News",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 370.dp, x = -130.dp)
                .height(60.dp)
                .width(60.dp)
                .clickable {
                    ifIsClicked = true
                    navController.navigate("addNewsPage")
                }
        )
    }
    }


