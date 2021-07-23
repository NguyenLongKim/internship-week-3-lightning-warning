package com.example.lightningWarning.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import com.example.lightningWarning.utils.ErrorUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotificationFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private var loadMessagesResponseLiveData = MutableLiveData<GetMessagesResponse>()
    private var alertsLiveData = MutableLiveData(ArrayList<Alert>())
    private val errorResponseLiveData = MutableLiveData<ErrorResponse>()
    private val isLoadingMessagesLiveData = MutableLiveData<Boolean>()

    fun getLoadMessagesResponseLiveData() = loadMessagesResponseLiveData

    fun getAlertsLiveData() = alertsLiveData

    fun getErrorResponseLiveData() = errorResponseLiveData

    fun getIsLoadingMessagesLiveData() = isLoadingMessagesLiveData

    fun loadMessages(token: String, page: Int) {
        isLoadingMessagesLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.loadMessages(token, page)
            if (response.isSuccessful) {
                loadMessagesResponseLiveData.postValue(response.body())
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
            isLoadingMessagesLiveData.postValue(false)
        }
    }

    fun loadAlerts(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.loadAlerts(token)
            if (response.isSuccessful) {
                val fakeAlert = Alert(
                    "04 Sep, 11:00AM",
                    "Alert title",
                    "Alert text"
                )
                val tmpAlerts = response.body()!!.data as ArrayList
                tmpAlerts.add(fakeAlert)
                alertsLiveData.apply {
                    this.value?.clear()
                    this.value?.addAll(tmpAlerts)
                    this.postValue(this.value)
                }
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }
}