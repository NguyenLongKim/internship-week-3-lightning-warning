package com.example.lightningWarning.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.ItemMessageBinding
import com.example.lightningWarning.models.Message

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MessageViewHolder(inflater.inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MessageViewHolder).binding.message=messages[position]
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    private class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMessageBinding.bind(view)
    }
}