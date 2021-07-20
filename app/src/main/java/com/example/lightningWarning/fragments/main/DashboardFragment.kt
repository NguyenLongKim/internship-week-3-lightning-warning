package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentDashboardBinding
import com.example.lightningWarning.viewmodels.DashboardFragmentViewModel
import com.google.android.material.tabs.TabLayoutMediator


class DashboardFragment : Fragment(){
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardFragmentViewModel by navGraphViewModels(R.id.dashboardFragment)
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = (activity as MainActivity).getToken()
        viewModel.loadSensors(token)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        // init view pager
        val pagerAdapter = MyPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false // disable swiping

        // connect view pager with tab layout
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position){
                0 -> {
                    tab.text = "Status"
                    tab.setIcon(R.drawable.bottom_icon_status)
                }
                1 -> {
                    tab.text = "Lightning Map"
                    tab.setIcon(R.drawable.bottom_icon_map)
                }
                else -> {
                    tab.text = "History"
                    tab.setIcon(R.drawable.bottom_icon_history)
                }
            }
        }.attach()

        // set tittle for action bar
        "Dashboard".also {
            (activity as AppCompatActivity)
                .findViewById<TextView>(R.id.toolbar_title)
                .text = it
        }

        //observer for selected sensor view
        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner, { sensorData ->
            binding.selectedSensor = sensorData
        })

        // listen to navigate to Search location fragment
        binding.tvSelectedSensorDisplayName.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToSearchLocationFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


    // PagerAdapter inner class
    private class MyPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StatusFragment()
                1 -> LightningMapFragment()
                else -> HistoryFragment()
            }
        }
    }
}