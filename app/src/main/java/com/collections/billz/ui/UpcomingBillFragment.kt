package com.collections.billz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.collections.billz.R
import com.collections.billz.databinding.FragmentSummaryBinding
import com.collections.billz.databinding.FragmentUpcomingBillBinding
import com.collections.billz.viewmodel.BillsViewModel


class UpcomingBillFragment : Fragment() {
var binding : FragmentUpcomingBillBinding? = null
        val billsViewModel : BillsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingBillBinding.inflate(layoutInflater, container, false)
        return  inflater.inflate(R.layout.fragment_upcoming_bill, container, false)
        return  binding?.root
    }


    override fun onResume() {
        super.onResume()
        billsViewModel.getWeeklyUpcoming().observe(this){upcomingWeekly->
            val adapter = UpcomingBillAdapter(upcomingWeekly)
            binding?.rvWeekly?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvWeekly?.adapter = adapter

        }

        billsViewModel.getMonthlyUpcoming().observe(this){upcomingMonthly->
            val adapter = UpcomingBillAdapter(upcomingMonthly)
            binding?.rvPaid?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvPaid?.adapter = adapter
        }

        billsViewModel.getAnnualUpcoming().observe(this){upcomingAnnual->
            val adapter = UpcomingBillAdapter(upcomingAnnual)
            binding?.rvAnnual?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvAnnual?.adapter = adapter

        }
    }
     override fun onDestroy(){
        super.onDestroy()
        binding = null
    }

}





