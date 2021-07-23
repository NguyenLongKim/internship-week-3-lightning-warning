package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentSettingBinding
import com.example.lightningWarning.models.RequestUpdateScheduleData
import com.example.lightningWarning.models.ScheduleData
import com.example.lightningWarning.viewmodels.SettingFragmentViewModel

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val viewModel by navGraphViewModels<SettingFragmentViewModel>(R.id.settingFragment)
    private lateinit var oldSchedule: ScheduleData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadSchedule((activity as MainActivity).getToken())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // set toolbar title
        (activity as MainActivity).setToolBarTitle("Settings")

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting,
            container,
            false
        )

        // listen to navigate to set up schedule fragment
        binding.tvSchedule.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingFragmentToSetupScheduleFragment()
            findNavController().navigate(action)
        }

        // listen to trigger sign out
        binding.tvLogout.setOnClickListener {
            viewModel.signOut((activity as MainActivity).getToken())
        }

        // listen to update do not disturb
        binding.swDisturb.setOnClickListener {
            it as Switch
            val newSchedule = RequestUpdateScheduleData()
            newSchedule.alarm_vibration = oldSchedule.alarm_vibration
            newSchedule.alarm_sound = oldSchedule.alarm_sound
            newSchedule.do_not_disturb = it.isChecked
            viewModel.putSchedule((activity as MainActivity).getToken(), newSchedule)
        }

        // listen to update alarm_sound
        binding.swAlarmSound.setOnClickListener {
            it as Switch
            val newSchedule = RequestUpdateScheduleData()
            newSchedule.alarm_vibration = oldSchedule.alarm_vibration
            newSchedule.do_not_disturb = oldSchedule.do_not_disturb
            newSchedule.alarm_sound = it.isChecked
            viewModel.putSchedule((activity as MainActivity).getToken(), newSchedule)
        }

        // listen to update alarm_sound
        binding.swAlarmVibration.setOnClickListener {
            it as Switch
            val newSchedule = RequestUpdateScheduleData()
            newSchedule.do_not_disturb = oldSchedule.do_not_disturb
            newSchedule.alarm_sound = oldSchedule.alarm_sound
            newSchedule.alarm_vibration = it.isChecked
            viewModel.putSchedule((activity as MainActivity).getToken(), newSchedule)
        }


        // get schedule response observer
        viewModel.getLoadScheduleResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response?.status == true) {
                response.data.also {
                    binding.schedule = it
                    oldSchedule = it
                }
            }

        })

        // put schedule response observer
        viewModel.getPutScheduleResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response?.status == true) {
                response.data.also {
                    oldSchedule = it
                    binding.schedule = it
                }
            }
        })

        // observer for sign out response
        viewModel.getSignOutResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response?.status == true) { // sign out successfully
                (activity as MainActivity).processToSignOut()
            }
        })

        // error response observer
        viewModel.getErrorResponseLiveData().observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }
}