package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance
    private val refreshTokenResponseLiveData = MutableLiveData<RefreshTokenResponse>()

    fun getRefreshTokenResponseLiveData() = refreshTokenResponseLiveData

    private fun refreshToken(token: String, refresh_token: String) {
        khindRepo.refreshToken(
            token,
            refresh_token,
            object : Callback<RefreshTokenResponse> {
                override fun onResponse(
                    call: Call<RefreshTokenResponse>,
                    response: Response<RefreshTokenResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        refreshTokenResponseLiveData.value = body
                    }

                }

                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {

                }
            }
        )
    }
}