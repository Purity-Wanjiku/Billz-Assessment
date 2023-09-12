package com.collections.billz.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.collections.billz.databinding.FragmentSummaryBinding
import com.collections.billz.databinding.UpcomingBillsListItemBinding
import com.collections.billz.models.UpcomingBill

class UpcomingBillAdapter (var upcomingBill: List<UpcomingBill>): Adapter<UpcomingBillsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingBillsViewHolder {
       val binding = UpcomingBillsListItemBinding
           .inflate(LayoutInflater.from(parent.context), parent, false)
        return  UpcomingBillsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingBillsViewHolder, position: Int) {
     var upcomingBill = upcomingBill.get(position)
        holder.binding.apply {
            cbUpcoming.text = upcomingBill.name
            tvAmount.text = upcomingBill.amount.toString()
            tvDueDate.text = upcomingBill.dueDate
        }
    }
    override fun getItemCount(): Int {
        return  upcomingBill.size
    }
}

class UpcomingBillsViewHolder(var binding: UpcomingBillsListItemBinding):
ViewHolder(binding.root)