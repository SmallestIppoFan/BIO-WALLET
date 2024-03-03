package com.example.bio_wallet.screens.SettingsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bio_wallet.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth):ViewModel() {
    fun signOut(){
        firebaseAuth.signOut()
    }
}