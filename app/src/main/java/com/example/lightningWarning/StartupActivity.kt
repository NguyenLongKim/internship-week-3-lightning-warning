package com.example.lightningWarning

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lightningWarning.models.UserData
import com.google.gson.Gson

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
            intentToMainActivity(userData)
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