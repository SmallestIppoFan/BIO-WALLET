package com.example.bio_wallet.screens.TransactionScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bio_wallet.Status
import com.example.bio_wallet.model.TransactionData
import com.example.bio_wallet.model.UserModel
import com.example.bio_wallet.model.auth.UserAuthData
import com.example.bio_wallet.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase,
        private val firebaseRepository: FirebaseRepository
    ):ViewModel() {
    private val _state = MutableStateFlow(TransactionState())
    val state: StateFlow<TransactionState> = _state

    private val channel = Channel<TransactionEvent>()
    val eventFlow = channel.receiveAsFlow()


    private val usersRef = firebaseDatabase.reference.child("Username")
    private val transactionRef = firebaseDatabase.reference.child("Transactions")

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
            usersRef.orderByChild("phone").equalTo("7$phone")
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
                                channel.send(TransactionEvent.ShowDialog(true, "Cant find wallet with this number"))
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
        firebaseRepository.getProfile(onSuccess = {
        }, onDone = {
            _state.update {
                it.copy(
                    profileInfo = UserAuthData.currentUser!!,
                    localLoadingStatus = Status.HIDE_LOADING
                )
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun confirmTransaction(amount: Int) {
        val receiverMoneyValue = state.value.senderUserData.money!!.toInt() + amount
        val senderMoneyValue = state.value.profileInfo.money!!.toInt() - amount


        val receiverUpdates = hashMapOf<String, Any>(
            "money" to receiverMoneyValue
        )

        val senderUpdates = hashMapOf<String,Any>(
            "money" to senderMoneyValue
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
            usersRef.child(state.value.senderUserUID).updateChildren(receiverUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        updateUserMoney(senderUpdates, task,amount)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUserMoney(
        senderUpdates: HashMap<String, Any>,
        task: Task<Void>,
        amount: Int,

    ) {
        val senderTransactionRef = usersRef.child(UserAuthData.UID!!).child("transactionId")
        val receiverTransactionRef = usersRef.child(state.value.senderUserUID).child("transactionId")
        val transactionKey = senderTransactionRef.push().key

        usersRef.child(UserAuthData.UID!!).updateChildren(senderUpdates).addOnCompleteListener {
            if (task.isSuccessful) {
                viewModelScope.launch {
                    _state.update {
                        it.copy(checkData = TransactionData(
                            amount = amount.toString(), commission = "0",
                            transactionId ="$transactionKey", date = getCurrentDateTimeFormatted(), receiverName  = state.value.senderUserData.name, receiverPhone = state.value.senderUserData.phone),
                        )
                    }
                    checkInTransactionHistory(amount,senderTransactionRef,receiverTransactionRef,transactionKey)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkInTransactionHistory(amount: Int,senderTransactionRef:DatabaseReference,receiverTransactionRef:DatabaseReference,transactionKey:String?) {

        val transactionData =
            mapOf(
                "amount" to amount,
                "commission" to 0,
                "transactionId" to transactionKey.toString(),
                "date" to getCurrentDateTimeFormatted(),
                "receiverName" to state.value.senderUserData.name,
                "receiverPhone" to state.value.senderUserData.phone
            )

        if (transactionKey != null) {
            val senderMap = mapOf("amount" to "-$amount",
                                "fullName" to "${state.value.senderUserData.name} ${state.value.senderUserData.surname}"
                )
            senderTransactionRef.child(transactionKey).setValue(senderMap).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val receiverMap = mapOf("amount" to "+$amount",
                        "fullName" to "${UserAuthData.currentUser!!.name} ${UserAuthData.currentUser!!.surname}"
                        )
                    receiverTransactionRef.child(transactionKey).setValue(receiverMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            viewModelScope.launch {
                                transactionRef.child(transactionKey).setValue(transactionData).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        viewModelScope.launch {
                                            channel.send(
                                                TransactionEvent.ShowCheck(
                                                    true,
                                                    state.value.checkData
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            viewModelScope.launch {
                                channel.send(TransactionEvent.ShowDialog(true, "Failed to record transaction for receiver."))
                            }
                        }
                    }
                } else {
                    viewModelScope.launch {
                        channel.send(TransactionEvent.ShowDialog(true, "Failed to record transaction for sender."))
                    }
                }
            }
        } else {
            viewModelScope.launch {
                channel.send(TransactionEvent.ShowDialog(true, "Failed to generate transaction ID."))
            }
        }
    }

}



data class TransactionState(
    val profileInfo: UserModel = UserModel(),
    val senderUserData:UserModel = UserModel(),
    val senderUserUID:String = "",
    val localLoadingStatus : Status = Status.SHOW_LOADING,
    val checkData : TransactionData = TransactionData()
)

sealed class TransactionEvent{
    data class ShowDialog(val show:Boolean,val text:String):TransactionEvent()
    data class ShowReceiver(val receiver:String):TransactionEvent()
    data class ShowCheck(val show:Boolean,val transactionData: TransactionData):TransactionEvent()

}


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTimeFormatted(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
    return currentDateTime.format(formatter)
}
