package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentDashboardBinding
import com.example.lightningWarning.viewmodels.DashboardFragmentViewModel
import com.google.android.material.tabs.TabLayoutMediator

private const val NUM_PAGES = 3

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardFragmentViewModel by navGraphViewModels(R.id.dashboardFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadSensors((activity as MainActivity).getToken())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        )

        // init view pager
        val pagerAdapter = MyPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false // disable swiping
        binding.viewPager.offscreenPageLimit =
            NUM_PAGES / 2 // decrease delay when switch to other page

        // connect view pager with tab layout
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
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

        // set toolbar title
        (activity as MainActivity).setToolBarTitle("Dashboard")

        // selected sensor observer
        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner, { sensorData ->
            binding.selectedSensor = sensorData
        })

        // listen to navigate to Search location fragment
        binding.tvSelectedSensorDisplayName.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToSearchLocationFragment()
            findNavController().navigate(action)
        }

        // error response observer
        viewModel.getErrorResponseLiveData().observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }


    // PagerAdapter inner class
    private class MyPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return NUM_PAGES
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