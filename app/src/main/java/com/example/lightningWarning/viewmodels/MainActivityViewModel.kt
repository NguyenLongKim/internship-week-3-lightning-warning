package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance

    fun refreshToken(token: String, refresh_token: String) : Token? {
        var newToken:Token?=null
        runBlocking {
            val response = khindRepo.refreshToken(token, refresh_token)
            if (response.isSuccessful) {
                newToken = response.body()!!.data.token
            }
        }
        return newToken
    }
}