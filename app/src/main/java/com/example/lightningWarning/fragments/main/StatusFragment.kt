package com.example.lightningWarning.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentStatusBinding
import com.example.lightningWarning.viewmodels.MainActivityViewModel

class StatusFragment : Fragment() {
    private val viewModel by activityViewModels<MainActivityViewModel>()
    private lateinit var binding : FragmentStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_status,container,false)

        // init observer
        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner,{sensorData->
            binding.sensor = sensorData
        })
        return binding.root
    }
}