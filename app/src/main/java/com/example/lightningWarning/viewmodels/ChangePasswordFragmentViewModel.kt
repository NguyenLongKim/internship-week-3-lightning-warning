package com.example.lightningWarning.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightningWarning.models.ChangePasswordResponse
import com.example.lightningWarning.models.ErrorResponse
import com.example.lightningWarning.models.SignInResponse
import com.example.lightningWarning.repositories.KhindRepository
import com.example.lightningWarning.utils.ErrorUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChangePasswordFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private val changePasswordResponseLiveData = MutableLiveData<ChangePasswordResponse>()
    private val reSignInResponseLiveData = MutableLiveData<SignInResponse>()
    private val errorResponseLiveData = MutableLiveData<ErrorResponse>()

    fun getChangePasswordResponseLiveData() = changePasswordResponseLiveData

    fun getReSignInResponseLiveData() = reSignInResponseLiveData

    fun getErrorResponseLiveData() = errorResponseLiveData

    fun changePassword(
        token: String,
        newPassword: String,
        passwordConfirmation: String,
        currentPassword: String
    ) {
        viewModelScope.launch(Dispatchers.IO){
            val response = khindRepo.changePassword(
                token,
                newPassword,
                passwordConfirmation,
                currentPassword
            )
            if (response.isSuccessful){
                changePasswordResponseLiveData.postValue(response.body()!!)
            }else{
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }

    fun reSignIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO){
            val response = khindRepo.signIn(email,password)
            if (response.isSuccessful){
                reSignInResponseLiveData.postValue(response.body())
            }else{
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }
}