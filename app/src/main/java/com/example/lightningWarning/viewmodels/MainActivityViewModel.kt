package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.jvm.internal.FunctionReference

class MainActivityViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private var userData: UserData? = null
    private val sensorsLiveData = MutableLiveData<List<SensorData>>()
    private var selectedSensorLiveData = MutableLiveData<SensorData>()
    private var selectedSensorDetailLiveData = MutableLiveData<SensorDetailData>()
    private var signOutResponseLiveData = MutableLiveData<SignOutResponse>()
    private var functionsWaitingForNewToken = mutableListOf<()->Unit>()


    fun getUserData() = userData

    fun setUserData(userData: UserData) {
        this.userData = userData
    }

    fun getSensorsLiveData() = sensorsLiveData

    fun getSelectedSensorLiveData() = selectedSensorLiveData

    fun getSelectedSensorDetailLiveData() = selectedSensorDetailLiveData

    fun getSignOutResponseLiveData() = signOutResponseLiveData

    fun setSelectedSensor(sensor:SensorData){
        this.selectedSensorLiveData.value = sensor
        loadSelectedSensorDetail()
    }

    fun loadSensors() {
        if (userData != null) {
            if (isTokenExpired()){
                functionsWaitingForNewToken.add(this::loadSensors)
                refreshToken()
                return
            }
            khindRepo.loadSensors(
                userData!!.token.token,
                object : Callback<GetSensorsResponse> {
                    override fun onResponse(
                        call: Call<GetSensorsResponse>,
                        response: Response<GetSensorsResponse>
                    ) {
                        val data = response.body()?.data
                        if (data != null && data.isNotEmpty()) {
                            sensorsLiveData.value= data
                            selectedSensorLiveData.value = data[0]
                            loadSelectedSensorDetail()
                        }
                    }

                    override fun onFailure(call: Call<GetSensorsResponse>, t: Throwable) {

                    }
                })
        }
    }

    private fun loadSelectedSensorDetail() {
        val sensorId = selectedSensorLiveData.value?.id
        if (userData != null && sensorId != null) {
            if (isTokenExpired()){
                functionsWaitingForNewToken.add(this::loadSelectedSensorDetail)
                refreshToken()
                return
            }
            khindRepo.loadSensorDetail(
                userData!!.token.token,
                sensorId,
                object : Callback<GetSensorDetailResponse> {
                    override fun onResponse(
                        call: Call<GetSensorDetailResponse>,
                        response: Response<GetSensorDetailResponse>
                    ) {
                        val body = response.body()
                        if (body != null) {
                            selectedSensorDetailLiveData.value = body.data
                        }
                    }

                    override fun onFailure(call: Call<GetSensorDetailResponse>, t: Throwable) {

                    }
                })
        }
    }

    fun signOut(){
        if (userData!=null) {
            if (isTokenExpired()){
                functionsWaitingForNewToken.add(this::signOut)
                refreshToken()
                return
            }
            khindRepo.signOut(
                userData!!.token.token,
                object : Callback<SignOutResponse>{
                    override fun onResponse(
                        call: Call<SignOutResponse>,
                        response: Response<SignOutResponse>
                    ) {
                        signOutResponseLiveData.value = response.body()
                    }

                    override fun onFailure(call: Call<SignOutResponse>, t: Throwable) {

                    }

                })
        }
    }

    private fun isTokenExpired():Boolean = userData!!.token.expired_at*1000 < System.currentTimeMillis()

    private fun refreshToken(){
        if (userData!=null){
            khindRepo.refreshToken(
                userData!!.token.token,
                userData!!.token.refresh_token,
                object:Callback<RefreshTokenResponse>{
                    override fun onResponse(
                        call: Call<RefreshTokenResponse>,
                        response: Response<RefreshTokenResponse>
                    ) {
                        val body = response.body()
                        if (body?.data != null){
                            userData!!.token=body.data.token
                        }
                        for (func in functionsWaitingForNewToken){
                            func()
                        }
                        functionsWaitingForNewToken.clear()
                    }

                    override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {

                    }
                })
        }
    }
}