package com.example.bio_wallet.screens.FaceScreen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bio_wallet.model.auth.UserAuthData
import com.example.bio_wallet.repository.FirebaseRepository
import com.example.bio_wallet.screens.RegisterScreen.RegistrationEvents
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class FaceCheckViewModel @Inject constructor(private val repository: FirebaseRepository,
    private val firebaseAuth: FirebaseAuth

    ):ViewModel() {

    private val channel = Channel<FaceAuthEvents>()
    val channelFlow = channel.receiveAsFlow()



    fun createPhoto(savedUri: Uri) {
        viewModelScope.launch {
            channel.send(FaceAuthEvents.ShowLoading(true))
        }
        val storageRef = Firebase.storage.reference
        val fileName = savedUri.lastPathSegment ?: "image_${System.currentTimeMillis()}"
        val photoRef = storageRef.child("photos/$fileName")



        photoRef.putFile(savedUri).addOnSuccessListener {
            photoRef.downloadUrl.addOnSuccessListener { downloadUri ->
                repository.getProfile(onSuccess = {
                    Log.d("ASdasd123123",UserAuthData.currentUser!!.faceId.toString())
                    uploadImageUrls(downloadUri.toString() , UserAuthData.currentUser!!.faceId.toString())
                }, onDone = {})
            }
        }.addOnFailureListener {

        }.addOnCompleteListener {

        }
    }


    private fun uploadImageUrls(imageUrl1: String, imageUrl2: String) {
        val imageUrls = ImageUrls(imageUrl1, imageUrl2)

        RetrofitClient.instance.uploadImageUrls(imageUrls).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseString = response.body()!!.string()
                    Log.d("Response", responseString)
                    if (responseString.contains("\"samePerson\": true")) {
                        viewModelScope.launch {
                            channel.send(FaceAuthEvents.Navigate)
                        }
                    } else if (responseString.contains("\"samePerson\": false")) {
                        viewModelScope.launch {
                            channel.send(FaceAuthEvents.ShowLoading(false))
                        }

                    } else {
                        Log.d("Result", "Unexpected response format")
                    }
                } else {
                    Log.e("ErrorResponse", "Response was not successful")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("NetworkError", t.message.toString())
            }
        })
    }

        fun signOut(){
            firebaseAuth.signOut()
        }



}
interface MyBackendService {
    @POST("authenticate")
    fun uploadImageUrls(@Body imageUrls: ImageUrls): Call<ResponseBody>
}
object RetrofitClient {
    private const val BASE_URL = "http://192.168.8.6:5000/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: MyBackendService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyBackendService::class.java)
    }
}

data class ImageUrls(
    val image1: String,
    val image2: String
)


sealed class FaceAuthEvents{
    data class ShowLoading(var show:Boolean):FaceAuthEvents()
    object Navigate : FaceAuthEvents()
}
