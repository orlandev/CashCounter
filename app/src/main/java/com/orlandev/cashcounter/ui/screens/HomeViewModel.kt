package com.orlandev.cashcounter.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orlandev.cashcounter.data.DataRepository
import com.orlandev.cashcounter.data.database.entity.History
import com.orlandev.cashcounter.utils.ICashPrint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val cashPrint: ICashPrint, private val dataRepository: DataRepository) :
    ViewModel() {

    val allHistory = dataRepository.allHistory

    fun deleteAllHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.deleteAll()
        }
    }

    fun insertHistory(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.insert(history)
        }
    }

    fun deleteHistoryById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.deleteById(id)
        }
    }

}