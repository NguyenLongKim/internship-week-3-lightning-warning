package com.example.lightningWarning.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.lightningWarning.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_dashboard, container, false)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        val pagerAdapter = MyPagerAdapter(this)
        viewPager.adapter=pagerAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text=position.toString()
            tab.setIcon(R.drawable.ic_notifications)
        }.attach()
        "Dashboard".also { (activity as AppCompatActivity)
            .findViewById<TextView>(R.id.toolbar_title)
            .text = it }
        return view
    }


    private class MyPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0->StatusFragment()
                1->LightningMapFragment()
                else->HistoryFragment()
            }
        }
    }
}