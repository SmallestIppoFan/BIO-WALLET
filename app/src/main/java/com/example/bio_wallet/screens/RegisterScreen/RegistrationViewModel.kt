package com.example.bio_wallet.screens.RegisterScreen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bio_wallet.repository.FirebaseRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: FirebaseRepository):ViewModel() {

    private val channel = Channel<RegistrationEvents>()
    val channelFlow = channel.receiveAsFlow()
    private fun createUser(
        name: String,
        surname: String,
        money: Int,
        phone: String,
        faceId: String,
        onDone:() -> Unit
    ) {
        repository.createUser(name = name,
            surname = surname,
            money = money,
            phone = phone,
            faceId = faceId,
            onSuccess = {
                onDone(

                )
            },
            onDone = {
                onDone()
            }
        )
    }


    fun createPhoto(savedUri: Uri, name:String,surname: String,money: Int,phone: String) {
        viewModelScope.launch {
            channel.send(RegistrationEvents.ShowLoading(true))
        }
        val storageRef = Firebase.storage.reference
        val fileName = savedUri.lastPathSegment ?: "image_${System.currentTimeMillis()}"
        val photoRef = storageRef.child("photos/$fileName")

        photoRef.putFile(savedUri).addOnSuccessListener {
            photoRef.downloadUrl.addOnSuccessListener { downloadUri ->
                savePhotoUrlToFirestore(downloadUri.toString(),name,surname,money,phone)
            }
            }.addOnFailureListener {
            }.addOnCompleteListener {
        }
    }

    private fun savePhotoUrlToFirestore(photoUrl: String, name:String,surname: String,money: Int,phone: String) {
        createUser(name,surname,money,phone,photoUrl){
            viewModelScope.launch {
                channel.send(RegistrationEvents.ShowLoading(false))
                channel.send(RegistrationEvents.Navigate)
            }
        }
    }

    private fun addFaceToDb(){

    }


}
sealed class RegistrationEvents{
    data class ShowLoading(var show:Boolean):RegistrationEvents()
    object Navigate : RegistrationEvents()
}



