package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentSearchLocationBinding
import com.example.lightningWarning.viewmodels.MainActivityViewModel

class SearchLocationFragment : Fragment() {
    private lateinit var binding:FragmentSearchLocationBinding
    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_location,container,false)

        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner,{sensorData->
            binding.selectedSensor = sensorData
        })

        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}