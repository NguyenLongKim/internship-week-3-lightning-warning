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
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private var messagesLiveData = MutableLiveData(ArrayList<Message>())
    private var alertsLiveData = MutableLiveData(ArrayList<Alert>())
    private val errorResponseLiveData = MutableLiveData<ErrorResponse>()

    fun getMessagesLiveData() = messagesLiveData

    fun getAlertsLiveData() = alertsLiveData

    fun getErrorResponseLiveData() = errorResponseLiveData

    fun loadMessages(token: String) {
        viewModelScope.launch(Dispatchers.IO){
            val response = khindRepo.loadMessages(token)
            if (response.isSuccessful){
                val fakeMessage = Message(
                    "04 Sep, 11:00AM",
                    "Message title",
                    "Message text"
                )
                val tmpMessages = response.body()!!.data as ArrayList
                tmpMessages.add(fakeMessage)
                messagesLiveData.apply {
                    this.value?.clear()
                    this.value?.addAll(tmpMessages)
                    this.postValue(this.value)
                }
            }else{
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }

    fun loadAlerts(token: String) {
        viewModelScope.launch(Dispatchers.IO){
            val response = khindRepo.loadAlerts(token)
            if (response.isSuccessful){
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
            }else{
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }
}