package com.example.bio_wallet.screens.MainSreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.screens.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun MainScreen(navigator:DestinationsNavigator) {
        Surface(modifier = Modifier.fillMaxSize(), color = Colors.mainScreenBg) {
            Column(modifier = Modifier.fillMaxSize()) {
                Surface(modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth(),color = Colors.mainScreenBg) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 25.dp, end = 25.dp, top = 10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                            Card(onClick = { navigator.navigate(SettingsScreenDestination) }, modifier = Modifier.size(40.dp), shape = CircleShape, colors = CardDefaults.cardColors(containerColor = Color.White)) {
                                Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = "Settings icon",
                                        tint = Colors.mainScreenBg
                                    )
                                }
                            }
                        }
                        Text(text = "Current balance",
                            color = Color.White.copy(0.6f),
                            fontSize = 15.sp
                            )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "₸",
                            color = Color.White,
                            fontSize = 40.sp
                        )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "150000",
                                fontSize = 60.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                                )
                        }
                    }
                }
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), color = Color.White,
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    ) {
                    Column() {
                        Spacer(modifier = Modifier.height(20.dp))
                        LazyColumn(){
                            items(10){
                                TransactionHistory()
                            }
                        }
                    }
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistory() {
    Card(onClick = {

    }, modifier = Modifier
        .fillMaxWidth()
        .height(100.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxSize(),) {
            Surface(color = Colors.mainScreenBg, shape = CircleShape, modifier = Modifier.size(50.dp)) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Amir Slanbek", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.weight(0.9f))
            Text(text = "₸ 15000", fontWeight = FontWeight.Bold, color = Colors.mainScreenBg.copy(alpha = 0.9f))
        }
    }
}


@Preview
@Composable
fun PreviewTransactionHistory() {
    TransactionHistory()
}