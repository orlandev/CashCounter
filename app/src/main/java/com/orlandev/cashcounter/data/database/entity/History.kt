package com.orlandev.cashcounter.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    val title:String="",
    val data: String,
    val date:Long
)