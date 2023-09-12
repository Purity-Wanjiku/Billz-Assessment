package com.collections.billz.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.collections.billz.models.Bill

@Dao
interface BillsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBill(bill: Bill)

    @Query ("SELECT * FROM Bills WHERE frequency=:freq")
    fun getRecurringBills(freq:String):List<Bill>
}