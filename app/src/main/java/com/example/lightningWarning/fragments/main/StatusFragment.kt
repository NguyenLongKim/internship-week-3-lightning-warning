package com.example.lightningWarning.fragments.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentStatusBinding
import com.example.lightningWarning.viewmodels.DashboardFragmentViewModel
import com.example.lightningWarning.viewmodels.MainActivityViewModel

class StatusFragment : Fragment() {
    private lateinit var binding: FragmentStatusBinding
    private val viewModel: DashboardFragmentViewModel by navGraphViewModels(R.id.dashboardFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_status,
            container,
            false
        )

        // selected sensor observer
        viewModel.getSelectedSensorLiveData().observe(viewLifecycleOwner, { sensorData ->
            val statusDrawableId = when (sensorData.alarm) {
                "clear" -> R.drawable.green_status
                "warning" -> R.drawable.orange_status
                else -> R.drawable.red_status
            }
            binding.imStatus.setImageResource(statusDrawableId)
        })

        return binding.root
    }
}