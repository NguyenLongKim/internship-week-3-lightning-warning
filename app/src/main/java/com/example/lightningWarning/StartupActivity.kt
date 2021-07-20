package com.example.lightningWarning

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        // retrieve app shared preference
        val sharedPreference = getSharedPreferences("Khind", Context.MODE_PRIVATE)

        // get JSON string of UserData
        val jsonStringOfUserData = sharedPreference.getString(
            "jsonStringOfUserData",
            null
        )

        // check if signed in user still valid
        if (jsonStringOfUserData != null) { // user is signed in
            val gson = Gson()
            // convert JSON string to a UserData object
            val userData = gson.fromJson(
                jsonStringOfUserData,
                UserData::class.java
            )

            // check if userData is still valid
            KhindRepository.instance.loadSensors(
                userData.token.token,
                object : Callback<GetSensorsResponse> {
                    override fun onResponse(
                        call: Call<GetSensorsResponse>,
                        response: Response<GetSensorsResponse>
                    ) {
                        val body = response.body()
                        if (body?.status == true) { // token still valid
                            intentToMainActivity(userData)
                        } else { // token is expired
                            intentToSignInActivity()
                        }
                    }

                    override fun onFailure(call: Call<GetSensorsResponse>, t: Throwable) {
                    }

                }
            )
        } else { // user hasn't signed in
            intentToSignInActivity()
        }
    }


    private fun intentToMainActivity(userData: UserData) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userData", userData)
        startActivity(intent)
        finish()
    }

    private fun intentToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}