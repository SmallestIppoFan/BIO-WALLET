package com.example.bio_wallet.screens.MainSreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bio_wallet.model.MainScreenTransactionData
import com.example.bio_wallet.model.TransactionData
import com.example.bio_wallet.model.UserModel
import com.example.bio_wallet.model.auth.UserAuthData
import com.example.bio_wallet.repository.FirebaseRepository
import com.example.bio_wallet.screens.TransactionScreen.TransactionEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase,
                                        private val repository: FirebaseRepository):ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    private val channel = Channel<MainTransactionEvent>()
    val eventFlow = channel.receiveAsFlow()


    init {
        getProfile()
        getTransactionList()
    }


    private fun getProfile() {
        _state.update {
            it.copy(
                loading = LoadingState.Loading
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
                        loading = LoadingState.Done
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
    }

    private fun getTransactionList() {
        _state.update {
            it.copy(
                loading = LoadingState.Loading
            )
        }
        repository.getProfileTransaction(onSuccess = { transactionList ->
            _state.update {
                it.copy(
                    transactionList = transactionList
                )
            }

        }, onDone = {
            _state.update {
                it.copy(
                    loading = LoadingState.Done
                )

            }
        }
        )
    }

    fun getTransactionDataById(id: String) {
        _state.update {
            it.copy(
                loading = LoadingState.Loading
            )
        }
        repository.getTransactionDataById(id) { result ->
            viewModelScope.launch {
                channel.send(MainTransactionEvent.ShowCheck(true, transactionData = result))
                _state.update {
                    it.copy(checkData = result)
                }
            }
        }
        _state.update {
            it.copy(
                loading = LoadingState.Done
            )
        }
    }

}

data class State(
    val profileInfo:UserModel = UserModel(),
    val loading : LoadingState = LoadingState.Loading,
    val transactionList:List<MainScreenTransactionData>? = null,
    val checkData : TransactionData = TransactionData()
)




enum class LoadingState{
    Loading,
    Done
}

sealed class MainTransactionEvent{
    data class ShowCheck(val show:Boolean,val transactionData: TransactionData): MainTransactionEvent()
}
