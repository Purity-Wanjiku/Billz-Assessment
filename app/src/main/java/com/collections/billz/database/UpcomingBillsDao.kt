package com.collections.billz.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.collections.billz.models.UpcomingBill

@Dao
interface UpcomingBillsDao {
    //    we specify a uniqueness constraint like upcoming bill is inserted like once monthly when its expected
//  allows bulk insert of upcoming bills

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUpcomingBills( vararg upcomingBill: UpcomingBill)

    @Query("SELECT * FROM UpcomingBills WHERE billId =:billId AND dueDate BETWEEN :startDate AND :endDate LIMIT 1")
    fun queryExistingBills(billId:String, startDate:String, endDate:String):List<UpcomingBill>


    @Query("SELECT  * FROM UpcomingBills WHERE frequency = :freq AND paid = :paid ORDER BY dueDate")
    fun getUpcomingBillsByFrequency(freq : String, paid: Boolean=false): LiveData<List<UpcomingBill>>
}