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
import com.example.lightningWarning.adapters.MessageAdapter
import com.example.lightningWarning.databinding.FragmentHistoryBinding
import com.example.lightningWarning.databinding.FragmentMessageBinding
import com.example.lightningWarning.viewmodels.MainActivityViewModel

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadMessages()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_message,container,false)

        viewModel.getMessagesLiveData().observe(viewLifecycleOwner,{
            binding.rvMessage.adapter?.notifyDataSetChanged()
        })

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView(){
        val messages = viewModel.getMessagesLiveData().value
        if (messages!=null) {
            val adapter = MessageAdapter(messages)
            binding.rvMessage.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(context)
            }
        }
    }
}