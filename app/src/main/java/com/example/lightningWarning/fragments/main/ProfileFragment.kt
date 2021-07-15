package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.lightningWarning.R

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        val tvChangePassword = view.findViewById<TextView>(R.id.tv_change_password)
        tvChangePassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        }
        "My Profile".also { (activity as AppCompatActivity)
            .findViewById<TextView>(R.id.toolbar_title)
            .text = it }
        return view
    }
}