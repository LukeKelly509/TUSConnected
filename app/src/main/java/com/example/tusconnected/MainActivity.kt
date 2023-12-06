package com.example.tusconnected

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "LoginPage"
                ) {
                    composable("LoginPage") {
                        LoginPage(navController)
                    }
                    composable("TUSHubPage") {
                        TUSHub(navController)
                    }
                    composable("AboutUSPage") {
                        AboutUs(navController)
                    }
                    composable("NewsFeedPage") {
                        NewsFeed(navController)
                    }
                    composable("CampusMapPage") {
                        NewsFeed(navController)
                    }
                    composable("TimetablePage") {
                        Timetable(navController)
                    }
                }
            }
        }
    }
}

    @Composable
    private fun navigateToTUSHubPage() {
        val navController = rememberNavController()
        navController.navigate("TUSHubPage")
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    var kNumberText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Login Now",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = kNumberText,
            onValueChange = { kNumberText = it },
            label = { Text("KNumber", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            label = { Text("Password", color = Color.Black) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                navController.navigate("TUSHubPage")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("LOGIN", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "TUSConnected",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Hope you have a great time",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )



//        BottomAppBar(
//            content = { Text("Your Title", color = Color.White) },
//            modifier = Modifier
//                .background(Color.Black)
//                .height(80.dp)
//                .fillMaxWidth()
//                .padding(200.dp)
//                .align(Alignment.BottomCenter)
//        )
    }
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
}