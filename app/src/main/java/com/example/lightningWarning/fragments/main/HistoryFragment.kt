package com.example.lightningWarning.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.adapters.HistoryAdapter
import com.example.lightningWarning.databinding.FragmentHistoryBinding
import com.example.lightningWarning.viewmodels.DashboardFragmentViewModel
import com.example.lightningWarning.viewmodels.MainActivityViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: DashboardFragmentViewModel by navGraphViewModels(R.id.dashboardFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_history,
            container,
            false
        )

        // selected sensor observer
        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner,{sensor->
                viewModel.loadSelectedSensorHistories(
                    (activity as MainActivity).getToken(),
                    sensor.id
                )
        })

        // selected sensor histories observer
        viewModel.getSelectedSensorHistoriesLiveData().observe(viewLifecycleOwner, {
            binding.rvHistory.adapter?.notifyDataSetChanged()
        })

        // error response observer
        viewModel.getErrorResponseLiveData().observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        })

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val adapter = HistoryAdapter(viewModel.getSelectedSensorHistoriesLiveData().value!!)
        binding.rvHistory.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
        }
    }
}