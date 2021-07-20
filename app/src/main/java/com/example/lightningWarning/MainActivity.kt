package com.example.lightningWarning

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.ActivityMainBinding
import com.example.lightningWarning.models.UserData
import com.example.lightningWarning.viewmodels.MainActivityViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userData: UserData
    private var isSignOut = false
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retrieve user data from login activity
        val userData = intent.getParcelableExtra<UserData>("userData")
        if (userData != null) {
            this.userData = userData
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment,
                R.id.profileFragment,
                R.id.settingFragment,
            ),
            binding.drawerLayout
        )

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navDrawer.setupWithNavController(navController)

        window.statusBarColor = ContextCompat.getColor(this,R.color.white)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    fun getToken(): String = this.userData.token.token

    fun getRefreshToken(): String = this.userData.token.refresh_token

    fun getUserData(): UserData = this.userData

    fun setUserData(userData:UserData){
        this.userData = userData
    }

    fun setIsSignOut(isSignOut:Boolean){
        this.isSignOut = isSignOut
    }

    private fun saveUserData(userData: UserData){
        val gson = Gson()
        val jsonStringOfUserData = gson.toJson(userData)//convert userData to JSON string
        val shared = getSharedPreferences("Khind", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString("jsonStringOfUserData", jsonStringOfUserData)
        editor.apply()
    }

    override fun onStop() {
        if (!isSignOut)
        saveUserData(userData)
        super.onStop()
    }
}