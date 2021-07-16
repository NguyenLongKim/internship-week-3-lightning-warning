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
import com.example.lightningWarning.adapters.HistoryAdapter
import com.example.lightningWarning.databinding.FragmentHistoryBinding
import com.example.lightningWarning.viewmodels.MainActivityViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding:FragmentHistoryBinding
    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_history,container,false)

        viewModel.getSelectedSensorHistoriesLiveData().observe(viewLifecycleOwner,{
            binding.rvHistory.adapter?.notifyDataSetChanged()
        })

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView(){
        val histories = viewModel.getSelectedSensorHistoriesLiveData().value
        if (histories!=null) {
            val adapter = HistoryAdapter(histories)
            binding.rvHistory.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(context)
            }
        }
    }
}