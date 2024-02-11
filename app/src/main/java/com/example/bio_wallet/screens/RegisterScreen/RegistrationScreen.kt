@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.bio_wallet.screens.RegisterScreen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.bio_wallet.commans.Colors
import com.example.bio_wallet.commans.Constants
import com.example.bio_wallet.commans.mobileNumberFilter
import com.example.bio_wallet.screens.destinations.LoginScreenDestination
import com.example.bio_wallet.screens.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun RegistrationScreen(navigator:DestinationsNavigator) {
    val context = LocalContext.current

    val keyboard = LocalSoftwareKeyboardController.current


    val step = remember{
        mutableStateOf(0)
    }


    //first screen vals
    val firstName = remember{
        mutableStateOf("")
    }
    val secondName = remember{
        mutableStateOf("")
    }
    val isKazakh = remember{
        mutableStateOf(false)
    }

    //second screen vals
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
                    .fillMaxHeight(0.9f)
            ) {
                Column(modifier=Modifier.padding(13.dp)) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "", modifier = Modifier.clickable { if (step.value==0){
                        navigator.navigate(LoginScreenDestination)
                    }else{
                        step.value--
                    }
                    })
                    Spacer(modifier = Modifier.height(30.dp))
                    when (step.value) {
                        0 -> {
                            FirstStep(firstName, secondName,isKazakh, keyboard!!)
                        }

                        1 -> {
                            SecondStep(number, keyboard!!)
                        }

                        2 -> {
                            ThirdStep(passFirst, passSecond, termsAccept, keyboard!!)
                        }
                        3 ->{
                            LastStep()
                        }
                    }
                }

            }
            Surface(modifier = Modifier.fillMaxSize()
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
                        colors = ButtonDefaults.buttonColors(containerColor = Colors.mainColor),
                        enabled = (firstName.value != "" && secondName.value != "" && isKazakh.value && step.value==0)||
                                (step.value==1 && number.value != "") || (step.value==2 && passFirst.value.length >= 8 && passSecond.value.length >=8 && termsAccept.value) || step.value ==4
                        ) {
                        Text(
                            text = "Continue",
                            fontSize = 21.sp
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
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            Text(text = "What's your name?",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
                ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "First Name",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                    )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(value = firstName.value, onValueChange ={
                    firstName.value=it
                } ,modifier= Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors( unfocusedBorderColor = Color.Gray.copy(0.7f)),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboard.hide()
                    }),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Last Name",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(value = secondName.value, onValueChange ={
                    secondName.value=it
                } ,modifier= Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors( unfocusedBorderColor = Color.Gray.copy(0.7f)),
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
                        colors = CheckboxDefaults.colors(checkedColor = Colors.mainColor)
                    )
                    Text(text = "I am a citizen of Kazakhstan",
                        color = Color.Black.copy(alpha = 0.8f),
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
    Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Enter your phone number",
            color = Color.Black,
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdStep(passFirst:MutableState<String>,passSecond:MutableState<String>,termsAccept:MutableState<Boolean>,keyboard: SoftwareKeyboardController) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Create a secure password",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
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
                fontWeight = FontWeight.Medium
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
                }),maxLines = 1,                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)

            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Repeat password again",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)

            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = termsAccept.value, onCheckedChange = {
                        termsAccept.value = it
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Colors.mainColor)
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

}


//@SuppressLint("UnrememberedMutableState")
//@Preview
//@Composable
//fun FirstStepPrev() {
//    FirstStep(firstName = mutableStateOf(""), secondName = mutableStateOf(""), isKazakh =mutableStateOf(false) )
//}
//





