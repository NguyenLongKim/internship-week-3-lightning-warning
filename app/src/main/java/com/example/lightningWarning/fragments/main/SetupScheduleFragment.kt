package com.example.lightningWarning.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R


class SetupScheduleFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setToolBarTitle("Setup Schedule")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup_schedule, container, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}