package com.example.bio_wallet.api

import com.example.bio_wallet.commans.Constants.api_key
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FaceAuthApi {
    @POST("/photo/compare")
    @Headers("token: $api_key") // Replace with your actual API key
    fun compareFaces(
        @Body request: CompareFacesRequest
    ): Call<CompareFacesResponse>

    data class CompareFacesRequest(
        val photo1: String, // URL or identifier for the first photo
        val photo2: String  // URL or identifier for the second photo
    )

    data class CompareFacesResponse(
        val status: String,
        val match: Boolean,  // Indicates if it's the same person
        val confidence: Float // Similarity score, if available
    )
}


