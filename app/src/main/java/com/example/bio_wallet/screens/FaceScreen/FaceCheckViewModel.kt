package com.example.bio_wallet.screens.FaceScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bio_wallet.api.FaceAuthApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel
class FaceCheckViewModel @Inject constructor(private val api: FaceAuthApi):ViewModel() {
    init {
        compareFaces()
    }
    private fun compareFaces(){
        Log.d("asdkasdklasd","start")
        api.compareFaces(FaceAuthApi.CompareFacesRequest("https://firebasestorage.googleapis.com/v0/b/biowallet-46e71.appspot.com/o/photos%2F2024-03-10-16-17-49-280.jpg?alt=media&token=9edc42ba-93d9-43db-bb8c-fb0c59ff46d0",
            "https://firebasestorage.googleapis.com/v0/b/biowallet-46e71.appspot.com/o/photos%2F2024-03-10-16-17-49-280.jpg?alt=media&token=9edc42ba-93d9-43db-bb8c-fb0c59ff46d0")
        ).enqueue(object :retrofit2.Callback<FaceAuthApi.CompareFacesResponse> {
            override fun onResponse(
                call: Call<FaceAuthApi.CompareFacesResponse>,
                response: Response<FaceAuthApi.CompareFacesResponse>
            ) {
                if (response.isSuccessful){
                    Log.d("asdkasdklasd","Same Person")
                }
            }

            override fun onFailure(call: Call<FaceAuthApi.CompareFacesResponse>, t: Throwable) {
                Log.d("asdkasdklasd",t.toString())
            }

        })
    }
}