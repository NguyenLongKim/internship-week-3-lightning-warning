package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import com.example.lightningWarning.utils.ErrorUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private val sensorsLiveData = MutableLiveData(ArrayList<SensorData>())
    private val selectedSensorLiveData = MutableLiveData<SensorData>()
    private val selectedSensorDetailLiveData = MutableLiveData<SensorDetailData>()
    private val selectedSensorHistoriesLiveData = MutableLiveData(ArrayList<SensorHistory>())
    private val errorResponseLiveData = MutableLiveData<ErrorResponse>()


    fun getSensorsLiveData() = sensorsLiveData

    fun getSelectedSensorLiveData() = selectedSensorLiveData

    fun getSelectedSensorDetailLiveData() = selectedSensorDetailLiveData

    fun getSelectedSensorHistoriesLiveData() = selectedSensorHistoriesLiveData

    fun getErrorResponseLiveData() = errorResponseLiveData

    fun setSelectedSensor(token: String, sensor: SensorData) {
        this.selectedSensorLiveData.value = sensor
        loadSelectedSensorDetail(token, sensor.id)
        loadSelectedSensorHistories(token, sensor.id)
    }

    fun loadSensors(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.loadSensors(token)
            if (response.isSuccessful) {
                val data = response.body()!!.data
                sensorsLiveData.postValue(data as ArrayList)
                selectedSensorLiveData.postValue(data[0])
                loadSelectedSensorDetail(token, data[0].id)
                loadSelectedSensorHistories(token, data[0].id)
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }

    private fun loadSelectedSensorDetail(token: String, sensorId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.loadSensorDetail(token, sensorId)
            if (response.isSuccessful) {
                selectedSensorDetailLiveData.postValue(response.body()!!.data)
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }

    private fun loadSelectedSensorHistories(token: String, sensorId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.loadSensorHistories(token, sensorId)
            if (response.isSuccessful) {
                val fakeSensorHistory = SensorHistory(
                    "04 Sep, 11:00AM",
                    "lightning detected",
                    "Small lightning detected"
                )
                val tmpHistories = response.body()!!.data as ArrayList
                tmpHistories.add(fakeSensorHistory)
                selectedSensorHistoriesLiveData.apply {
                    this.value?.clear()
                    this.value?.addAll(tmpHistories)
                    this.postValue(this.value)
                }
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }
}