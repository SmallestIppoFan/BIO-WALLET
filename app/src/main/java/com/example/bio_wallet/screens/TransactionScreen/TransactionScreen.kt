package com.example.bio_wallet.screens.TransactionScreen

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bio_wallet.R
import com.example.bio_wallet.commans.Colors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun TransactionScreen(navigator: DestinationsNavigator) {
    val number = remember{
        mutableStateOf("706 4199278")
    }
    val moneyAmount = remember {
        mutableStateOf("1250 T")
    }
    Surface(modifier = Modifier.fillMaxSize(), color = Colors.splashScreenBg) {
        Column(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), color = Colors.splashScreenBg
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    OutlinedCard(
                        modifier = Modifier.size(40.dp),
                        colors = CardDefaults.outlinedCardColors(containerColor = Colors.splashScreenBg),
                        border = BorderStroke(width = 1.dp, color = Color.Gray.copy(alpha = 0.5f))
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.7f))
                    Text(
                        text = "Transaction",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Bank Account", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                        Image(painter = painterResource(id = R.drawable.logo_icon), contentDescription = "", modifier = Modifier.size(35.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Wallet Gold", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                        Spacer(modifier = Modifier.weight(0.6f))
                        Text(text = "15 000 T", fontWeight = FontWeight.Medium, fontSize = 17.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Sender Phone", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                        TextField(
                            value = number.value,
                            onValueChange = {
                                number.value = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.Phone, contentDescription = "")
                                    Text(text = "+7", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                containerColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize =16.sp )
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.drawable.user_icon), contentDescription ="" , modifier = Modifier.size(50.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Abay Kunanbaev", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Set amount", fontWeight = FontWeight.Medium, fontSize = 17.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "How much would like to transfer?",  fontSize = 17.sp,color = Color.Gray.copy(0.5f))
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        TextField(value =moneyAmount.value, onValueChange ={
                            moneyAmount.value=it
                        }, colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                            textStyle = TextStyle(color = Color.Black, fontSize = 25.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)

                            )
                    }
                    Column(modifier = Modifier.fillMaxSize().height(60.dp), verticalArrangement = Arrangement.Bottom) {
                        Button(onClick = {  }, colors = ButtonDefaults.buttonColors(containerColor = Colors.splashScreenBg), modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Continue", color = Color.White, fontSize = 17.sp)
                        }
                    }
                }
            }
        }

    }
}


