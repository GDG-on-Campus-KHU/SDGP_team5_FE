package com.example.resq.presentaion.rooms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.rooms.model.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomsViewModel : ViewModel() {

    private val _rooms = MutableStateFlow(emptyList<Room>())
    val rooms: StateFlow<List<Room>> = _rooms

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getRooms() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getRooms().body()
                response?.rooms?.let { _rooms.value = it }
            } catch (e: Exception) {
                Log.d("getRooms", e.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun deleteRoom(roomId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                apiService.deleteRoom(roomId)
                getRooms()
            } catch (e: Exception) {
                Log.d("deleteRoom", e.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun outRoom(roomId: String) {
        viewModelScope.launch {
            try {
                apiService.outRoom(roomId)
                getRooms()
            } catch (e: Exception) {
                Log.d("outRoom", e.message.toString())
            }
        }
    }
}