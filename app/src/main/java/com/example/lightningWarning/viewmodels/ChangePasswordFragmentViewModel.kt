package com.example.lightningWarning.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.ChangePasswordResponse
import com.example.lightningWarning.models.PutAvatarResponse
import com.example.lightningWarning.models.SignInResponse
import com.example.lightningWarning.repositories.KhindRepository
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordFragmentViewModel : ViewModel() {
    private var khindRepo = KhindRepository.instance
    private var changePasswordResponseLiveData = MutableLiveData<ChangePasswordResponse>()
    private var reSignInResponseLiveData = MutableLiveData<SignInResponse>()

    fun getChangePasswordResponseLiveData() = changePasswordResponseLiveData

    fun getReSignInResponseLiveData() = reSignInResponseLiveData

    fun changePassword(
        token: String,
        newPassword: String,
        passwordConfirmation: String,
        currentPassword: String
    ) {
        khindRepo.changePassword(
            token,
            newPassword,
            passwordConfirmation,
            currentPassword,
            object : Callback<ChangePasswordResponse> {
                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    changePasswordResponseLiveData.value = response.body()
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {

                }
            }
        )
    }

    fun reSignIn(email:String,password:String){
        khindRepo.signIn(
            email,
            password,
            object : Callback<SignInResponse> {
                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    reSignInResponseLiveData.value = response.body()
                }

                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {

                }

            }
        )
    }
}