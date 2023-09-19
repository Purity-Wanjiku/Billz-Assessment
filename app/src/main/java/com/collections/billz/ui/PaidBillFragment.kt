package com.collections.billz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.collections.billz.R
import com.collections.billz.databinding.FragmentPaidBillBinding
import com.collections.billz.models.Bill
import com.collections.billz.models.UpcomingBill
import com.collections.billz.viewmodel.BillsViewModel

class PaidBillFragment : Fragment(), OnClickBill {
var binding : FragmentPaidBillBinding? = null
        val billsViewModel : BillsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaidBillBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
       billsViewModel.getPaidBills().observe(this){paidbills ->
           val adapter = UpcomingBillAdapter(paidbills, this)
           //we call this because the adapter is on the type onClickBill and the Fragment class. The fragment has the function the adapter needs to call (checkPaidBill function)
           binding?.rvPaid?.layoutManager = LinearLayoutManager(requireContext())
           binding?.rvPaid?.adapter = adapter
           
       }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun checkPaidBill(upcomingBill: UpcomingBill) {
       upcomingBill.paid = !upcomingBill.paid
        billsViewModel.updateUpcomingBill(upcomingBill)
    }

}