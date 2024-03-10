package com.example.bio_wallet.di

import com.example.bio_wallet.api.FaceAuthApi
import com.example.bio_wallet.commans.Constants
import com.example.bio_wallet.repository.FirebaseRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideRepository():FirebaseRepository = FirebaseRepository(provideFirebaseDatabase(), provideFirebaseAuth())

    @Singleton
    @Provides
    fun provideRetrofit(): FaceAuthApi {
        val instance: FaceAuthApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(FaceAuthApi::class.java)
        }
        return instance
    }

}