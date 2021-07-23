package com.example.lightningWarning.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.ItemLocationBinding
import com.example.lightningWarning.models.SensorData

class LocationAdapter(private val locations: List<SensorData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onLocationClickListener: OnLocationClickListener? = null

    interface OnLocationClickListener {
        fun onLocationClick(location: SensorData)
    }

    fun setOnLocationClickListener(listener: OnLocationClickListener) {
        this.onLocationClickListener = listener
    }

    fun onLocationClick(location: SensorData) {
        onLocationClickListener?.onLocationClick(location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LocationViewHolder(inflater.inflate(R.layout.item_location, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LocationViewHolder).binding.location = locations[position]
        holder.binding.adapter = this
        val statusDrawableId = when (locations[position].alarm) {
            "clear" -> R.drawable.green_status_mini
            "warning" -> R.drawable.orange_status_mini
            else -> R.drawable.red_status_mini
        }
        holder.binding.tvLocationDisplayName.setCompoundDrawablesWithIntrinsicBounds(
            statusDrawableId, 0, 0, 0
        )
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    private class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemLocationBinding.bind(view)
    }
}