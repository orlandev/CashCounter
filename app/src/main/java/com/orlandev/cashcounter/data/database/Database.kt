package com.orlandev.cashcounter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.orlandev.cashcounter.data.database.dao.HistoryDao
import com.orlandev.cashcounter.data.database.entity.History


@Database(
    entities = [History::class], version = 1, exportSchema = true, autoMigrations = [

    ]
)
abstract class CashCounterDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {

        private const val DATABASE_NAME = "cashcounter-db"

        @Volatile
        private var INSTANCE: CashCounterDatabase? = null
        fun getInstance(context: Context): CashCounterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, CashCounterDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}