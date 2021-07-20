package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private var messagesLiveData = MutableLiveData(ArrayList<Message>())
    private var alertsLiveData = MutableLiveData(ArrayList<Alert>())

    fun getMessagesLiveData() = messagesLiveData

    fun getAlertsLiveData() = alertsLiveData

    fun loadMessages(token: String) {
        khindRepo.loadMessages(
            token,
            object : Callback<GetMessagesResponse> {
                override fun onResponse(
                    call: Call<GetMessagesResponse>,
                    response: Response<GetMessagesResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        val fakeMessage = Message(
                            "04 Sep, 11:00AM",
                            "Message title",
                            "Message text"
                        )
                        messagesLiveData.apply {
                            val tmp = body.data as ArrayList
                            tmp.add(fakeMessage)
                            this.value?.clear()
                            this.value?.addAll(tmp)
                            this.value = this.value
                        }
                    }
                }

                override fun onFailure(call: Call<GetMessagesResponse>, t: Throwable) {
                }
            }
        )
    }

    fun loadAlerts(token: String) {
        khindRepo.loadAlerts(
            token,
            object : Callback<GetAlertsResponse> {
                override fun onResponse(
                    call: Call<GetAlertsResponse>,
                    response: Response<GetAlertsResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        val fakeAlert = Alert(
                            "04 Sep, 11:00AM",
                            "Alert title",
                            "Alert text"
                        )
                        alertsLiveData.apply {
                            val tmp = body.data as ArrayList
                            tmp.add(fakeAlert)
                            this.value?.clear()
                            this.value?.addAll(tmp)
                            this.value = this.value
                        }
                    }
                }

                override fun onFailure(call: Call<GetAlertsResponse>, t: Throwable) {
                }
            }
        )
    }
}