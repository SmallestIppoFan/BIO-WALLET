package com.example.bio_wallet.screens.LoginOTPCode

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.bio_wallet.commans.OtpTextField
import com.example.bio_wallet.screens.FaceScreen.FaceCheckScreen
import com.example.bio_wallet.screens.destinations.FaceCheckScreenDestination
import com.example.bio_wallet.screens.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@Destination
@Composable
fun LoginOTPScreen(navigator: DestinationsNavigator,viewModel: LoginOtpViewModel = hiltViewModel(),phone:String) {
    val otpCode = remember{
        mutableStateOf("")
    }
    val context = LocalContext.current
    val buttonEnabled = otpCode.value.length ==6



    LaunchedEffect(key1 = Unit, block ={
        viewModel.onLoginCLicked(context,phone)

        viewModel.eventFlow.collect {event ->
            when(event){
                LoginOtpViewModel.OtpResult.Success ->{
                    navigator.navigate(FaceCheckScreenDestination)
                }
                LoginOtpViewModel.OtpResult.Failure ->{

                }
            }
        }
    } )



    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.locked_icon),
                    contentDescription = "",
                    modifier = Modifier.size(120.dp)
                )
                Text(text = "Verification", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text(
                    text = "Enter OTP code send to your number",
                    color = Color.Gray.copy(0.8f),
                    fontSize = 18.sp
                )
                Text(text = phone, color = Color.Gray.copy(0.8f), fontSize = 18.sp)
                Spacer(modifier = Modifier.height(50.dp))
                OtpTextField(otpText = otpCode.value, onOtpTextChange = { it, bool ->
                    otpCode.value = it
                })
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), verticalArrangement = Arrangement.Bottom) {
                Button(
                    onClick = {
                        viewModel.verifyPhoneNumberWithCode(viewModel.verificationCode, code = otpCode.value,context)
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.splashScreenBg),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = buttonEnabled
                ) {
                    Text(text = "Continue", color = Color.White)
                }

            }
        }
    }
}



//
//@Preview
//@Composable
//fun LoginOtpScreenPreview() {
//    LoginOTPScreen()
//}