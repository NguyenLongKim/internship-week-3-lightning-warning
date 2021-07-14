package com.example.lightningWarning.repositories

import com.example.lightningWarning.api.KhindService
import com.example.lightningWarning.models.GetSensorsResponse
import com.example.lightningWarning.models.SignInResponse
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KhindRepository {
    companion object{
        val instance = KhindRepository()
        private const val BASE_URL = "https://khind.vinova.sg"
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        private val khindService = retrofit.create(KhindService::class.java)
    }

    fun signIn(email:String,password:String, callback:Callback<SignInResponse>){
        khindService.signIn(email,password).enqueue(callback)
    }

    fun getSensors(token:String, callback:Callback<GetSensorsResponse>){
        khindService.getSensors(token).enqueue(callback)
    }
}