package com.example.bio_wallet.model

data class Data(
    val collections: List<Collection>,
    val name: String,
    val personId: String,
    val photos: List<Photo>
)