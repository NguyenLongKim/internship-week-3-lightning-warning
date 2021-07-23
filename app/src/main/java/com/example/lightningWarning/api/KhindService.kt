package com.example.lightningWarning.api

import com.example.lightningWarning.models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface KhindService {
    // sign in
    @FormUrlEncoded
    @POST("/auth/sign_in")
    suspend fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<SignInResponse>

    // refresh token
    @FormUrlEncoded
    @POST("/auth/refresh_token")
    suspend fun refreshToken(
        @Header("X-Refresh-Token") token: String,
        @Field("refresh_token") refreshToken: String
    ): Response<RefreshTokenResponse>


    // sign out
    @DELETE("/auth/sign_out")
    suspend fun signOut(@Header("X-Http-Token") token: String): Response<SignOutResponse>
    ////////////////

    // load sensors
    @GET("/sensors")
    suspend fun loadSensors(@Header("X-Http-Token") token: String): Response<GetSensorsResponse>

    // sensor detail
    @GET("/sensors/{id}")
    suspend fun loadSensorDetail(
        @Header("X-Http-Token") token: String,
        @Path("id") sensorId: String
    ): Response<GetSensorDetailResponse>

    // sensor histories
    @GET("/sensors/{id}/histories")
    suspend fun loadSensorHistories(
        @Header("X-Http-Token") token: String,
        @Path("id") sensorId: String
    ): Response<GetSensorHistoriesResponse>

    // alerts
    @GET("/lightning_alerts")
    suspend fun loadAlerts(@Header("X-Http-Token") token: String): Response<GetAlertsResponse>

    // messages
    @GET("/messages")
    suspend fun loadMessages(
        @Header("X-Http-Token") token: String,
        @Query("page") page: Int
    ): Response<GetMessagesResponse>

    // put avatar
    @Multipart
    @PUT("/users/avatar")
    suspend fun putAvatar(
        @Header("X-Http-Token") token: String,
        @Part image: MultipartBody.Part
    ): Response<PutAvatarResponse>

    // change password
    @FormUrlEncoded
    @PUT("/users/change_password")
    suspend fun changePassword(
        @Header("X-Http-Token") token: String,
        @Field("password") newPassword: String,
        @Field("password_confirmation") passwordConfirmation: String,
        @Field("current_password") currentPassword: String,
    ): Response<ChangePasswordResponse>

    // get schedule
    @GET("/settings/schedule")
    suspend fun loadSchedule(@Header("X-Http-Token") token: String): Response<GetScheduleResponse>

    // put schedule
    @PUT("/settings/schedule")
    suspend fun putSchedule(
        @Header("X-Http-Token") token: String,
        @Body body: RequestUpdateScheduleData
    ): Response<PutScheduleResponse>
}