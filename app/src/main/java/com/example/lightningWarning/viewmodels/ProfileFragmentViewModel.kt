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

    fun getPutAvatarResponseLiveData() = putAvatarResponseLiveData

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
}