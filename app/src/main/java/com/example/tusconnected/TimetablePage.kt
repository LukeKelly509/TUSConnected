package com.example.tusconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

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
    val firebase = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    //needed for calling collection "course"
    data class courseDetails(
        val course_name: String,
        val day: String,
        val start_time: String,
        val end_time: String,
        val location: String
    )

    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 240.dp, bottom = 70.dp)
        ) {
            var courseDetailsUsed by remember { mutableStateOf(emptyList<courseDetails>()) }
            var selectedDay by remember { mutableStateOf("") }

            listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday").forEach { day ->
                Button(
                    onClick = { selectedDay = day },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                ) {
                    Text(text = day)
                }
            }

            //same as NewsFeed just tweaked
            if (courseDetailsUsed.isNotEmpty()) {
                courseDetailsUsed.forEach { item ->
                    Text(
                        text = item.course_name,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(start = 190.dp, bottom = 16.dp)
                    )

                    Text(
                        text = item.day,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .background(color = Color.White)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(start = 190.dp, bottom = 16.dp)
                    )

                    Text(
                        text = item.start_time,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(start = 190.dp, bottom = 16.dp)
                    )

                    Text(
                        text = item.end_time,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(start = 190.dp, bottom = 16.dp)
                    )

                    Text(
                        text = item.location,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(start = 190.dp, bottom = 16.dp)
                    )
                }
            }

            LaunchedEffect(selectedDay) {
                //same as About Us, just tweaked to work for timetable
                firestore.collection("timetable")
                    .orderBy("day", Query.Direction.ASCENDING)
                    .whereEqualTo("day", selectedDay)
                    .get()
                    .addOnSuccessListener { result ->
                        val items = result.documents.map { document ->
                            val courseName = document.getString("course_name") ?: ""
                            val day = document.getString("day") ?: ""
                            val location = document.getString("location") ?: ""
                            val start_time = document.getString("start_time") ?: ""
                            val end_time = document.getString("end_time") ?: ""
                            courseDetails(courseName, day, location, start_time, end_time)
                        }
                        courseDetailsUsed = items
                    }
                    .addOnFailureListener { exception ->
                    }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
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
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp)
                    .size(50.dp)
                    .clickable() {
                        ifIsClicked = true
                        navController.navigate("TUSHubPage")
                    }
            )

            Text(
                text = "TIMETABLE",
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
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp, top = 16.dp)
                    .size(50.dp)
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
        var ifIsClicked by remember { mutableStateOf(false) }

        Image(
            painter = contactUsImage,
            contentDescription = "Contact Us",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(70.dp)
                .padding(start = 0.dp, bottom = 10.dp)
                .clickable {
                    ifIsClicked = true
                    navController.navigate("ContactUsPage")
                }
        )

        val logoutImage = painterResource(id = R.drawable.logout)
        var ifClicked by remember { mutableStateOf(false) }

        Image(
            painter = logoutImage,
            contentDescription = "Logout Image",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(100.dp)
                .padding(end = 40.dp, top = 25.dp)
                .clickable() {
                    ifClicked = true
                    firebase.signOut()
                    navController.navigate("LoginPage")
                }
        )

        val addNewsImage = painterResource(id = R.drawable.database)

        Image(
            painter = addNewsImage,
            contentDescription = "Adding News",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp, bottom = 10.dp)
                .size(60.dp)
                .clickable {
                    val emailAllowed = "k00273673@student.tus.ie"
                    val currentUser = FirebaseAuth.getInstance().currentUser?.email
                    println("Email Allowed: $emailAllowed, Current User: $currentUser")
                    if (emailAllowed == currentUser) {
                        ifIsClicked = true
                        navController.navigate("addNewsPage")
                    } else {
                        navController.navigate("errorPage")
                    }
                }
        )

    }
}

