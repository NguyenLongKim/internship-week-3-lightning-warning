package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightningWarning.models.ChangePasswordResponse
import com.example.lightningWarning.models.ErrorResponse
import com.example.lightningWarning.models.PutAvatarResponse
import com.example.lightningWarning.models.SignInResponse
import com.example.lightningWarning.repositories.KhindRepository
import com.example.lightningWarning.utils.ErrorUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentViewModel : ViewModel() {
    private var khindRepo = KhindRepository.instance
    private var putAvatarResponseLiveData = MutableLiveData<PutAvatarResponse>()
    private val errorResponseLiveData = MutableLiveData<ErrorResponse>()

    fun getPutAvatarResponseLiveData() = putAvatarResponseLiveData

    fun getErrorResponseLiveData() = errorResponseLiveData

    fun putAvatar(token: String, image: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO){
            val response = khindRepo.putAvatar(token,image)
            if (response.isSuccessful){
                putAvatarResponseLiveData.postValue(response.body()!!)
            }else{
                errorResponseLiveData.postValue(ErrorUtil.parseErrorBody(response.errorBody()!!))
            }
        }
    }
}