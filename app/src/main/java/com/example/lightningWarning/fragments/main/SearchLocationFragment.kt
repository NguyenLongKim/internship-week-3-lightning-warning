package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lightningWarning.R
import com.example.lightningWarning.adapters.LocationAdapter
import com.example.lightningWarning.databinding.FragmentSearchLocationBinding
import com.example.lightningWarning.models.SensorData
import com.example.lightningWarning.viewmodels.DashboardFragmentViewModel

class SearchLocationFragment : Fragment() {
    private lateinit var binding: FragmentSearchLocationBinding
    private val viewModel: DashboardFragmentViewModel by navGraphViewModels(R.id.dashboardFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_location,
            container,
            false
        )

        (activity as AppCompatActivity).supportActionBar?.hide()

        // selected sensor observer
        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner, { sensorData ->
            binding.selectedSensor = sensorData
        })

        // sensors observer
        viewModel.getSensorsLiveData().observe(viewLifecycleOwner, {
            binding.rvLocations.adapter?.notifyDataSetChanged()
        })

        // listener to close this fragment
        binding.tvSelectedSensorDisplayName.setOnClickListener {
            activity?.onBackPressed()
        }

        initLocationsRecyclerView()

        return binding.root
    }

    private fun initLocationsRecyclerView() {
        val locationAdapter = LocationAdapter(viewModel.getSensorsLiveData().value!!)
        binding.rvLocations.adapter = locationAdapter
        binding.rvLocations.layoutManager = LinearLayoutManager(context)
        locationAdapter.setOnLocationClickListener(object :
            LocationAdapter.OnLocationClickListener {
            override fun onLocationClick(location: SensorData) {
                viewModel.setSelectedSensor(location)
                activity?.onBackPressed()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}