package com.example.lightningWarning.api

import com.example.lightningWarning.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface KhindService {
    // sign in
    @FormUrlEncoded
    @POST("/auth/sign_in")
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignInResponse>

    // refresh token
    @FormUrlEncoded
    @POST("/auth/refresh_token")
    fun refreshToken(
        @Header("X-Refresh-Token") token: String,
        @Field("refresh_token") refreshToken: String
    ): Call<RefreshTokenResponse>


    // sign out
    @DELETE("/auth/sign_out")
    fun signOut(@Header("X-Http-Token") token: String): Call<SignOutResponse>
    ////////////////

    // sensors
    @GET("/sensors")
    fun loadSensors(@Header("X-Http-Token") token: String): Call<GetSensorsResponse>

    // sensor detail
    @GET("/sensors/{id}")
    fun loadSensorDetail(
        @Header("X-Http-Token") token: String,
        @Path("id") sensorId: String
    ): Call<GetSensorDetailResponse>

    // sensor histories
    @GET("/sensors/{id}/histories")
    fun loadSensorHistories(
        @Header("X-Http-Token") token: String,
        @Path("id") sensorId: String
    ): Call<GetSensorHistoriesResponse>

    // alerts
    @GET("/lightning_alerts")
    fun loadAlerts(@Header("X-Http-Token") token: String): Call<GetAlertsResponse>

    // messages
    @GET("/messages")
    fun loadMessages(@Header("X-Http-Token") token: String): Call<GetMessagesResponse>

    // put avatar
    @Multipart
    @PUT("/users/avatar")
    fun putAvatar(
        @Header("X-Http-Token") token: String,
        @Part image: MultipartBody.Part
    ): Call<PutAvatarResponse>

    // change password
    @FormUrlEncoded
    @PUT("/users/change_password")
    fun changePassword(
        @Header("X-Http-Token") token: String,
        @Field("password") newPassword: String,
        @Field("password_confirmation") passwordConfirmation: String,
        @Field("current_password") currentPassword: String,
    ): Call<ChangePasswordResponse>
}