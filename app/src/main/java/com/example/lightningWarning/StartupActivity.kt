package com.example.lightningWarning

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lightningWarning.models.GetSensorsResponse
import com.example.lightningWarning.models.UserData
import com.example.lightningWarning.repositories.KhindRepository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        val shared = getSharedPreferences("Khind", Context.MODE_PRIVATE)
        val jsonStringOfUserData = shared.getString("jsonStringOfUserData",null)
        if (jsonStringOfUserData!=null) {
            val gson = Gson()
            val userData = gson.fromJson(
                jsonStringOfUserData,
                UserData::class.java
            )
            KhindRepository.instance.loadSensors(
                userData.token.token,
                object: Callback<GetSensorsResponse> {
                    override fun onResponse(
                        call: Call<GetSensorsResponse>,
                        response: Response<GetSensorsResponse>
                    ) {
                        val body = response.body()
                        if (body?.status==true){
                            intentToMainActivity(userData)
                        }
                        else{
                            intentToSignActivity()
                        }
                    }

                    override fun onFailure(call: Call<GetSensorsResponse>, t: Throwable) {
                        intentToSignActivity()
                    }

                }
            )
        }else{
            intentToSignActivity()
        }
    }

    private fun intentToMainActivity(userData: UserData){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("userData",userData)
        startActivity(intent)
        finish()
    }

    private fun intentToSignActivity(){
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}