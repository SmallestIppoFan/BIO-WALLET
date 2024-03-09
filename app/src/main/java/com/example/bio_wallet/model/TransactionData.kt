package com.example.bio_wallet.model

data class TransactionData(
    val amount:String? =null,
    val commission: String? =null,
    val transactionId: String? = null,
    val date: String? = null,
    val receiverName:String? =null,
    val receiverPhone:String? =null
)
