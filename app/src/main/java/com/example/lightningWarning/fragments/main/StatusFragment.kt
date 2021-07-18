package com.example.lightningWarning.fragments.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentStatusBinding
import com.example.lightningWarning.viewmodels.DashboardFragmentViewModel
import com.example.lightningWarning.viewmodels.MainActivityViewModel

class StatusFragment : Fragment() {
    private lateinit var binding : FragmentStatusBinding
    private val viewModel:DashboardFragmentViewModel by navGraphViewModels(R.id.dashboardFragment)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_status,container,false)

        // observer for selected sensor alarm view
        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner,{sensorData->
            binding.selectedSensor = sensorData
        })

        return binding.root
    }
}