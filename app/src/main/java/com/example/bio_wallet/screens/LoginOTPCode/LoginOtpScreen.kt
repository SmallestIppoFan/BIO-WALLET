package com.example.bio_wallet.screens.LoginOTPCode

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bio_wallet.R
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.commans.OtpTextField
import com.example.bio_wallet.screens.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LoginOTPScreen(navigator: DestinationsNavigator) {
    val otpCode = remember{
        mutableStateOf("")
    }
    val buttonEnabled = otpCode.value.length ==4
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.otp_img), contentDescription ="", modifier = Modifier.size(120.dp) )
            Text(text = "Verification", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(text = "Enter OTP code send to your number", color = Color.Gray.copy(0.8f), fontSize = 18.sp)
            Text(text = "87002450251",color = Color.Gray.copy(0.8f), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(50.dp))
            OtpTextField(otpText = otpCode.value , onOtpTextChange ={ it, bool ->
                otpCode.value = it
            } )
            Spacer(modifier = Modifier.height(50.dp))
            Button(onClick = {navigator.navigate(MainScreenDestination) }, colors = ButtonDefaults.buttonColors(containerColor = Colors.mainColor),
                modifier = Modifier.width(150.dp).height(50.dp), enabled = buttonEnabled) {
                Text(text = "Continue", color = Color.White)
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