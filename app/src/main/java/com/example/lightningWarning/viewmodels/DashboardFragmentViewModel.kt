package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private val sensorsLiveData = MutableLiveData(ArrayList<SensorData>())
    private var selectedSensorLiveData = MutableLiveData<SensorData>()
    private var selectedSensorDetailLiveData = MutableLiveData<SensorDetailData>()
    private var selectedSensorHistoriesLiveData = MutableLiveData(ArrayList<SensorHistory>())


    fun getSensorsLiveData() = sensorsLiveData

    fun getSelectedSensorLiveData() = selectedSensorLiveData

    fun getSelectedSensorDetailLiveData() = selectedSensorDetailLiveData

    fun getSelectedSensorHistoriesLiveData() = selectedSensorHistoriesLiveData

    fun setSelectedSensor(token: String, sensor: SensorData) {
        this.selectedSensorLiveData.value = sensor
        loadSelectedSensorDetail(token, sensor.id)
        loadSelectedSensorHistories(token, sensor.id)
    }

    fun loadSensors(token: String) {
        khindRepo.loadSensors(
            token,
            object : Callback<GetSensorsResponse> {
                override fun onResponse(
                    call: Call<GetSensorsResponse>,
                    response: Response<GetSensorsResponse>
                ) {
                    val data = response.body()?.data
                    if (data != null && data.isNotEmpty()) {
                        sensorsLiveData.apply {
                            this.value = data as ArrayList
                            this.value = this.value
                        }
                        selectedSensorLiveData.value = data[0]
                        loadSelectedSensorDetail(token, data[0].id)
                        loadSelectedSensorHistories(token, data[0].id)
                    }
                }

                override fun onFailure(call: Call<GetSensorsResponse>, t: Throwable) {

                }
            }
        )
    }

    private fun loadSelectedSensorDetail(token: String, sensorId: String) {
        khindRepo.loadSensorDetail(
            token,
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

    private fun loadSelectedSensorHistories(token: String, sensorId: String) {
        khindRepo.loadSensorHistories(
            token,
            sensorId,
            object : Callback<GetSensorHistoriesResponse> {
                override fun onResponse(
                    call: Call<GetSensorHistoriesResponse>,
                    response: Response<GetSensorHistoriesResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        val fakeSensorHistory = SensorHistory(
                            "04 Sep, 11:00AM",
                            "lightning detected",
                            "Small lightning detected"
                        )
                        selectedSensorHistoriesLiveData.apply {
                            val tmp = body.data as ArrayList
                            tmp.add(fakeSensorHistory)
                            this.value?.clear()
                            this.value?.addAll(tmp)
                            this.value = this.value
                        }
                    }
                }

                override fun onFailure(call: Call<GetSensorHistoriesResponse>, t: Throwable) {

                }
            }
        )
    }
}