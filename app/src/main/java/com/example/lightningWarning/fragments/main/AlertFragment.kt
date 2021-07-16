package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lightningWarning.R
import com.example.lightningWarning.adapters.AlertAdapter
import com.example.lightningWarning.adapters.MessageAdapter
import com.example.lightningWarning.databinding.FragmentAlertBinding
import com.example.lightningWarning.databinding.FragmentMessageBinding
import com.example.lightningWarning.viewmodels.MainActivityViewModel

class AlertFragment : Fragment() {
    private lateinit var binding: FragmentAlertBinding
    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAlerts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_alert,container,false)

        viewModel.getAlertsLiveData().observe(viewLifecycleOwner,{
            binding.rvAlert.adapter?.notifyDataSetChanged()
        })

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView(){
        val alerts = viewModel.getAlertsLiveData().value
        if (alerts!=null) {
            val adapter = AlertAdapter(alerts)
            binding.rvAlert.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(context)
            }
        }
    }
}