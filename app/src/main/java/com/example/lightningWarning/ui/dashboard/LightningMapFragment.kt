package com.example.lightningWarning.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentLightningMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class LightningMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentLightningMapBinding
    private val viewModel: DashboardFragmentViewModel by navGraphViewModels(R.id.dashboardFragment)
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var address: String = ""
    private var map: GoogleMap? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_lightning_map,
            container,
            false
        )

        // init google map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // selected sensor observer
        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner, { sensor ->
            viewModel.loadSelectedSensorDetail(
                (activity as MainActivity).getToken(),
                sensor.id
            )
        })

        // selected sensor detail observer
        viewModel.getSelectedSensorDetailLiveData().observe(viewLifecycleOwner, { sensorDetail ->
            latitude = sensorDetail.latitude
            longitude = sensorDetail.longitude
            address = sensorDetail.installation_address
            upDateMap()
            binding.imLightningDetected.also {
                if (sensorDetail.alarm == "clear"){
                    it.visibility = View.GONE
                } else{
                    it.visibility = View.VISIBLE
                }
            }
        })

        // error response observer
        viewModel.getErrorResponseLiveData().observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        upDateMap()
    }

    private fun upDateMap() {
        if (map != null) {
            val newLocation = LatLng(latitude, longitude)
            map!!.addMarker(MarkerOptions().position(newLocation).title(address))
            map!!.moveCamera(CameraUpdateFactory.newLatLng(newLocation))
            map!!.addCircle(
                CircleOptions()
                    .center(newLocation)
                    .radius(8000.0)
                    .strokeColor(ContextCompat.getColor(requireContext(), R.color.orange))
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