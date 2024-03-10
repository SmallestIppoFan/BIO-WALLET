package com.example.bio_wallet.repository

import android.util.Log
import com.example.bio_wallet.Status
import com.example.bio_wallet.model.MainScreenTransactionData
import com.example.bio_wallet.model.TransactionData
import com.example.bio_wallet.model.auth.UserAuthData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class FirebaseRepository @Inject constructor(private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
    ){

    private val userRef = firebaseDatabase.reference.child("Username")

    private val transactionRef = firebaseDatabase.reference.child("Transactions")




    fun createUser(name:String,
                   surname:String,
                   money:Int,
                   phone:String,
                   faceId:String,
                   onSuccess:() ->Unit,
                   onDone: () ->Unit
                   ){
        val data = mapOf("name" to name,
                        "surname" to surname,
            "money" to money,
            "phone" to phone,
            "faceId" to faceId
            )
        firebaseDatabase.reference.child("Username").child(UserAuthData.UID!!).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful){
                Log.d("asdas123","123sad")
                onSuccess()
            }
            else{
                onDone()
                Log.d("asdas123","123sad123")
            }
        }

    }
    fun getProfile(onSuccess:() ->Unit,onDone:() -> Unit){
        userRef.child(UserAuthData.UID!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val response = dataSnapshot.value
                UserAuthData.currentUser = UserAuthData.transformToUserModel(response.toString())
                onSuccess()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("asdsadsad", error.details)
            }
        })
        onDone()
    }

    fun getProfileTransaction(onSuccess:(List<MainScreenTransactionData>) ->Unit,onDone:() -> Unit) {
        userRef.child(UserAuthData.UID!!).child("transactionId").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    onSuccess(parseTransactions(snapshot.value.toString()))
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        )
        onDone()
    }
    fun getTransactionDataById(id:String,response:(TransactionData) -> Unit){
        transactionRef.child(id).addValueEventListener(object  :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                response(parseTransactionData(snapshot.value.toString()))
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun parseTransactions(response: String): List<MainScreenTransactionData> {
        return response.trimStart('{').trimEnd('}').split("}, ")
            .map { it.trim() }
            .map {
                val (id, data) = it.trimEnd('}').split("={")
                val properties = data.split(", ").associate {
                    val (key, value) = it.split("=")
                    key to value
                }
                val amount = properties["amount"]!!.toString()
                val fullName = properties["fullName"]!!
                MainScreenTransactionData(id, amount, fullName)
            }
    }

    fun parseTransactionData(response: String): TransactionData {
        val cleanResponse = response.trimStart('{').trimEnd('}')
        val keyValuePairs = cleanResponse.split(", ").associate {
            val (key, value) = it.split("=")
            key to value
        }
        return TransactionData(
            amount = keyValuePairs["amount"],
            commission = keyValuePairs["commission"],
            transactionId = keyValuePairs["transactionId"],
            date = keyValuePairs["date"],
            receiverName = keyValuePairs["receiverName"],
            receiverPhone = keyValuePairs["receiverPhone"]
        )
    }
}