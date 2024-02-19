package com.example.bio_wallet.screens.SettingsScreen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bio_wallet.R
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.screens.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                        navigator.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Settings", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(80.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    modifier = Modifier.size(140.dp),
                    color = Color.Gray.copy(alpha = 0.4f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            modifier = Modifier.size(130.dp),
                            color = Color.White
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.user_icon),
                                    contentDescription = "",
                                    modifier = Modifier.size(100.dp)
                                )
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier
                .fillMaxHeight(0.85f)
                .padding(20.dp)) {
                Spacer(modifier = Modifier.height(40.dp))
                InfoColumn("First Name", "Anthony",Icons.Default.Person)
                Spacer(modifier = Modifier.height(20.dp))
                InfoColumn("Second Name", "smith",Icons.Default.Person)
                Spacer(modifier = Modifier.height(20.dp))
                InfoColumn("Number", "87002450251",Icons.Default.Phone)
                Spacer(modifier = Modifier.height(50.dp))
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp), shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp),color = Colors.splashScreenBg
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
//                    modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
//                        navigator.navigate(LoginScreenDestination)
                            }
                            .padding(10.dp)
                    ) {
                        Spacer(modifier = Modifier.width(15.dp))
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Logout",
                            color = Color.White,
                            fontSize = 16.sp
                        )

                    }
                }
            }


        }
    }

}


@Composable
fun InfoColumn(title:String,data:String,icon :ImageVector) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp), color = Color.White) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Icon(imageVector = icon, contentDescription = "")
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = title, color = Color.Black, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = data, color = Color.Black, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Divider(modifier = Modifier.height(2.dp), color = Color.Gray.copy(0.4f))
            }
        }
    }
}


