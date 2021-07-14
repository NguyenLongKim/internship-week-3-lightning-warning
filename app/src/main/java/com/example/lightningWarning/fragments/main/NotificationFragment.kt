package com.example.lightningWarning.fragments.main

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

class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        "Notifications".also { (activity as AppCompatActivity)
            .findViewById<TextView>(R.id.toolbar_title)
            .text = it }
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        val adapter = MyPagerAdapter(this)
        viewPager.adapter=adapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = if (position==0) "Messages" else "Alerts"
        }.attach()
        return view
    }

    private class MyPagerAdapter(fragment:Fragment):FragmentStateAdapter(fragment){
        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0-> MessageFragment()
                else-> AlertFragment()
            }
        }
    }
}