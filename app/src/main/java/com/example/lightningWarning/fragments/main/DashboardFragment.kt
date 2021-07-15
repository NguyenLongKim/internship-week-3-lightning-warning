package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DashboardFragment : Fragment() {
    private lateinit var binding : FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard,container,false)

        // init view pager
        val pagerAdapter = MyPagerAdapter(this)
        binding.viewPager.adapter=pagerAdapter

        // connect view pager with tab layout
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
            tab.text=position.toString()
            tab.setIcon(R.drawable.ic_notifications)
        }.attach()

        // set tittle for action bar
        "Dashboard".also { (activity as AppCompatActivity)
            .findViewById<TextView>(R.id.toolbar_title)
            .text = it }

        return binding.root
    }


    // PagerAdapter inner class
    private class MyPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0-> StatusFragment()
                1-> LightningMapFragment()
                else-> HistoryFragment()
            }
        }
    }
}