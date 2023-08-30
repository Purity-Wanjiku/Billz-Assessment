package com.collections.billz.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.collections.billz.models.Bill

@Dao
interface BillsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBill(bill: Bill)
}