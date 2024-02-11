package com.example.bio_wallet.screens.LoginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.screens.destinations.RegistrationScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator?
) {
    val login = remember{
        mutableStateOf("")
    }
    val password = remember{
        mutableStateOf("")
    }

    Surface(color = Color.White,
        modifier = Modifier.fillMaxSize(),
        ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Text(text = "Welcome back",
                    fontWeight = FontWeight.Bold,
                fontSize = 25.sp
                )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "The most secure mobile wallet with biometrical date")
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(value =login.value , onValueChange ={
                login.value=it
            },
                shape = RoundedCornerShape(15.dp),
                label = {
                    Text(text = "Email")
                },
                modifier = Modifier.fillMaxWidth()
                )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = password.value, onValueChange = {
                password.value = it
            }, shape = RoundedCornerShape(15.dp),
                label = {
                    Text(text = "Password")
                },
                modifier = Modifier.fillMaxWidth()
                )
            Spacer(modifier = Modifier.height(50.dp))
            Row {
                Button(onClick = { },modifier=Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(0.2f))
                    ) {
                    Text(text = "Log in",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                        )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    navigator?.navigate(RegistrationScreenDestination())
                                 },modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.mainColor)
                    ) {
                    Text(text = "Sign Up")
                }

            }

        }

    }
}


//
//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}

