package com.collections.billz.repository

import com.collections.billz.BillzApp
import com.collections.billz.database.BillzDb
import com.collections.billz.database.UpcomingBillsDao
import com.collections.billz.models.Bill
import com.collections.billz.models.UpcomingBill
import com.collections.billz.utils.Constants
import com.collections.billz.utils.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID

class BillzRepository {
    val db = BillzDb.getDatabase(BillzApp.appContext)
//    gives reference to db and write reference to it
    val billsDao = db.billsDao()
    val upcomingBillsDao = db.upcomingBillsDao()

    suspend fun saveBill(bill: Bill){
        withContext(Dispatchers.IO){
            billsDao.insertBill(bill)
        }
    }
    suspend fun insertUpcomingBills(upcomingBill: UpcomingBill){
        withContext(Dispatchers.IO){
            upcomingBillsDao.insertUpcomingBills(upcomingBill)
        }
    }
    //    go and querry all the monthly bills go through all of them if there exist an upcoming bill
    suspend fun createRecurringMonthlyBills() {
        withContext(Dispatchers.IO) {
            val montlyBills = billsDao.getRecurringBills(Constants.MONTHLY)
            montlyBills.forEach { bill ->
                val cal = Calendar.getInstance()
                val month = cal.get(Calendar.MONTH) + 1
                val year = cal.get(Calendar.YEAR)
                val startDate = "1/$month/$year"
                val endDate = "31/$month/$year"
                val exisitng = upcomingBillsDao.queryExistingBills(bill.billId, startDate, endDate)
                if (exisitng.isEmpty()) {
                    val newUpcomingBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = "${bill.dueDate} / $month/$year",
                        userId = bill.userId,
                        paid = false
                    )
                    insertUpcomingBills(newUpcomingBill)
                }
            }
        }
    }


    suspend fun createRecurringWeeklyBills(){
        withContext(Dispatchers.IO){
            val weeklyBills= billsDao.getRecurringBills(Constants.WEEKLY)
            val startDate = DateTimeUtils.getFirstDayOfWeek()
            val endDate = DateTimeUtils.getLastDayOfWeek()
            weeklyBills.forEach { bill ->
                val existing =
                    upcomingBillsDao.queryExistingBills(bill.billId, startDate, endDate)
                if (existing.isEmpty()) {
                    val newUpcomingWeeklyBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = DateTimeUtils.getDateOfWeekDay(bill.dueDate),
                        userId = bill.userId,
                        paid = false
                    )
                    upcomingBillsDao.insertUpcomingBills(newUpcomingWeeklyBill)
                }
            }
        }
    }
}