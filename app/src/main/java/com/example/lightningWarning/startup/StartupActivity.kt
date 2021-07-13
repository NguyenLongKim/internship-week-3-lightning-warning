package com.example.lightningWarning.startup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.ActivityStartupBinding
import com.example.lightningWarning.main.MainActivity
import com.example.lightningWarning.startup.fragments.LoginFragment

class StartupActivity : AppCompatActivity(), LoginFragment.OnLoginClickListener {
    private lateinit var binding: ActivityStartupBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_startup)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController() // init navController

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        setupActionBarWithNavController(navController) // connect actionbar with navController
    }


    override fun onSupportNavigateUp(): Boolean {
        // trigger navController navigate up when Back button on actionbar clicked
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onClick() {
        val intent = Intent(this@StartupActivity,MainActivity::class.java)
        startActivity(intent)
    }
}