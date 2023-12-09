package com.example.tusconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme
import com.google.firebase.auth.FirebaseAuth

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
                    composable("SignUpPage") {
                        SignUp(navController)
                    }
                    composable("ForgotPasswordPage") {
                        ForgotPassword(navController)
                    }
                    composable("ContactUsPage") {
                        ContactUs(navController)
                    }
                    composable("PicturesPage") {
                        ContactUs(navController)
                    }
                    composable("GroupChatPage") {
                        ContactUs(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun navigateToTUSHubPage(navController: NavController) {
    navController.navigate("TUSHubPage")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    if(errorMessage != null){
        Text(text = errorMessage, color = Color.Red)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(200.dp))

        Text(
            text = "Login Now",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.Black) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        ClickableText(
            text = AnnotatedString("Forgot your password?"),
            onClick = {
                navController.navigate("ForgotPasswordPage")
            },
            modifier = Modifier
//                .padding(top = 8.dp)
                .align(Alignment.End),
            style = TextStyle(color = Color.Blue)
        )

//        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = errorMessage,
            color = Color.Red,
//            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                      if(isValidLogin(email, password)){
                          loginWithEmailAndPassword(email, password, navController)
                      } else {
                          navController.navigate("LoginPage")
                          errorMessage = "Invalid email and or password bro"
                      }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("LOGIN", color = Color.White)
        }
    }

        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = AnnotatedString("Don't have an account yet?"),
                    color = Color.Black,
                    style = TextStyle(color = Color.Black),
                    modifier = Modifier.padding(end = 4.dp)
                )

                ClickableText(
                    text = AnnotatedString("Sign Up"),
                    onClick = {
                        navController.navigate("SignUpPage")
                    },
                    style = TextStyle(color = Color.Blue)
                )
            }
        }


        Box(modifier = Modifier.fillMaxWidth()) {

        TopAppBar(
            title = { Text("", color = Color.Black) },
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
                .offset(y = -75.dp, x = -170.dp)
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

fun isValidLogin(email: String, password: String): Boolean {
    return isEmailValid(email) && isPasswordLoginValid(password)
}

fun isEmailValid(email: String): Boolean {
    return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordLoginValid(password: String): Boolean {
    return password.isNotEmpty() && password.length > 5
}

fun loginWithEmailAndPassword(email: String, password: String, navController: NavController) {
    val firebase = FirebaseAuth.getInstance()
    firebase.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("TUSHubPage")
            } else {
//                val errorMessage = "invalid email and or password"
                navController.navigate("LoginPage")
//                errorMessage
            }
        }
}
