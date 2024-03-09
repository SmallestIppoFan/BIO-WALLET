package com.example.bio_wallet.commans

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bio_wallet.R
import com.example.bio_wallet.model.TransactionData

@Composable
fun TransactionCheck(transactionData: TransactionData,closeCheck:()->Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black.copy(alpha = 0.9f)) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)) {
                Icon(imageVector = Icons.Default.Clear, contentDescription ="", modifier = Modifier
                    .size(30.dp)
                    .clickable { closeCheck() }, tint = Color.White )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .height(500.dp)
                        .width(300.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_icon),
                                contentDescription = ""
                            )
                            Text(text = "Перевод\nклиенту Wallet", fontSize = 19.sp)
                            Spacer(modifier = Modifier.height(15.dp))
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.user_icon),
                                    contentDescription = "",
                                    modifier = Modifier.size(50.dp)
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                Column {
                                    Text(
                                        text = transactionData.receiverName.toString(),
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = "Wallet Gold",
                                        fontSize = 15.sp
                                    )
                                }

                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp), color = Color.Green.copy(alpha = 0.6f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 10.dp, top = 3.dp)
                            ) {
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = "Transfer successfully completed",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = "${transactionData.amount} ₸",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Row(horizontalArrangement = Arrangement.Start) {
                                Text(text = "Transaction id", color = Color.LightGray)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = transactionData.transactionId.toString())
                            }
                            Divider(modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(horizontalArrangement = Arrangement.Start) {
                                Text(text = "Number", color = Color.LightGray)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = transactionData.receiverPhone.toString())
                            }
                            Divider(modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(5.dp))

                            Row(horizontalArrangement = Arrangement.Start) {
                                Text(text = "Date and time", color = Color.LightGray)
                                Spacer(modifier = Modifier.weight(1f))

                                Text(text = transactionData.date.toString())
                            }
                            Divider(modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(5.dp))

                            Row(horizontalArrangement = Arrangement.Start) {
                                Text(text = "Transaction commission", color = Color.LightGray)
                                Spacer(modifier = Modifier.weight(1f))

                                Text(text = transactionData.commission.toString())
                            }
                            Divider(modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(5.dp))

                            Row(horizontalArrangement = Arrangement.Start) {
                                Text(text = "Transaction sender", color = Color.LightGray)
                                Spacer(modifier = Modifier.weight(1f))

                                Text(text = transactionData.receiverName.toString())
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }
    }
}