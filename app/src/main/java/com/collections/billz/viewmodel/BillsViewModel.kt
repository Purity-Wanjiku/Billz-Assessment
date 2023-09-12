package com.collections.billz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collections.billz.models.Bill
import com.collections.billz.repository.BillzRepository
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
        }
    }

}