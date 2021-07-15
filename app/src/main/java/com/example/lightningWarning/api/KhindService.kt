package com.example.lightningWarning.api

import com.example.lightningWarning.models.GetSensorDetailResponse
import com.example.lightningWarning.models.GetSensorsResponse
import com.example.lightningWarning.models.SignInResponse
import retrofit2.Call
import retrofit2.http.*

interface KhindService {
    @FormUrlEncoded
    @POST("/auth/sign_in")
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignInResponse>

    @GET("/sensors")
    fun loadSensors(@Header("X-Http-Token") token: String): Call<GetSensorsResponse>

    @GET("/sensors/{id}")
    fun loadSensorDetail(
        @Header("X-Http-Token") token: String,
        @Path("id") sensorId:String
    ): Call<GetSensorDetailResponse>
}