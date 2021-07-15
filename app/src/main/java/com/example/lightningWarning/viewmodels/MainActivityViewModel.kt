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
    private val getSensorsResponseLiveData = MutableLiveData<GetSensorsResponse>()
    private var selectedSensorLiveData = MutableLiveData<SensorData>()
    private var selectedSensorDetailLiveData = MutableLiveData<SensorDetailData>()

    fun getUserData() = userData

    fun setUserData(userData: UserData) {
        this.userData = userData
    }

    fun getSelectedSensorLiveData() = selectedSensorLiveData

    fun getSelectedSensorDetailLiveData() = selectedSensorDetailLiveData

    fun loadSensors() {
        if (userData != null) {
            khindRepo.loadSensors(
                userData!!.token.token,
                object : Callback<GetSensorsResponse> {
                    override fun onResponse(
                        call: Call<GetSensorsResponse>,
                        response: Response<GetSensorsResponse>
                    ) {
                        val body = response.body()
                        if (body != null) {
                            getSensorsResponseLiveData.value = body
                            selectedSensorLiveData.value = body.data[0]
                        }
                    }

                    override fun onFailure(call: Call<GetSensorsResponse>, t: Throwable) {

                    }
                })
        }
    }

    fun loadSensorDetail() {
        val sensorId = selectedSensorLiveData.value?.id
        if (userData != null && sensorId != null) {
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

                }
            )
        }
    }
}