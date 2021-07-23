package com.example.lightningWarning.repositories

import com.example.lightningWarning.api.KhindService
import com.example.lightningWarning.models.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class KhindRepository {
    companion object {
        val instance = KhindRepository()
        private const val BASE_URL = "https://khind.vinova.sg"
        private var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private var okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        private val khindService = retrofit.create(KhindService::class.java)
    }

    suspend fun signIn(email: String, password: String) = khindService.signIn(email, password)

    suspend fun signOut(token: String) = khindService.signOut(token)

    suspend fun refreshToken(
        token: String,
        refresh_token: String,
    ) = khindService.refreshToken(token, refresh_token)

    suspend fun loadSensors(token: String) = khindService.loadSensors(token)

    suspend fun loadSensorDetail(
        token: String,
        sensorId: String
    ) = khindService.loadSensorDetail(token, sensorId)


    suspend fun loadSensorHistories(
        token: String,
        sensorId: String
    ) = khindService.loadSensorHistories(token, sensorId)


    suspend fun loadAlerts(token: String) = khindService.loadAlerts(token)

    suspend fun loadMessages(token: String, page: Int) = khindService.loadMessages(token, page)

    suspend fun putAvatar(
        token: String,
        image: MultipartBody.Part
    ) = khindService.putAvatar(token, image)

    suspend fun changePassword(
        token: String,
        newPassword: String,
        passwordConfirmation: String,
        currentPassword: String,
    ) = khindService.changePassword(token, newPassword, passwordConfirmation, currentPassword)

    suspend fun loadSchedule(token: String) = khindService.loadSchedule(token)

    suspend fun putSchedule(
        token: String,
        body: RequestUpdateScheduleData
    ) = khindService.putSchedule(token, body)

}