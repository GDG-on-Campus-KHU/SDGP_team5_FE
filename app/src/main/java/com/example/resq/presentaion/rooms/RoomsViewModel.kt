package com.example.resq.presentaion.rooms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.presentaion.rooms.model.Room
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomsViewModel : ViewModel() {

    private val _rooms = MutableStateFlow(emptyList<Room>())
    val rooms: StateFlow<List<Room>> = _rooms

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getRooms("")
    }

    private fun getRooms(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                _rooms.value = (1..10).map { Room(it.toString(), it.toString()) }
            } catch (e: Exception) {
                Log.d("getRooms", e.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun deleteRoom(roomTitle: String) {
        viewModelScope.launch {
            try {
                // 방 삭제
                getRooms("")
            } catch (e: Exception) {
                Log.d("getRooms", e.message.toString())
            }
        }
    }

    fun outRoom(roomTitle: String) {
        viewModelScope.launch {
            try {
                // 방 나가기
                getRooms("")
            } catch (e: Exception) {
                Log.d("getRooms", e.message.toString())
            }
        }
    }
}