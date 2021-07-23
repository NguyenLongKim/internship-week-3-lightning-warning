package com.example.lightningWarning.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import com.example.lightningWarning.utils.ErrorUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private var signOutResponseLiveData = MutableLiveData<SignOutResponse>()
    private val loadScheduleResponseLiveData = MutableLiveData<GetScheduleResponse>()
    private val putScheduleResponseLiveData = MutableLiveData<PutScheduleResponse>()
    private val errorResponseLiveData = MutableLiveData<ErrorResponse>()

    fun getSignOutResponseLiveData() = signOutResponseLiveData

    fun getLoadScheduleResponseLiveData() = loadScheduleResponseLiveData

    fun getPutScheduleResponseLiveData() = putScheduleResponseLiveData

    fun getErrorResponseLiveData() = errorResponseLiveData

    fun loadSchedule(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.loadSchedule(token)
            if (response.isSuccessful) {
                loadScheduleResponseLiveData.postValue(response.body()!!)
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }

    fun putSchedule(token: String, newSchedule: RequestUpdateScheduleData) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.putSchedule(token, newSchedule)
            if (response.isSuccessful) {
                putScheduleResponseLiveData.postValue(response.body()!!)
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }

    fun signOut(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.signOut(token)
            if (response.isSuccessful) {
                signOutResponseLiveData.postValue(response.body()!!)
            } else {
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }
}