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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tusconnected.ui.theme.TUSConnectedTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth

class CampusMapPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUSConnectedTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CampusMap(navController)
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusMap(navController: NavHostController) {
    val firebase = FirebaseAuth.getInstance()
    var map by remember { mutableStateOf<GoogleMap?>(null) }

//https://developer.android.com/jetpack/compose/migrate/interoperability-apis/views-in-compose <- for AndroidView and creating the lambda
    AndroidView(
        factory = { context ->
            val mapView = MapView(context)
            mapView.onCreate(Bundle())
            mapView.getMapAsync { googleMap ->
                //https://www.youtube.com/watch?v=pOKPQ8rYe6g <- for the googleMap location bit, moveCamera.. used normal map myself
                val location = LatLng(52.6753966,-8.6492355) //that's the astroturf one (why?? just to show the college in the center(college is 52.6755332,-8.6501045))
                map = googleMap
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
                googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

                //Marker part of mapsdemoik on Moodle
                googleMap.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title("TUS College")
                        .snippet("TUS College")
                )
            }
            mapView
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
    )

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
            text = "CAMPUS MAP",
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

//        val campusMapImage = painterResource(id = R.drawable.tuscloseup)
//        Image(
//            painter = campusMapImage,
//            contentDescription = "Campus Map Image",
//            modifier = Modifier
//                .align(Alignment.TopCenter)
////                .offset(y = -33.dp, x = 25.dp)
////                .scale(0.3f)
//                .fillMaxSize()
//                .height(200.dp)
//                .padding(bottom = 435.dp)
//        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(100.dp)
        ) {

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

