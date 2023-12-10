package com.example.tusconnected

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ContactUsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactUs(navController)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUs(navController: NavHostController) {
    val context = LocalContext.current
    val firebase = FirebaseAuth.getInstance()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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

        Text(
            text = "CONTACT US",
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

        val contactUsBannerImage = painterResource(id = R.drawable.contactusimage)
        Image(
            painter = contactUsBannerImage,
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
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Button(
                onClick = {
                    if (isValidContact(name, email, message)) {
                        addContact(name, email, message)
                        navController.navigate("TUSHubPage")
                    } else {
                        errorMessage = "Name > 2, Email starts with K00, Message > 5"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Contact Us", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(bottom = 10.dp))

            val emailAllowed = "k00273673@student.tus.ie"
            val currentUser = FirebaseAuth.getInstance().currentUser?.email

            Button(
                onClick = {
                    if(emailAllowed == currentUser){
                        navController.navigate("ViewContactedPage")
                    } else {
                        navController.navigate("errorPage")
                    }
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Contacted Messages", color = Color.White)
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

fun addContact(name: String, email: String, message: String) {
    val firestore = FirebaseFirestore.getInstance()

    val contactData = hashMapOf(
        "name" to name,
        "email" to email,
        "message" to message
    )

    firestore.collection("contact")
        .add(contactData)
        .addOnSuccessListener { documentReference ->
            println("Contact added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { error ->
            println("Error")
        }
}

private fun createNotificationChannel(context: Context, channelId: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Channel Name"
        val descriptionText = "Channel Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemServiceName(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
fun isValidContact(name: String, email: String, message: String): Boolean{
    return isNameValid(name) && isEmailValid(email) && isValidMessage(message)
}
fun isValidName(name: String): Boolean{
    return name.isNotEmpty() && name.length > 2
}

fun isValidEmail(email: String): Boolean {
    val regex = Regex("^K00.+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    return regex.matches(email)
}

fun isValidMessage(message: String): Boolean{
    return message.isNotEmpty() && message.length > 5
}


