package com.example.lightningWarning.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.ItemHistoryBinding
import com.example.lightningWarning.models.SensorHistory

class HistoryAdapter(private val histories: List<SensorHistory>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(inflater.inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HistoryViewHolder).binding.history=histories[position]
        val statusDrawableId = when (histories[position].alarm){
            "all clear" -> R.drawable.green_status_mini
            "lightning detected" -> R.drawable.orange_status_mini
            else -> R.drawable.red_status_mini
        }
        holder.binding.tvTitle.setCompoundDrawablesWithIntrinsicBounds(
            statusDrawableId, 0, 0, 0
        )
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    private class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemHistoryBinding.bind(view)
    }
}