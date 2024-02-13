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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun MainScreen() {
        Surface(modifier = Modifier.fillMaxSize(), color = Colors.mainScreenBg) {
            Column(modifier = Modifier.fillMaxSize()) {
                Surface(modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth(),color = Colors.mainScreenBg) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 25.dp, end = 25.dp, top = 10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                        Row(horizontalArrangement = Arrangement.End) {
                            Card(onClick = { /*TODO*/ }) {
                                Surface {

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
                }
            }
        }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}