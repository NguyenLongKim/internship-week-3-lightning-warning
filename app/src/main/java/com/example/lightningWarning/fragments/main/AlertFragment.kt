package com.example.lightningWarning.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.adapters.AlertAdapter
import com.example.lightningWarning.adapters.MessageAdapter
import com.example.lightningWarning.databinding.FragmentAlertBinding
import com.example.lightningWarning.databinding.FragmentMessageBinding
import com.example.lightningWarning.viewmodels.MainActivityViewModel
import com.example.lightningWarning.viewmodels.NotificationFragmentViewModel

class AlertFragment : Fragment() {
    private lateinit var binding: FragmentAlertBinding
    private val viewModel: NotificationFragmentViewModel by viewModels({ requireParentFragment() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val token = (activity as MainActivity).getToken()
        viewModel.loadAlerts(token)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alert, container, false)

        // get alerts response observer
        viewModel.getAlertsLiveData().observe(viewLifecycleOwner, {
            binding.rvAlert.adapter?.notifyDataSetChanged()
        })

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val alerts = viewModel.getAlertsLiveData().value
        val adapter = AlertAdapter(alerts!!)
        binding.rvAlert.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
        }
    }
}