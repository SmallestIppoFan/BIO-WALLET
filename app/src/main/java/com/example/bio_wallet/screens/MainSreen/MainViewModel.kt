package com.example.bio_wallet.screens.MainSreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bio_wallet.model.UserModel
import com.example.bio_wallet.model.auth.UserAuthData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase):ViewModel() {

    private val _state = MutableStateFlow(State())
    val state : StateFlow<State> = _state

    init {
        getProfile()
    }
    private fun getProfile(){
        _state.update {
            it.copy(
                loading = LoadingState.Loading
            )
        }
        val userRef = firebaseDatabase.reference.child("Username").child(UserAuthData.UID!!)
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val response = dataSnapshot.value
                UserAuthData.currentUser = UserAuthData.transformToUserModel(response.toString())
                _state.update {
                    it.copy(
                        profileInfo = UserAuthData.currentUser!!,
                        loading = LoadingState.Done
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
    }
}

data class State(
    val profileInfo:UserModel = UserModel(),
    val loading : LoadingState = LoadingState.Loading
)




enum class LoadingState{
    Loading,
    Done
}