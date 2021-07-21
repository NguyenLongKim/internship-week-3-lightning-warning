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
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.adapters.HistoryAdapter
import com.example.lightningWarning.adapters.MessageAdapter
import com.example.lightningWarning.databinding.FragmentHistoryBinding
import com.example.lightningWarning.databinding.FragmentMessageBinding
import com.example.lightningWarning.viewmodels.MainActivityViewModel
import com.example.lightningWarning.viewmodels.NotificationFragmentViewModel

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private val viewModel: NotificationFragmentViewModel by viewModels({ requireParentFragment() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadMessages((activity as MainActivity).getToken())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_message,
            container,
            false
        )

        // get messages response observer
        viewModel.getMessagesLiveData().observe(viewLifecycleOwner, {
            binding.rvMessage.adapter?.notifyDataSetChanged()
        })

        // error response observer
        viewModel.getErrorResponseLiveData().observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        })

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val adapter = MessageAdapter(viewModel.getMessagesLiveData().value!!)
        binding.rvMessage.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
        }
    }
}