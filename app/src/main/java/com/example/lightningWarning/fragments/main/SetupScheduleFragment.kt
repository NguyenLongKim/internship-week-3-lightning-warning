package com.example.lightningWarning.fragments.main

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentSetupScheduleBinding
import com.example.lightningWarning.models.RequestUpdateScheduleData
import com.example.lightningWarning.models.ScheduleData
import com.example.lightningWarning.viewmodels.SettingFragmentViewModel


private const val START_TIME_PICKER = 0
private const val END_TIME_PICKER = 1

class SetupScheduleFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: FragmentSetupScheduleBinding
    private val viewModel by navGraphViewModels<SettingFragmentViewModel>(R.id.settingFragment)
    private var timePickerFlag: Int? = null
    private var justUpdateSchedule = false
    private lateinit var oldSchedule: ScheduleData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.loadSchedule((activity as MainActivity).getToken())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setToolBarTitle("Setup Schedule")

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setup_schedule,
            container,
            false
        )

        // listener to change start time
        binding.tvStartTime.setOnClickListener {
            timePickerFlag = START_TIME_PICKER
            showTimePickerDialog()
        }

        // listener to change end time
        binding.tvEndTime.setOnClickListener {
            timePickerFlag = END_TIME_PICKER
            showTimePickerDialog()
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
            if (response?.status == true && justUpdateSchedule) {
                response.data.also {
                    oldSchedule = it
                    binding.schedule = it
                }
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                justUpdateSchedule = false
            }
        })

        // error response observer
        viewModel.getErrorResponseLiveData().observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        })


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.setup_schedule_action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.it_save -> {
                onSaveSchedule()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showTimePickerDialog() {
        val hour: Int
        val minute: Int

        if (timePickerFlag == START_TIME_PICKER) {
            hour = oldSchedule.start_hour
            minute = oldSchedule.start_min
        } else {
            hour = oldSchedule.end_hour
            minute = oldSchedule.end_min
        }

        val timePickerDialog = TimePickerDialog(
            context,
            this,
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        String.format("%02d:%02d", hourOfDay, minute).also {
            if (timePickerFlag == START_TIME_PICKER) {
                binding.tvStartTime.text = it
            } else {
                binding.tvEndTime.text = it
            }
        }
    }

    private fun onSaveSchedule() {
        justUpdateSchedule = true
        val newSchedule = RequestUpdateScheduleData()
        val newStartTime = binding.tvStartTime.text.split(':')
        val newEndTime = binding.tvEndTime.text.split(':')
        newSchedule.apply {
            this.do_not_disturb = oldSchedule.do_not_disturb
            this.alarm_sound = oldSchedule.alarm_sound
            this.alarm_vibration = oldSchedule.alarm_vibration
            if (newStartTime[0].toInt() != oldSchedule.start_hour) {
                this.start_hour = newStartTime[0].toInt()
            }
            if (newStartTime[1].toInt() != oldSchedule.start_min) {
                this.start_min = newStartTime[1].toInt()
            }
            if (newEndTime[0].toInt() != oldSchedule.end_hour) {
                this.end_hour = newEndTime[0].toInt()
            }
            if (newEndTime[1].toInt() != oldSchedule.end_min) {
                this.end_min = newEndTime[1].toInt()
            }
            if (binding.cbMonday.isChecked != oldSchedule.monday) {
                this.monday = binding.cbMonday.isChecked
            }
            if (binding.cbTuesday.isChecked != oldSchedule.tuesday) {
                this.tuesday = binding.cbTuesday.isChecked
            }
            if (binding.cbWednesday.isChecked != oldSchedule.wednesday) {
                this.wednesday = binding.cbWednesday.isChecked
            }
            if (binding.cbThursday.isChecked != oldSchedule.thursday) {
                this.thursday = binding.cbThursday.isChecked
            }
            if (binding.cbFriday.isChecked != oldSchedule.friday) {
                this.friday = binding.cbFriday.isChecked
            }
            if (binding.cbSaturday.isChecked != oldSchedule.saturday) {
                this.saturday = binding.cbSaturday.isChecked
            }
            if (binding.cbSunday.isChecked != oldSchedule.sunday) {
                this.sunday = binding.cbSunday.isChecked
            }
        }
        viewModel.putSchedule((activity as MainActivity).getToken(), newSchedule)
    }
}