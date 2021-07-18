package com.example.lightningWarning.fragments.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentLightningMapBinding
import com.example.lightningWarning.viewmodels.DashboardFragmentViewModel
import com.example.lightningWarning.viewmodels.MainActivityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class LightningMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentLightningMapBinding
    private val viewModel:DashboardFragmentViewModel by navGraphViewModels(R.id.dashboardFragment)
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var address: String = ""
    private var map: GoogleMap? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lightning_map, container, false)

        // init google map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // observer for selected sensor map view
        viewModel.getSelectedSensorDetailLiveData().observe(viewLifecycleOwner, { sensorDetail ->
            latitude = sensorDetail.latitude
            longitude = sensorDetail.longitude
            address = sensorDetail.installation_address
            upDateMap()
        })


        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        upDateMap()
    }

    private fun upDateMap(){
        if (map!=null) {
            val newLocation = LatLng(latitude, longitude)
            map!!.addMarker(MarkerOptions().position(newLocation).title(address))
            map!!.moveCamera(CameraUpdateFactory.newLatLng(newLocation))
            map!!.addCircle(
                CircleOptions()
                    .center(newLocation)
                    .radius(8000.0)
                    .strokeColor(Color.YELLOW)
            )
            map!!.addCircle(
                CircleOptions()
                    .center(newLocation)
                    .radius(6000.0)
                    .strokeColor(Color.RED)
            )
        }
    }
}