package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lightningWarning.R
import com.example.lightningWarning.adapters.LocationAdapter
import com.example.lightningWarning.databinding.FragmentSearchLocationBinding
import com.example.lightningWarning.models.SensorData
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

        viewModel.getSensorsLiveData().observe(viewLifecycleOwner,{
            binding.rvLocations.adapter?.notifyDataSetChanged()
        })

        (activity as AppCompatActivity).supportActionBar?.hide()

        initLocationsRecyclerView()

        return binding.root
    }

    private fun initLocationsRecyclerView(){
        val adapter = LocationAdapter(viewModel.getSensorsLiveData().value!!)
        binding.rvLocations.adapter=adapter
        binding.rvLocations.layoutManager=LinearLayoutManager(context)
        adapter.setOnLocationClickListener(object : LocationAdapter.OnLocationClickListener{
            override fun onLocationClick(location: SensorData) {
                viewModel.setSelectedSensor(location)
                (activity as AppCompatActivity).onBackPressed()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}