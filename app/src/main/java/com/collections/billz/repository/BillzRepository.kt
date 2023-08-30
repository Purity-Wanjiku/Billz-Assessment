package com.collections.billz.repository

import com.collections.billz.BillzApp
import com.collections.billz.database.BillzDb
import com.collections.billz.models.Bill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BillzRepository {
    val db = BillzDb.getDatabase(BillzApp.appContext)
//    gives reference to db and write reference to it
    val billsDao = db.billsDao()

    suspend fun saveBill(bill: Bill){
        withContext(Dispatchers.IO){
            billsDao.insertBill(bill)
        }
    }

}