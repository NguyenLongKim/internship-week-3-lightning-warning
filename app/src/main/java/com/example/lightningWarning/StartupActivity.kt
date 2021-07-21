package com.example.lightningWarning

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.lightningWarning.models.GetSensorsResponse
import com.example.lightningWarning.models.UserData
import com.example.lightningWarning.repositories.KhindRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            // if so, intent to MainActivity with this userData
            // otherwise, intent to SignInActivity
            lifecycleScope.launch(Dispatchers.IO){
                val response = KhindRepository.instance.loadSensors(userData.token.token)
                if (response.isSuccessful){
                    intentToMainActivity(userData)
                }else{
                    intentToSignInActivity()
                }
            }
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