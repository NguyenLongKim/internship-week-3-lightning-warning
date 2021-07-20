package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.ChangePasswordResponse
import com.example.lightningWarning.models.PutAvatarResponse
import com.example.lightningWarning.models.SignInResponse
import com.example.lightningWarning.repositories.KhindRepository
import com.google.gson.Gson
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
                    if (response.isSuccessful) {
                        changePasswordResponseLiveData.value = response.body()
                    } else {
                        val gson = Gson()
                        val errorResponse = gson.fromJson<ChangePasswordResponse>(
                            response.errorBody()!!.charStream(),
                            ChangePasswordResponse::class.java
                        )
                        changePasswordResponseLiveData.value = errorResponse
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {

                }
            }
        )
    }

    fun reSignIn(email: String, password: String) {
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