@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.bio_wallet.screens.RegisterScreen

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LifecycleOwner
import com.example.bio_wallet.R
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.commans.Constants
import com.example.bio_wallet.commans.mobileNumberFilter
import com.example.bio_wallet.screens.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.camera.core.Preview
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun RegistrationScreen(
    navigator:DestinationsNavigator
) {
    val context = LocalContext.current

    val keyboard = LocalSoftwareKeyboardController.current


    val step = remember{
        mutableStateOf(0)
    }


    val firstName = remember{
        mutableStateOf("")
    }
    val secondName = remember{
        mutableStateOf("")
    }
    val isKazakh = remember{
        mutableStateOf(false)
    }

    val number = remember{
        mutableStateOf("")
    }


    //last step screen val
    val passFirst = remember {
        mutableStateOf("")
    }
    val passSecond = remember{
        mutableStateOf("")
    }
    val termsAccept = remember {
        mutableStateOf(false)
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                color = Colors.splashScreenBg

            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f),
                        color = Colors.splashScreenBg
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .clickable {
                                            if (step.value == 0) {
                                                navigator.popBackStack()
                                            } else {
                                                step.value--
                                            }
                                        }
                                        .padding(10.dp),
                                    tint = Color.White
                                    )

                            }
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "Sign Up", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 35.sp)
                            }
                        }
                    }
                    Surface(modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        ) {
                        when (step.value) {
                            0 -> {
                                FirstStep(firstName, secondName, isKazakh, keyboard!!)
                            }

                            1 -> {
                                SecondStep(number, keyboard!!)
                            }

                            2 -> {
                                LastStep()
                            }

                            3 -> {
                                CameraScreen()
                            }
                        }
                    }
                }

            }
            Surface(modifier = Modifier.fillMaxSize(),
                color = Color.White
                ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    verticalArrangement = Arrangement.Bottom
                    ) {
                    Button(onClick = {
                        if (step.value != 3) {
                            if (step.value==2 && passFirst.value!=passSecond.value){
                                Toast.makeText(context,"Passwords do not match",Toast.LENGTH_SHORT).show()
                            }else {
                                step.value++
                            }
                        }
                        else{
                            navigator.navigate(MainScreenDestination)
                        }

                    },
                        modifier= Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Colors.splashScreenBg),
                        enabled = (firstName.value != "" && secondName.value != "" && isKazakh.value && step.value==0)||
                                (step.value==1 && number.value != "")  || step.value ==2 || step.value ==3
                        ) {
                        Text(
                            text = "Continue",
                            fontSize = 21.sp,
                            color = Color.White
                        )

                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FirstStep(firstName:MutableState<String>,secondName:MutableState<String>,isKazakh:MutableState<Boolean>,keyboard:SoftwareKeyboardController) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
            ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "What's your name?",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Colors.splashScreenBg
            )
            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
                ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "First Name",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray.copy(0.5f)
                    )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = firstName.value, onValueChange ={
                    firstName.value=it
                } ,modifier= Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors( unfocusedBorderColor = Color.Gray.copy(0.5f)),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboard.hide()
                    }),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Last Name",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray.copy(0.5f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = secondName.value, onValueChange ={
                    secondName.value=it
                } ,modifier= Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Gray.copy(0.5f)),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboard.hide()
                    }),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isKazakh.value, onCheckedChange = {
                            isKazakh.value = it
                        },
                        colors = CheckboxDefaults.colors(checkedColor = Colors.splashScreenBg)
                    )
                    Text(text = "I am a citizen of Kazakhstan",
                        color = Color.Gray.copy(0.5f),
                        fontSize = 15.sp
                        )
                }
            }
        }

}
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SecondStep(number:MutableState<String>,keyboard: SoftwareKeyboardController) {
    val requester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    Column(modifier=Modifier.fillMaxSize(),  horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "Enter your phone number",
            color = Colors.splashScreenBg,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
            )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = Constants.PHONE_PREFIX,
                fontSize = 25.sp,
                color = Color.Black
            )
            BasicTextField(
                value = number.value,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .focusRequester(requester),
                onValueChange = {
                    if (it.length <= 10 && it.isDigitsOnly()) number.value =
                        it
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 25.sp
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboard.hide()
                    }
                ),
                visualTransformation = {
                    mobileNumberFilter(it)
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ThirdStep(passFirst:MutableState<String>,passSecond:MutableState<String>,termsAccept:MutableState<Boolean>,keyboard: SoftwareKeyboardController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), 
//        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Create a secure password",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color=Colors.splashScreenBg
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Your password should be at least 8 characters.",
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
        Column(horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Password",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = passFirst.value, onValueChange ={
                passFirst.value=it
            } ,modifier= Modifier
                .fillMaxWidth()
                .height(50.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors( unfocusedBorderColor = Color.Gray.copy(0.7f)),
                keyboardActions = KeyboardActions(onDone = {
                    keyboard.hide()
                }),maxLines = 1,                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription ="" )
                }

            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Repeat password again",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = passSecond.value, onValueChange ={
                passSecond.value=it
            } ,modifier= Modifier
                .fillMaxWidth()
                .height(50.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors( unfocusedBorderColor = Color.Gray.copy(0.7f)),
                keyboardActions = KeyboardActions(onDone = {
                    keyboard.hide()
                }),maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription ="" )
                }

            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = termsAccept.value, onCheckedChange = {
                        termsAccept.value = it
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Colors.splashScreenBg)
                )
                Text(text = "By continuing, you agree to our Terms of Service and Privacy Policy.",
                    color = Color.Black.copy(alpha = 0.8f),
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun LastStep() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(painter = painterResource(id = R.drawable.facescan_icon), contentDescription ="" , modifier = Modifier.size(250.dp))

        }
    }
}



@Composable
fun CameraPreview() {
    val context = LocalContext.current
    AndroidView(factory = { ctx ->
        val previewView = PreviewView(ctx).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    (context as LifecycleOwner), cameraSelector, preview
                )
            } catch (exc: Exception) {
                Log.e("CameraPreview", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
        previewView
    }, update = {})
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen() {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    when {
        cameraPermissionState.hasPermission -> {
            CameraPreview()
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
