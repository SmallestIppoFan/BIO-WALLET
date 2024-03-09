package com.example.bio_wallet.screens.TransactionScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bio_wallet.R
import com.example.bio_wallet.Status
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.commans.CustomAlertDialog
import com.example.bio_wallet.commans.TransactionCheck
import com.example.bio_wallet.model.TransactionData
import com.example.bio_wallet.screens.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
@Destination
fun TransactionScreen(navigator: DestinationsNavigator,
                      transactionViewModel: TransactionViewModel = hiltViewModel()
                      ) {
    val state by transactionViewModel.state.collectAsState()

    val number = remember{
        mutableStateOf("")
    }
    val moneyAmount = remember {
        mutableStateOf("")
    }

    val showDialog = remember{ mutableStateOf(Pair(false,"") )}
    val showReceiver = remember{ mutableStateOf(Pair(false,"")) }
    val showCheck = remember { mutableStateOf(Pair(false, TransactionData())) }

    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit, block = {
        transactionViewModel.eventFlow.collect {
            when (it) {
                is TransactionEvent.ShowDialog -> {
                    showDialog.value= Pair(it.show,it.text)
                    number.value = ""
                }
                is TransactionEvent.ShowReceiver ->{
                    showReceiver.value = Pair(true,it.receiver)
                }
                is TransactionEvent.ShowCheck ->{
                    showCheck.value = Pair(it.show,it.transactionData)
                }
            }
        }
    })
    LaunchedEffect(key1 = number.value, block ={
        if (number.value.length ==10){
          transactionViewModel.getAccountByPhone(number.value)
        }
    } )
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
                        modifier = Modifier.size(40.dp).clickable {
                                                                  navigator.navigate(MainScreenDestination)
                        },
                        colors = CardDefaults.outlinedCardColors(containerColor = Colors.splashScreenBg),
                        border = BorderStroke(width = 1.dp, color = Color.Gray.copy(alpha = 0.85f))
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Bank Account",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_icon),
                            contentDescription = "",
                            modifier = Modifier.size(35.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Wallet Gold", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                        Spacer(modifier = Modifier.weight(0.6f))
                        Text(
                            text = "${state.profileInfo.money} T",
                            fontWeight = FontWeight.Medium,
                            fontSize = 17.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Sender Phone",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextField(
                            value = number.value,
                            onValueChange = { newValue ->
                                if (newValue.length <= 10 || newValue.length < number.value.length) {
                                    number.value = newValue
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.Phone, contentDescription = "")
                                    Text(
                                        text = "+7",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                containerColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize = 16.sp),
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                keyboard!!.hide()
                            })
                        )
                    }
                    if (showReceiver.value.first) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            ReceiverData(showReceiver.value.second)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Set amount", fontWeight = FontWeight.Medium, fontSize = 17.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "How much would like to transfer?",
                        fontSize = 17.sp,
                        color = Color.Gray.copy(0.5f)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = moneyAmount.value,
                            onValueChange = {
                                moneyAmount.value = it
                            },
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                keyboard!!.hide()
                            })
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(60.dp), verticalArrangement = Arrangement.Bottom
                    ) {
                        Button(
                            onClick = { transactionViewModel.confirmTransaction(moneyAmount.value.toInt()) },
                            colors = ButtonDefaults.buttonColors(containerColor = Colors.splashScreenBg),
                            modifier = Modifier.fillMaxWidth(),
                            enabled = state.senderUserUID != ""
                        ) {
                            Text(text = "Continue", color = Color.White, fontSize = 17.sp)
                        }
                    }
                }
            }
        }
        if (state.localLoadingStatus==Status.SHOW_LOADING){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Log.d("asdasdasda","herere1")
                CircularProgressIndicator()
            }
        }
        if (showDialog.value.first){
            CustomAlertDialog(alertText =showDialog.value.second) {
                showDialog.value= Pair(false,"")
            }
        }
        if (showCheck.value.first){
            TransactionCheck(transactionData = showCheck.value.second){
                showCheck.value = Pair(false, TransactionData())
                navigator.navigate(MainScreenDestination)
            }
        }
    }
}

@Composable
private fun ReceiverData(name:String) {
    Image(
        painter = painterResource(id = R.drawable.user_icon),
        contentDescription = "",
        modifier = Modifier.size(50.dp)
    )
    Spacer(modifier = Modifier.width(10.dp))
    Text(text = name, fontWeight = FontWeight.Bold, fontSize = 17.sp)
}


