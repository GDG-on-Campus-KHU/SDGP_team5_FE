package com.example.resq.presentaion.userrecordlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.userrecordlist.model.RecordInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserRecordListViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _records = MutableStateFlow(emptyList<RecordInfo>())
    val records: StateFlow<List<RecordInfo>> = _records

    init {
        getRecords()
    }

    private fun getRecords() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getRecords().body()
                response?.let { _records.value = it.records }
                Log.d("testt", response.toString())
            } catch (e: Exception) {

            }
            _isLoading.value = false
        }
    }
}