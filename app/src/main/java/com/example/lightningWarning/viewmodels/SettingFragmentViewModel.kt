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

class SettingFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private var signOutResponseLiveData = MutableLiveData<SignOutResponse>()
    private val errorResponseLiveData = MutableLiveData<ErrorResponse>()

    fun getSignOutResponseLiveData() = signOutResponseLiveData

    fun getErrorResponseLiveData() = errorResponseLiveData

    fun signOut(token: String) {
        viewModelScope.launch(Dispatchers.IO){
            val response = khindRepo.signOut(token)
            if (response.isSuccessful){
                signOutResponseLiveData.postValue(response.body()!!)
            }else{
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }
}