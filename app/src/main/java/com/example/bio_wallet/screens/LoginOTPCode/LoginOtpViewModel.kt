package com.example.bio_wallet.screens.LoginOTPCode

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bio_wallet.model.auth.UserAuthData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LoginOtpViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {
    private val firebaseAuthSettings = firebaseAuth.firebaseAuthSettings
    var verificationCode:String =""

//    private var channel : ReceiveChannel<OtpResult> = Channel()
    private val channel  = Channel<OtpResult>()
    val eventFlow = channel.receiveAsFlow()

    init {
        firebaseAuth.firebaseAuthSettings.forceRecaptchaFlowForTesting(true)
    }

    fun onLoginCLicked(context: Context,phoneNumber: String){
        firebaseAuth.setLanguageCode("en")
        val callback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("12334", "verification completed")
                signInWithPhoneAuthCredential(context, credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d("12334", "verification failed$p0")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                verificationCode = verificationId
            }
        }
        val options =
            PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L,TimeUnit.SECONDS)
                .setActivity(context as Activity)
                .setCallbacks(callback)
                .build()

        PhoneAuthProvider.verifyPhoneNumber(options)


    }

    fun verifyPhoneNumberWithCode(verificationId: String, code: String,context: Context) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(context = context,credential)
    }


    private fun signInWithPhoneAuthCredential(context: Context, credential: PhoneAuthCredential) {
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(context as Activity) { task ->
                    if (task.isSuccessful) {
                        Log.d("PHONE_AUTH","logged in")
                        UserAuthData.UID = task.result.user!!.uid
                        val isNewUser = task.result.additionalUserInfo?.isNewUser == true
                        if (isNewUser){
                            viewModelScope.launch {
                                channel.send(OtpResult.NEW)
                            }
                        }
                        else {
                            viewModelScope.launch {
                                channel.send(OtpResult.Success)
                            }
                        }
                    } else {
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            viewModelScope.launch {
                              channel.send(OtpResult.Failure)
                            }
                            Log.d("PHONE_AUTH","wrong otp")
                        }
                    }
                }
    }

    enum class OtpResult {
        Success,
        Failure,
        NEW
    }

}