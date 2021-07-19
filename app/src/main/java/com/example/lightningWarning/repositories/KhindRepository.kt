package com.example.lightningWarning.repositories

import android.util.Log
import com.example.lightningWarning.api.KhindService
import com.example.lightningWarning.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class KhindRepository {
    companion object {
        val instance = KhindRepository()
        private const val BASE_URL = "https://khind.vinova.sg"
        private var loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private var okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        private val khindService = retrofit.create(KhindService::class.java)
    }

    fun signIn(email: String, password: String, callback: Callback<SignInResponse>) {
        khindService.signIn(email, password).enqueue(callback)
    }

    fun signOut(token: String, callback: Callback<SignOutResponse>) {
        khindService.signOut(token).enqueue(callback)
    }

    fun refreshToken(
        token: String,
        refresh_token: String,
        callback: Callback<RefreshTokenResponse>
    ) {
        khindService.refreshToken(token, refresh_token).enqueue(callback)
    }

    fun loadSensors(token: String, callback: Callback<GetSensorsResponse>) {
        khindService.loadSensors(token).enqueue(callback)
    }

    fun loadSensorDetail(
        token: String,
        sensorId: String,
        callback: Callback<GetSensorDetailResponse>
    ) {
        khindService.loadSensorDetail(token, sensorId).enqueue(callback)
    }

    fun loadSensorHistories(
        token: String,
        sensorId: String,
        callback: Callback<GetSensorHistoriesResponse>
    ) {
        khindService.loadSensorHistories(token, sensorId).enqueue(callback)
    }

    fun loadAlerts(token: String, callback: Callback<GetAlertsResponse>) {
        khindService.loadAlerts(token).enqueue(callback)
    }

    fun loadMessages(token: String, callback: Callback<GetMessagesResponse>) {
        khindService.loadMessages(token).enqueue(callback)
    }

    fun putAvatar(
        token: String,
        image: MultipartBody.Part,
        callback: Callback<PutAvatarResponse>
    ) {
        khindService.putAvatar(token, image).enqueue(callback)
    }

    fun changePassword(
        token: String,
        newPassword: String,
        passwordConfirmation: String,
        currentPassword: String,
        callback: Callback<ChangePasswordResponse>
    ) {
        khindService.changePassword(token, newPassword, passwordConfirmation, currentPassword)
            .enqueue(callback)
    }
}