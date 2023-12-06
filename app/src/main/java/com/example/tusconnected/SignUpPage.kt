package com.example.tusconnected

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.tooling.data.EmptyGroup.name
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme

class SignUpPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUp(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavHostController) {
    var kNumberText by remember { mutableStateOf("") }
    var nameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(200.dp))

        Text(
            text = "Sign Up",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = nameText,
            onValueChange = { nameText = it
                            errorMessage = ""},
            label = { Text("Name", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = kNumberText,
            onValueChange = { kNumberText = it
                            errorMessage = ""},
            label = { Text("KNumber", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = passwordText,
            onValueChange = { passwordText = it
                            errorMessage = ""},
            label = { Text("Password", color = Color.Black) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                if (isValidSignUp(nameText, kNumberText, passwordText)) {
                    navController.navigate("TUSHubPage")
                } else {
                    errorMessage = "Not correct, try again!"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("SIGN UP", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = AnnotatedString("Back to Login"),
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = TextStyle(color = Color.Blue)
        )

        Spacer(modifier = Modifier.height(16.dp))

//        Text(
//            text = "TUSConnected",
//            color = Color.Black,
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 8.dp)
//                .align(Alignment.CenterHorizontally),
//        )
//
//        Text(
//            text = "Welcome to the community!",
//            color = Color.Black,
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 16.dp)
//                .align(Alignment.CenterHorizontally)
//        )
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


fun isValidSignUp(name: String, kNumber: String, password: String): Boolean {
    return isNameValid(name) && isKNumberValid(kNumber) && isPasswordValid(password)
}

fun isNameValid(name: String): Boolean {
    return name.length > 2
}

fun isKNumberValid(kNumber: String): Boolean {
    return kNumber.startsWith("K00") && kNumber.length == 9
}

fun isPasswordValid(password: String): Boolean {
    return password.isNotEmpty() && password.length > 5
}