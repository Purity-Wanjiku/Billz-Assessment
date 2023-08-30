package com.collections.billz.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


//accessing the database using a singleton database
abstract class BillzDb:RoomDatabase() {
    abstract fun billsDao(): BillsDao
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