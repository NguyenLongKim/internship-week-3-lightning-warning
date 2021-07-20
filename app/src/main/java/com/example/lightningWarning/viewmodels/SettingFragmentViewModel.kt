package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingFragmentViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private var signOutResponseLiveData = MutableLiveData<SignOutResponse>()

    fun getSignOutResponseLiveData() = signOutResponseLiveData

    fun signOut(token: String) {
        khindRepo.signOut(
            token,
            object : Callback<SignOutResponse> {
                override fun onResponse(
                    call: Call<SignOutResponse>,
                    response: Response<SignOutResponse>
                ) {
                    signOutResponseLiveData.value = response.body()
                }

                override fun onFailure(call: Call<SignOutResponse>, t: Throwable) {

                }

            }
        )
    }
}