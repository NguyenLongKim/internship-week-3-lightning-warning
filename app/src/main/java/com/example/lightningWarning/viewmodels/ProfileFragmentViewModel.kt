package com.example.lightningWarning.viewmodels

import android.util.Log
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

class ProfileFragmentViewModel : ViewModel() {
    private var khindRepo = KhindRepository.instance
    private var putAvatarResponseLiveData = MutableLiveData<PutAvatarResponse>()
    private var changePasswordResponseLiveData = MutableLiveData<ChangePasswordResponse>()
    private var reSignInResponseLiveData = MutableLiveData<SignInResponse>()

    fun getPutAvatarResponseLiveData() = putAvatarResponseLiveData

    fun getChangePasswordResponseLiveData() = changePasswordResponseLiveData

    fun getReSignInResponseLiveData() = reSignInResponseLiveData

    fun putAvatar(token: String, image: MultipartBody.Part) {
        khindRepo.putAvatar(
            token,
            image,
            object : Callback<PutAvatarResponse> {
                override fun onResponse(
                    call: Call<PutAvatarResponse>,
                    response: Response<PutAvatarResponse>
                ) {
                    putAvatarResponseLiveData.value = response.body()
                }

                override fun onFailure(call: Call<PutAvatarResponse>, t: Throwable) {
                }
            }
        )
    }

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
                    Log.d("CC", response.toString())
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
            object : Callback<SignInResponse>{
                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    Log.d("CC",response.toString())
                    reSignInResponseLiveData.value = response.body()
                }

                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {

                }

            }
        )
    }
}