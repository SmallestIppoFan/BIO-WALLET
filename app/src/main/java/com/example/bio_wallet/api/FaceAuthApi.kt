package com.example.bio_wallet.api

import com.example.bio_wallet.commans.Constants.api_key
import com.example.bio_wallet.model.AddFaceResponce
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FaceAuthApi {
    @POST("facepp/v3/compare")
    fun compareFaces(
        @Body requestBody: FaceComparisonRequest
    ): Call<FaceComparisonResponse>

    data class FaceComparisonResponse(
        val time_used: Int,
        val confidence: Float,
        val thresholds: Thresholds,
        val request_id: String
    )

    data class Thresholds(
        val `1e-3`: Float,
        val `1e-4`: Float,
        val `1e-5`: Float
    )
    data class FaceComparisonRequest(
        val api_key: String,
        val api_secret: String,
        val image_url1: String, // You may need to change the type depending on how you intend to encode the images
        val image_url2: String  // As above, consider the actual format you'll use for the images
    )
}


