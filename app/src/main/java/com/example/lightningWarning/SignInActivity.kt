package com.example.lightningWarning

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lightningWarning.databinding.ActivitySignInBinding
import com.example.lightningWarning.fragments.startup.LoginFragment
import com.example.lightningWarning.models.UserData
import com.example.lightningWarning.viewmodels.SignInActivityViewModel

class SignInActivity : AppCompatActivity(), LoginFragment.OnLoginSuccessListener {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<SignInActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController() // init navController

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        setupActionBarWithNavController(navController) // connect actionbar with navController

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }


    override fun onSupportNavigateUp(): Boolean {
        // trigger navController navigate up when Back button on actionbar clicked
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onLoginSuccess(userData: UserData) {
        intentToMainActivity(userData)
    }

    private fun intentToMainActivity(userData: UserData) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userData", userData)
        startActivity(intent)
        finish()
    }
}