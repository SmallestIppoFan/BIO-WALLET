package com.example.bio_wallet.screens.LoginScreen

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
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bio_wallet.R
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.screens.destinations.LoginOTPScreenDestination
import com.example.bio_wallet.screens.destinations.MainScreenDestination
import com.example.bio_wallet.screens.destinations.RegistrationScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator?
) {
    val login = remember{
        mutableStateOf("")
    }
    val keyboard = LocalSoftwareKeyboardController.current

    Surface(color = Color.White,
        modifier = Modifier.fillMaxSize(),
        ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Image(painter = painterResource(id = R.drawable.logo_icon), contentDescription ="",modifier =Modifier.size(100.dp) )
            Spacer(modifier = Modifier.height(100.dp))

            Text(text = "Welcome back",
                    fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Colors.splashScreenBg
                )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "The most secure mobile wallet with biometrical date", color = Colors.splashScreenBg)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = login.value,
                onValueChange = {
                    login.value = it
                },
                leadingIcon = {
                    Row {
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "",
                            tint = Colors.splashScreenBg
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "+7")
                    }
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = {
                    keyboard!!.hide()
                }),
                    singleLine = true,

            )
            Spacer(modifier = Modifier.height(40.dp))
            Row {
                Button(onClick = {
                                 navigator!!.navigate(LoginOTPScreenDestination(phone = "+7${login.value}"))
                },modifier= Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.mainColorV2),
                    ) {
                    Text(text = "Log in",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                        )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row {
                Text(text = "New to Wallet?")
                Text(text = " Sign Up", color = Colors.splashScreenBg, fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
                    navigator!!.navigate(RegistrationScreenDestination())
                })
            }

        }

    }
}



//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}


