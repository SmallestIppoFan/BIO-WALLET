package com.example.bio_wallet.screens.SplashScreen

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bio_wallet.R
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.screens.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination(start = true)
@Composable
fun SplashScreen(navigator:DestinationsNavigator) {
    LaunchedEffect(key1 = Unit, block ={
        delay(2000)
        navigator.navigate(LoginScreenDestination)} )
    Surface(modifier = Modifier.fillMaxSize(),color = Colors.splashScreenBg) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.logo_icon), contentDescription = "",modifier = Modifier.size(120.dp))
        }
    }
}