package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lightningWarning.R


class SetupScheduleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        "Setup Schedule".also { (activity as AppCompatActivity)
            .findViewById<TextView>(R.id.toolbar_title)
            .text = it }
        return inflater.inflate(R.layout.fragment_setup_schedule, container, false)
    }
}