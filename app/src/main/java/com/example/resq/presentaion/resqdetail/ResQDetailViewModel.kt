package com.example.resq.presentaion.resqdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.presentaion.resqdetail.model.ResQDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResQDetailViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _resQDetail = MutableStateFlow(emptyList<ResQDetail>())
    val resQDetail: StateFlow<List<ResQDetail>> = _resQDetail

    fun getResQDetail(resQ: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                _resQDetail.value = listOf(ResQDetail(resQ), ResQDetail("예시 resQDetail"))
            } catch (e: Exception) {
                Log.d("getResQDetail", e.message.toString())
            }
            _isLoading.value = false
        }
    }
}