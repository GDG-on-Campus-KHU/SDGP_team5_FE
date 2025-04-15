package com.example.resq.presentaion.roomadd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoomAddViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _members = MutableStateFlow(emptyList<String>())
    val members: StateFlow<List<String>> = _members

    private var getMembersJop: Job? = null

    fun getMembers(email: String) {
        getMembersJop?.cancel()
        getMembersJop = viewModelScope.launch {
            _isLoading.value = true
            if (email.isEmpty())
                _members.value = emptyList()
            else
                try {
                    // 전체에서 멤버 검색
                    delay(1000)
                    _members.value = (1..10).map { it.toString() }
                } catch (e: Exception) {
                    Log.d("getMembers", e.message.toString())
                }
            _isLoading.value = false
        }
    }

    fun resetMembers() {
        getMembers("")
    }

    fun addMembers(roomTitle: String, members: List<String>) {
        viewModelScope.launch {
            _isLoading.value = true
            if (members.isNotEmpty())
                try {
                    // 멤버 추가
                } catch (e: Exception) {
                    Log.d("addMembers", e.message.toString())
                }
            _isLoading.value = false
        }
    }
}