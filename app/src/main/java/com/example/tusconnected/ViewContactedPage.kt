package com.example.tusconnected

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ViewContactedPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ViewContacted(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewContacted(navController: NavController) {
    val firebase = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    data class itemsForContacts(
        val name: String,
        val email: String,
        val message: String
    )

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
                    navController.navigate("ContactUsPage")
                }
        )

        Text(
            text = "Messages",
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
        var contacts by remember { mutableStateOf(emptyList<itemsForContacts>()) }

        firestore.collection("contact")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val contacted = result.documents.map { document ->
                    val name = document.getString("name") ?: ""
                    val email = document.getString("email") ?: ""
                    val message = document.getString("message") ?: ""
                    itemsForContacts(name, email, message)
                }
                contacts = contacted
            }
            .addOnFailureListener { exception ->
            }


        val contactingImage = painterResource(id = R.drawable.contacting)
        Image(
            painter = contactingImage,
            contentDescription = "Viewing Contacted Messages",
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
            if (contacts.isNotEmpty()) {
                items(contacts) { item ->
                    Text(
                        text = item.email,
                        color = Color.Red,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Text(
                        text = item.name,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)
                    )

                    Text(
                        text = item.message,
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
