package com.example.bio_wallet.screens.MainSreen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bio_wallet.R
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.commans.TransactionCheck
import com.example.bio_wallet.model.TransactionData
import com.example.bio_wallet.screens.destinations.MainScreenDestination
import com.example.bio_wallet.screens.destinations.SettingsScreenDestination
import com.example.bio_wallet.screens.destinations.TransactionScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun MainScreen(
    navigator:DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val showTransactionCheck = remember{
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit){
        viewModel.eventFlow.collect {
            when (it) {
                is MainTransactionEvent.ShowCheck -> {
                    showTransactionCheck.value = it.show
                }
            }
        }
    }

        Surface(modifier = Modifier.fillMaxSize(), color = Colors.splashScreenBg) {
            Column(modifier = Modifier.fillMaxSize()) {
                Surface(modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth(),color = Colors.splashScreenBg) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 25.dp, end = 25.dp, top = 10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                            OutlinedCard(onClick = {
                                navigator.navigate(SettingsScreenDestination)
                                           }, modifier = Modifier.size(40.dp), shape = CircleShape, colors = CardDefaults.outlinedCardColors(containerColor = Colors.splashScreenBg), border = BorderStroke(width = 2.dp, color = Color.Gray.copy(0.5f))) {
                                Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = "Settings icon",
                                        tint = Color.White
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
                            Text(text = state.profileInfo.money ?: "",
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
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp), shadowElevation = 10.dp, tonalElevation = 10.dp
                    ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Transaction History", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
                            OutlinedCard(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, color = Color.Gray.copy(alpha = 0.5f)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                                onClick = {
                                    navigator.navigate(TransactionScreenDestination)
                                }
                            ) {
                                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.transfer_icon),
                                        contentDescription = "",
                                        tint = Colors.splashScreenBg,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(40.dp))
                        }
                        LazyColumn(){
                            if (state.transactionList != null) {
                                items(state.transactionList!!.size) {
                                    TransactionHistory(
                                        state.transactionList!![it].fullName,
                                        state.transactionList!![it].amount
                                    ){
                                        viewModel.getTransactionDataById(state.transactionList!![it].id)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                if (state.loading== LoadingState.Loading) {
                    CircularProgressIndicator()
                }
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                if (showTransactionCheck.value) {
                    TransactionCheck(transactionData = state.checkData){
                        showTransactionCheck.value = false
                    }
                }
            }

        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistory(name:String, price:String,onClick: () ->Unit) {
    Card(onClick = {
        onClick()
    }, modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(start = 10.dp, end = 10.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxSize()
            .height(80.dp),) {
            Image(painter = painterResource( R.drawable.transaction_history_icon), contentDescription ="", modifier = Modifier.size(50.dp) )
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(text = "Transaction", fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = name, fontWeight = FontWeight.Bold, color = Color.Gray.copy(0.8f))
            }
            Spacer(modifier = Modifier.weight(0.9f))
            Text(text = "$price ₸", fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

