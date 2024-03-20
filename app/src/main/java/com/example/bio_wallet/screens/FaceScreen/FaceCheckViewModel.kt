package com.example.bio_wallet.screens.FaceScreen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bio_wallet.commans.Constants.api_key
import com.example.bio_wallet.commans.Constants.api_secret
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.security.auth.callback.Callback


@HiltViewModel
class FaceCheckViewModel @Inject constructor(private val luxandRepository: LuxandRepository):ViewModel() {

    init {
        uploadImageUrls("https://firebasestorage.googleapis.com/v0/b/biowallet-46e71.appspot.com/o/photos%2F2024-03-12-19-30-40-658.jpg?alt=media&token=93550a8f-c9fd-4730-bd50-08ff63aa2ad0",
            "https://firebasestorage.googleapis.com/v0/b/biowallet-46e71.appspot.com/o/photos%2F2024-03-12-19-30-40-658.jpg?alt=media&token=93550a8f-c9fd-4730-bd50-08ff63aa2ad0")
    }


    fun uploadImageUrls(imageUrl1: String, imageUrl2: String) {
        val imageUrls = ImageUrls(imageUrl1, imageUrl2)

        RetrofitClient.instance.uploadImageUrls(imageUrls).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("asdasd1231234234",response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("asdasd123123423",t.toString())
            }
        })
    }


}
interface MyBackendService {
    @POST("upload")
    fun uploadImageUrls(@Body imageUrls: ImageUrls): Call<ResponseBody>
}
object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.164:5000/"

    val instance: MyBackendService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyBackendService::class.java)
    }
}

data class ImageUrls(
    val image1: String,
    val image2: String
)