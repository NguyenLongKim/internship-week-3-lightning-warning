package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private var userData: UserData? = null
    private val sensorsLiveData = MutableLiveData<List<SensorData>>()
    private var selectedSensorLiveData = MutableLiveData<SensorData>()
    private var selectedSensorDetailLiveData = MutableLiveData<SensorDetailData>()
    private var signOutResponseLiveData = MutableLiveData<SignOutResponse>()
    private var tasksWaitingForNewToken = mutableSetOf<() -> Unit>()
    private var selectedSensorHistoriesLiveData = MutableLiveData<ArrayList<SensorHistory>>()
    private var messagesLiveData = MutableLiveData<ArrayList<Message>>()
    private var alertLiveData = MutableLiveData<ArrayList<Alert>>()

    fun getUserData() = userData

    fun setUserData(userData: UserData) {
        this.userData = userData
    }

    fun getSensorsLiveData() = sensorsLiveData

    fun getSelectedSensorLiveData() = selectedSensorLiveData

    fun getSelectedSensorDetailLiveData() = selectedSensorDetailLiveData

    fun getSelectedSensorHistoriesLiveData() = selectedSensorHistoriesLiveData

    fun getMessagesLiveData() = messagesLiveData

    fun getAlertsLiveData() = alertLiveData

    fun getSignOutResponseLiveData() = signOutResponseLiveData

    fun setSelectedSensor(sensor: SensorData) {
        this.selectedSensorLiveData.value = sensor
        loadSelectedSensorDetail()
    }

    fun loadSensors() {
        if (userData != null) {
            if (checkPossibleNeedRefreshToken(this::loadSensors)) {
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
                            sensorsLiveData.value = data
                            selectedSensorLiveData.value = data[0]
                            loadSelectedSensorDetail()
                            loadSelectedSensorHistory()
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
            if (checkPossibleNeedRefreshToken(this::loadSelectedSensorDetail)) {
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

    private fun loadSelectedSensorHistory(){
        val sensorId = selectedSensorLiveData.value?.id
        if (userData != null && sensorId != null) {
            if (checkPossibleNeedRefreshToken(this::loadSelectedSensorHistory)) {
                return
            }
            khindRepo.loadSensorHistory(
                userData!!.token.token,
                sensorId,
                object : Callback<GetSensorHistoriesResponse> {
                    override fun onResponse(
                        call: Call<GetSensorHistoriesResponse>,
                        response: Response<GetSensorHistoriesResponse>
                    ) {
                        val body = response.body()
                        if (body != null) {
                            selectedSensorHistoriesLiveData.value = body.data as ArrayList
                            val fakeSensorHistory = SensorHistory(
                                "time",
                                "alarm",
                                "message"
                            )
                            selectedSensorHistoriesLiveData.value?.add(fakeSensorHistory)
                        }
                    }

                    override fun onFailure(call: Call<GetSensorHistoriesResponse>, t: Throwable) {

                    }
                })
        }
    }

    fun loadMessages(){
        if (userData!=null){
            if (checkPossibleNeedRefreshToken(this::loadMessages)) {
                return
            }
            khindRepo.loadMessages(
                userData!!.token.token,
                object:Callback<GetMessagesResponse>{
                    override fun onResponse(
                        call: Call<GetMessagesResponse>,
                        response: Response<GetMessagesResponse>
                    ) {
                        val body = response.body()
                        if (body!=null){
                            messagesLiveData.value=body.data as ArrayList
                            val fakeMessage = Message(
                                "time",
                                "title",
                                "message"
                            )
                            messagesLiveData.value?.add(fakeMessage)
                        }
                    }

                    override fun onFailure(call: Call<GetMessagesResponse>, t: Throwable) {
                    }
                }
            )
        }
    }

    fun loadAlerts(){
        if (userData!=null){
            if (checkPossibleNeedRefreshToken(this::loadMessages)) {
                return
            }
            khindRepo.loadAlerts(
                userData!!.token.token,
                object:Callback<GetAlertsResponse>{
                    override fun onResponse(
                        call: Call<GetAlertsResponse>,
                        response: Response<GetAlertsResponse>
                    ) {
                        val body = response.body()
                        if (body!=null){
                            alertLiveData.value=body.data as ArrayList
                            val fakeAlert = Alert(
                                "time",
                                "title",
                                "message"
                            )
                            alertLiveData.value?.add(fakeAlert)
                        }
                    }

                    override fun onFailure(call: Call<GetAlertsResponse>, t: Throwable) {
                    }
                }
            )
        }
    }

    fun signOut() {
        if (userData != null) {
            if (checkPossibleNeedRefreshToken(this::signOut)) {
                return
            }
            khindRepo.signOut(
                userData!!.token.token,
                object : Callback<SignOutResponse> {
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

    private fun isTokenExpired(): Boolean =
        userData!!.token.expired_at * 1000 - 60000 < System.currentTimeMillis()

    private fun refreshToken() {
        if (userData != null) {
            khindRepo.refreshToken(
                userData!!.token.token,
                userData!!.token.refresh_token,
                object : Callback<RefreshTokenResponse> {
                    override fun onResponse(
                        call: Call<RefreshTokenResponse>,
                        response: Response<RefreshTokenResponse>
                    ) {
                        val body = response.body()
                        if (body?.data != null) {
                            userData!!.token = body.data.token
                            for (func in tasksWaitingForNewToken) {
                                func()
                            }
                            tasksWaitingForNewToken.clear()
                        }

                    }

                    override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {

                    }
                })
        }
    }

    private fun checkPossibleNeedRefreshToken(task:()->Unit):Boolean{
        if (isTokenExpired()){
            tasksWaitingForNewToken.add(task)
            refreshToken()
            return true
        }
        return false
    }
}