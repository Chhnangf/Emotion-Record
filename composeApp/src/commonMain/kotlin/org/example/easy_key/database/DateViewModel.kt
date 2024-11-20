package org.example.easy_key.database

import DateRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DateViewModel(private val repository: DateRepository): ViewModel() {

    val allRecordsFlow: Flow<List<DateEntity>> = repository.getAllRecordsFlow()

    fun addRecord(dateObj: DateEntity) {
        viewModelScopeAsync {
            repository.addRecord(dateObj)
        }
    }


    private fun viewModelScopeAsync(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }
}