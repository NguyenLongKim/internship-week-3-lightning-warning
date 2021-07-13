package com.example.lightningWarning.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.lightningWarning.R

class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_setting, container, false)
        val tvSetupSchedule = view.findViewById<TextView>(R.id.tv_schedule)
        tvSetupSchedule.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingFragmentToSetupScheduleFragment()
            findNavController().navigate(action)
        }
        return view
    }
}