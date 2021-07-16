package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.GetSensorsResponse
import com.example.lightningWarning.models.SignInResponse
import com.example.lightningWarning.repositories.KhindRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivityViewModel : ViewModel(){
    private val khindRepo = KhindRepository.instance
    private val signInResponseLiveData = MutableLiveData<SignInResponse>()

    fun getSignInResponseLiveData() = signInResponseLiveData

    fun signIn(email:String,password:String){
        khindRepo.signIn(email,password,object: Callback<SignInResponse>{
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                signInResponseLiveData.value=response.body()
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {

            }
        })
    }
}