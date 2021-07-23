package com.example.lightningWarning.ui.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightningWarning.models.ErrorResponse
import com.example.lightningWarning.models.SignInResponse
import com.example.lightningWarning.repositories.KhindRepository
import com.example.lightningWarning.utils.ErrorUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LogInFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private val signInResponseLiveData = MutableLiveData<SignInResponse>()
    private val errorResponseLiveData = MutableLiveData<ErrorResponse>()

    fun getSignInResponseLiveData() = signInResponseLiveData

    fun getErrorResponseLiveData() = errorResponseLiveData

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = khindRepo.signIn(email, password)
            if (response.isSuccessful) {
                signInResponseLiveData.postValue(response.body())
            } else {
                errorResponseLiveData.postValue(
                    ErrorUtil.parseErrorBody(response.errorBody()!!)
                )
            }
        }
    }
}