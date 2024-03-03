package com.example.bio_wallet.screens.TransactionScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bio_wallet.Status
import com.example.bio_wallet.model.UserModel
import com.example.bio_wallet.model.auth.UserAuthData
import com.example.bio_wallet.screens.LoginOTPCode.LoginOtpViewModel
import com.example.bio_wallet.screens.MainSreen.State
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TransactionViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase):ViewModel() {
    private val _state = MutableStateFlow(TransactionState())
    val state: StateFlow<TransactionState> = _state

    private val channel = Channel<TransactionEvent>()
    val eventFlow = channel.receiveAsFlow()
    private val usersRef = firebaseDatabase.reference.child("Username")

    init {
        getProfile()
    }

    fun getAccountByPhone(phone: String) {
        _state.update {
            it.copy(
                localLoadingStatus = Status.SHOW_LOADING
            )
        }
        if (phone == UserAuthData.currentUser!!.phone) {
            viewModelScope.launch {
                channel.send(TransactionEvent.ShowDialog(true, "Cant send money to your wallet"))
            }
        } else {
            usersRef.orderByChild("phone").equalTo("+77064199279")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (childSnapshot in dataSnapshot.children) {
                                val uid = childSnapshot.key
                                val response = childSnapshot.value
                                _state.update {
                                    it.copy(
                                        senderUserData = UserAuthData.transformToUserModel(response.toString()),
                                        senderUserUID = uid.toString()
                                    )
                                }
                                viewModelScope.launch {
                                    channel.send(TransactionEvent.ShowReceiver(receiver = "${state.value.senderUserData.name} ${state.value.senderUserData.surname}"))
                                }
                            }
                        } else {
                            viewModelScope.launch {
                                channel.send(TransactionEvent.ShowDialog(true, ""))
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        println("Database error: ${databaseError.toException()}")
                    }

                })
        }
        _state.update {
            it.copy(
                localLoadingStatus = Status.HIDE_LOADING
            )
        }
    }

    private fun getProfile() {
        _state.update {
            it.copy(
                localLoadingStatus = Status.SHOW_LOADING
            )
        }
        val userRef = firebaseDatabase.reference.child("Username").child(UserAuthData.UID!!)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val response = dataSnapshot.value
                UserAuthData.currentUser = UserAuthData.transformToUserModel(response.toString())
                _state.update {
                    it.copy(
                        profileInfo = UserAuthData.currentUser!!,
                        localLoadingStatus = Status.HIDE_LOADING
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("asdsadsad", error.details)
            }
        })
    }

    fun confirmTransaction(amount: Int) {

        val newMoneyValue = state.value.senderUserData.money!!.toInt() + amount

        val userUpdates = hashMapOf<String, Any>(
            "money" to newMoneyValue
        )

        _state.update {
            it.copy(
                localLoadingStatus = Status.SHOW_LOADING
            )
        }
        if (amount > UserAuthData.currentUser!!.money!!.toInt()) {
            viewModelScope.launch {
                channel.send(TransactionEvent.ShowDialog(true, "Not enough money on wallet"))
            }
        } else {
            usersRef.child(state.value.senderUserUID).updateChildren(userUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Money updated successfully.")
                    } else {
                        println("Failed to update money: ${task.exception?.message}")
                    }
                }
        }
        _state.update {
            it.copy(
                localLoadingStatus = Status.HIDE_LOADING
            )
        }

    }
}



data class TransactionState(
    val profileInfo: UserModel = UserModel(),
    val senderUserData:UserModel = UserModel(),
    val senderUserUID:String = "",
    val localLoadingStatus : Status = Status.SHOW_LOADING
)

sealed class TransactionEvent{
    data class ShowDialog(val show:Boolean,val text:String):TransactionEvent()
    data class ShowReceiver(val receiver:String):TransactionEvent()
}
