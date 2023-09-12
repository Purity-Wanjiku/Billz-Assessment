package com.collections.billz.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.collections.billz.models.Bill
import com.collections.billz.models.UpcomingBill

@Database(entities = [Bill :: class, UpcomingBill :: class], version = 3)
//accessing the database using a singleton database
abstract class BillzDb:RoomDatabase() {
    abstract fun billsDao(): BillsDao

    abstract fun upcomingBillsDao():UpcomingBillsDao
    companion object{
        var database: BillzDb? = null

        fun getDatabase(context: Context): BillzDb{
            if (database==null){
                database = Room
                    .databaseBuilder(context, BillzDb::class.java, "BillzDb")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database as BillzDb
        }
    }

}