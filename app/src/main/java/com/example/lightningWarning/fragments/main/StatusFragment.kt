package com.example.lightningWarning.fragments.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentStatusBinding
import com.example.lightningWarning.viewmodels.DashboardFragmentViewModel

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
            binding.imLightningDetected.also {
                if (sensorData.alarm == "clear"){
                   it.visibility = View.GONE
                } else{
                    it.visibility = View.VISIBLE
                }
            }
        })

        return binding.root
    }
}