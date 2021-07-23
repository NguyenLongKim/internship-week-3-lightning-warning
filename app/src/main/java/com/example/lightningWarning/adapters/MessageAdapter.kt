package com.example.lightningWarning.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.ItemMessageBinding
import com.example.lightningWarning.models.Message
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        @JvmStatic
        @BindingAdapter("createdTime")
        fun convertCreateTimeToString(view: TextView, createdTime: Long) {
            val sdf = SimpleDateFormat("dd MMM, h:mm a", Locale.ENGLISH)
            view.text = sdf.format(createdTime * 1000)
        }
    }

    private var onMessageClickListener: OnMessageClickListener? = null

    interface OnMessageClickListener {
        fun onMessageClick(message: Message)
    }

    fun setOnMessageClickListener(listener: OnMessageClickListener) {
        this.onMessageClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MessageViewHolder(inflater.inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MessageViewHolder).binding.message = messages[position]
        holder.itemView.setOnClickListener {
            onMessageClickListener?.onMessageClick(messages[position])
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addMessages(newMessages: List<Message>) {
        val oldMessagesSize = this.messages.size
        (this.messages as ArrayList).addAll(newMessages)
        this.notifyItemRangeChanged(oldMessagesSize, newMessages.size)
    }

    private class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMessageBinding.bind(view)
    }
}