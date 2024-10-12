package com.orlandev.cashcounter.data

import com.orlandev.cashcounter.data.database.dao.HistoryDao
import com.orlandev.cashcounter.data.database.entity.History

class DataRepository(
    private val historyDao: HistoryDao
) {


    val allHistory = historyDao.getAll()

    suspend fun insert(history: History) {
        historyDao.insert(history)
    }

    suspend fun update(history: History) {
        historyDao.update(history)
    }

    suspend fun deleteAll() {
        historyDao.deleteAll()
    }

    suspend fun deleteById(id: Long) {
        historyDao.deleteByID(id)
    }

    suspend fun deleteHistory(history: History) {
        historyDao.delete(history)
    }

}