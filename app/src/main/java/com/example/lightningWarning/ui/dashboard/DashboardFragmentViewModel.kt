package com.example.lightningWarning.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import com.example.lightningWarning.utils.ErrorUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

    fun setSelectedSensor(sensor: SensorData) {
        this.selectedSensorLiveData.postValue(sensor)
    }

    fun loadSensors(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.loadSensors(token)
            if (response.isSuccessful) {
                val data = response.body()!!.data as ArrayList
                sensorsLiveData.postValue(data)
                selectedSensorLiveData.postValue(data[0])
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }

    fun loadSelectedSensorDetail(token: String, sensorId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.loadSensorDetail(token, sensorId)
            if (response.isSuccessful) {
                selectedSensorDetailLiveData.postValue(response.body()!!.data)
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }

    fun loadSelectedSensorHistories(token: String, sensorId: String) {
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