package com.collections.billz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collections.billz.models.Bill
import com.collections.billz.models.UpcomingBill
import com.collections.billz.repository.BillzRepository
import com.collections.billz.utils.Constants
import kotlinx.coroutines.launch

class BillsViewModel:ViewModel() {
    val billsRepository = BillzRepository()

    fun saveBill(bill: Bill){
        viewModelScope.launch {
            billsRepository.saveBill(bill)
        }
    }

    fun createUpcomingBills(){
        viewModelScope.launch {
           billsRepository.createRecurringWeeklyBills()
            billsRepository.createRecurringMonthlyBills()
            billsRepository.createRecurringAnnualBills()
        }
    }


    fun getWeeklyUpcoming(): LiveData<List<UpcomingBill>>{
        return  billsRepository.getUpcomingBillsByFrequency(Constants.WEEKLY)
    }
    fun getMonthlyUpcoming(): LiveData<List<UpcomingBill>>{
        return  billsRepository.getUpcomingBillsByFrequency(Constants.MONTHLY)
    }
    fun getAnnualUpcoming(): LiveData<List<UpcomingBill>>{
        return  billsRepository.getUpcomingBillsByFrequency(Constants.ANNUAL)
    }

    fun updateUpcomingBill(upcomingBill: UpcomingBill){
        viewModelScope.launch {
            billsRepository.updateUpcomingBill(upcomingBill)
        }
    }

    fun getPaidBills():LiveData<List<UpcomingBill>>{
        return billsRepository.getPaidBIlls()
    }
}