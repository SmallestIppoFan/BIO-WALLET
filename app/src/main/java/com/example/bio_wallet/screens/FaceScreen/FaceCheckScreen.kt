package com.example.bio_wallet.screens.FaceScreen

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bio_wallet.model.auth.UserAuthData
import com.example.bio_wallet.screens.RegisterScreen.CameraPreview
import com.example.bio_wallet.screens.RegisterScreen.CaptureButton
import com.example.bio_wallet.screens.RegisterScreen.RegistrationEvents
import com.example.bio_wallet.screens.destinations.LoginScreenDestination
import com.example.bio_wallet.screens.destinations.MainScreenDestination
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun FaceCheckScreen(navigator: DestinationsNavigator,
                    viewModel: FaceCheckViewModel= hiltViewModel()
                    ) {
    val showLoading = remember{
        mutableStateOf(false)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel.channelFlow, block ={
        viewModel.channelFlow.collect{
            when(it){
                is FaceAuthEvents.ShowLoading ->{
                    showLoading.value = it.show
                    if (!showLoading.value){
                        Toast.makeText(context, "No face mathch",Toast.LENGTH_LONG).show()
                        viewModel.signOut()
                        navigator.popBackStack(LoginScreenDestination,false)
                    }
                }
                FaceAuthEvents.Navigate ->{
                    navigator.navigate(MainScreenDestination)
                }
            }
        }
    } )


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
        {
            var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
            imageCapture = ImageCapture.Builder().build()
            val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
            when {
                cameraPermissionState.hasPermission -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CameraPreview(imageCapture)
                        CaptureButton(Modifier.align(Alignment.BottomCenter),imageCapture,savePhoto = {
                            viewModel.createPhoto( it)
                        })
                    }
                }
                cameraPermissionState.shouldShowRationale -> {
                    Text("Camera permission is needed to capture photos")
                }
                else -> {
                    LaunchedEffect(true) {
                        cameraPermissionState.launchPermissionRequest()
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                if (showLoading.value){
                    CircularProgressIndicator()
                }
        }
    }
}