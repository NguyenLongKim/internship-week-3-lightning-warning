package com.example.lightningWarning.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightningWarning.models.GetSensorsResponse
import com.example.lightningWarning.models.UserData
import com.example.lightningWarning.repositories.KhindRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel:ViewModel() {
    private val khindRepo = KhindRepository.instance
    private lateinit var userData: UserData
    private val getSensorsResponseLiveData=MutableLiveData<GetSensorsResponse>()

    fun setUserData(userData: UserData) {
        this.userData = userData
    }

    fun getSensors(){
        khindRepo.getSensors(userData.token.token,object : Callback<GetSensorsResponse> {
            override fun onResponse(
                call: Call<GetSensorsResponse>,
                response: Response<GetSensorsResponse>
            ) {
                Log.d("CC",response.toString())
                Log.d("CC",response.body().toString())
                getSensorsResponseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<GetSensorsResponse>, t: Throwable) {

            }
        })
    }
}