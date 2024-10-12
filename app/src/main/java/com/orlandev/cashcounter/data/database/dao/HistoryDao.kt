package com.orlandev.cashcounter.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.orlandev.cashcounter.data.database.entity.History
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: History)

    @Update
    suspend fun update(data: History)

    @Query("SELECT * FROM History ORDER BY date DESC")
    fun getAll(): Flow<List<History>>

    @Delete
    suspend fun delete(data: History)

    @Query(value = "DELETE FROM History")
    suspend fun deleteAll()

    @Query(value = "DELETE FROM History where id = :id")
    suspend fun deleteByID(id: Long)

}