package com.example.tusconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class addNewsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    addNews(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addNews(navController: NavHostController) {
    val firebase = FirebaseAuth.getInstance()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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
            text = "ADD NEWS",
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val addingNewsImage = painterResource(id = R.drawable.loginpageimage)
        Image(
            painter = addingNewsImage,
            contentDescription = "Adding News Image",
            modifier = Modifier
                .align(Alignment.TopCenter)
//                .offset(y = -33.dp, x = 25.dp)
//                .scale(0.3f)
                .fillMaxSize()
                .height(200.dp)
                .padding(bottom = 435.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(100.dp)
        ) {
            Spacer(modifier = Modifier.height(250.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

//            OutlinedTextField(
//                value = timestamp,
//                onValueChange = { timestamp = it },
//                label = { Text("Timestamp") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp)
//            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Button(
                onClick = {
                    if (isValidAdd(title, description)) {
                        addNews(title, description)
                        navController.navigate("TUSHubPage")
                    } else {
                        errorMessage = "Title Length > 3, Description is not null!"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Add News", color = Color.White)
            }
            Spacer(modifier = Modifier.padding(bottom = 30.dp))
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
                    if(emailAllowed == currentUser){
                        ifIsClicked = true
                        navController.navigate("addNewsPage")
                    } else {
                        navController.navigate("errorPage")
                    }
                }
        )

    }
}
//method for adding news to firestore using the news collection

fun addNews(title: String, description: String) {
    val firestore = FirebaseFirestore.getInstance()

    data class infoForNews(
            val title: String,
            val timestamp: Timestamp,
            val description: String
    )

    val newsData = infoForNews(
        title = title,
        timestamp = Timestamp.now(),
        description = description
    )

    firestore.collection("news")
        .add(newsData)
}
//method for making sure both title and description aren't empty
fun isValidAdd(title: String, description: String): Boolean {
    return isValidTitle(title) && isValidDescription(description)
}
//method for making sure title isn't empty and over 3 characters long
fun isValidTitle(title: String): Boolean {
    return title.isNotEmpty() && title.length > 3
}
//method for making sure description isn't empty
fun isValidDescription(description: String): Boolean {
    return description.isNotEmpty()
}


