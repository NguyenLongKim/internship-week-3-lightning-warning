package com.example.lightningWarning.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.ItemAlertBinding
import com.example.lightningWarning.models.Alert

class AlertAdapter(private val alerts: List<Alert>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AlertViewHolder(inflater.inflate(R.layout.item_alert, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AlertViewHolder).binding.alert=alerts[position]
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    private class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAlertBinding.bind(view)
    }
}