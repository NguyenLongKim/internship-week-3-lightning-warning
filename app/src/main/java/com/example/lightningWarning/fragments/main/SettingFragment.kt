package com.example.lightningWarning.fragments.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.SignInActivity
import com.example.lightningWarning.databinding.FragmentSettingBinding
import com.example.lightningWarning.viewmodels.MainActivityViewModel
import com.example.lightningWarning.viewmodels.SettingFragmentViewModel

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingFragmentViewModel by viewModels()
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = (activity as MainActivity).getToken()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // set toolbar title
        (activity as MainActivity).setToolBarTitle("Settings")

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        // listen to navigate to set up schedule fragment
        binding.tvSchedule.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingFragmentToSetupScheduleFragment()
            findNavController().navigate(action)
        }

        // listen to trigger sign out
        binding.tvLogout.setOnClickListener {
            viewModel.signOut(token)
        }

        // observer for sign out response
        viewModel.getSignOutResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response?.status == true) { // sign out successfully
                val mainActivity = activity as MainActivity
                mainActivity.setIsSignedOut(true)
                mainActivity.removeUserDataFromSharedPreference()
                mainActivity.returnToSignInActivity()
            }
        })

        return binding.root
    }
}