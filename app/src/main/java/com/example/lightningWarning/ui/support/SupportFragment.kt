package com.example.lightningWarning.ui.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R

class SupportFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).setToolBarTitle("About")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}