package com.example.lightningWarning.viewmodels

import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.*
import com.example.lightningWarning.repositories.KhindRepository
import kotlinx.coroutines.*


class MainActivityViewModel : ViewModel() {
    private val khindRepo = KhindRepository.instance

    fun refreshToken(token: String, refresh_token: String): Token? {
        var newToken: Token? = null
        runBlocking {
            val response = khindRepo.refreshToken(token, refresh_token)
            if (response.isSuccessful) {
                newToken = response.body()!!.data.token
            }
        }
        return newToken
    }
}