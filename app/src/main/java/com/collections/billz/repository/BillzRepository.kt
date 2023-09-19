package com.collections.billz.repository

import androidx.lifecycle.LiveData
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
                val startDate = DateTimeUtils.getFirstDayOfMonth()
                val endDate = DateTimeUtils.getLatsDateOfMOnth()


                val exisitng = upcomingBillsDao.queryExistingBills(bill.billId, startDate, endDate)
                if (exisitng.isEmpty()) {
                    val newUpcomingBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = DateTimeUtils.getFullDateFromDay(bill.dueDate),
                        userId = bill.userId,
                        paid = false
                    )
                    upcomingBillsDao.insertUpcomingBills(newUpcomingBill)
                }
            }
        }
    }


    suspend fun createRecurringWeeklyBills() {
        withContext(Dispatchers.IO) {
            val weeklyBills = billsDao.getRecurringBills(Constants.WEEKLY)
            val startDate = DateTimeUtils.getFirstDayOfWeek()
            val endDate = DateTimeUtils.getLastDayOfWeek()
            weeklyBills.forEach { bill ->
                val existing = upcomingBillsDao.queryExistingBills(bill.billId, startDate, endDate)
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
        suspend fun createRecurringAnnualBills(){
            withContext(Dispatchers.IO){
                val annualBills = billsDao.getRecurringBills(Constants.ANNUAL)
                val year = DateTimeUtils.getCurrentYear()
                val startDate = "$year-001-01"
                val endDate = "$year-12-31"
                annualBills.forEach{ bill ->
                    val existing = upcomingBillsDao.queryExistingBills(bill.billId, startDate, endDate)
                    if (existing.isEmpty()){
                        val newAnnualBill = UpcomingBill(
                            upcomingBillId = UUID.randomUUID().toString(),
                            billId = bill.billId,
                            name = bill.name,
                            amount = bill.amount,
                            frequency = bill.frequency,
                            dueDate = "${bill.dueDate}/$year",
                            userId = bill.userId,
                            paid = false
                        )
//                          save the bill
                      upcomingBillsDao.insertUpcomingBills(newAnnualBill)

                    }
                }
            }
        }



    fun getUpcomingBillsByFrequency (frequency: String):LiveData<List<UpcomingBill>>{
        return  upcomingBillsDao.getUpcomingBillsByFrequency(frequency)
    }

    suspend fun updateUpcomingBill(upcomingBill: UpcomingBill){
        withContext(Dispatchers.IO){
            upcomingBillsDao.updateUpcomingBill(upcomingBill)
        }
    }

    fun getPaidBIlls(): LiveData<List<UpcomingBill>> {
        return upcomingBillsDao.getPaidBills()
    }
}