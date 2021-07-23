package com.example.lightningWarning.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.adapters.MessageAdapter
import com.example.lightningWarning.databinding.FragmentMessageBinding
import com.example.lightningWarning.models.Message
import com.example.lightningWarning.utils.EndlessScrollRecyclerViewListener

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private val viewModel: NotificationFragmentViewModel by viewModels({ requireParentFragment() })
    private var pageCount = 0
    private var totalPages = 1
    private var isLoadingMessages = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadMessages((activity as MainActivity).getToken(), ++pageCount)
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
        viewModel.getLoadMessagesResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response?.status == true) {
                val messageAdapter = binding.rvMessage.adapter as MessageAdapter
                messageAdapter.addMessages(response.data)
                totalPages = response.metadata.total_pages
            }
        })

        // is loading messages observer
        viewModel.getIsLoadingMessagesLiveData().observe(viewLifecycleOwner, { newValue ->
            isLoadingMessages = newValue
        })

        // error response observer
        viewModel.getErrorResponseLiveData().observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        })

        initRecyclerView()

        return binding.root
    }


    private fun initRecyclerView() {
        val messageAdapter = MessageAdapter(ArrayList())
        messageAdapter.setOnMessageClickListener(object : MessageAdapter.OnMessageClickListener {
            override fun onMessageClick(message: Message) {
                val bundle = Bundle()
                bundle.putLong("created_time", message.created_at)
                bundle.putString("title", message.title)
                bundle.putString("description", message.description)
                findNavController().navigate(R.id.action_global_detailFragment, bundle)
            }
        })
        binding.rvMessage.apply {
            this.adapter = messageAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.addOnScrollListener(
                object :
                    EndlessScrollRecyclerViewListener(this.layoutManager as LinearLayoutManager) {
                    override fun onLoadMore() {
                        if (pageCount < totalPages && !isLoadingMessages) {
                            viewModel.loadMessages(
                                (activity as MainActivity).getToken(),
                                ++pageCount
                            )
                        }
                    }
                }
            )
        }
    }
}