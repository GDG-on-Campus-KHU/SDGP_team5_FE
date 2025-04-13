package com.example.resq.presentaion.resqdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResQDetailViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _resQDetail = MutableStateFlow(emptyList<String>())
    val resQDetail: StateFlow<List<String>> = _resQDetail

    fun getResQDetail(resQ: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                _resQDetail.value = listOf("resQ", "예시 resQDetail")
            } catch (e: Exception) {

            }
            _isLoading.value = false
        }
    }
}