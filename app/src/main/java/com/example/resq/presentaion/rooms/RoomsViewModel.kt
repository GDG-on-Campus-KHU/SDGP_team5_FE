package com.example.resq.presentaion.rooms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
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

    fun getRooms() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getRooms()
                _rooms.value = response.body()?.rooms ?: emptyList()
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
                _rooms.value.forEachIndexed { index, room ->
                    if (room.roomId == roomId) {
                        _rooms.value -= _rooms.value[index]
                        return@forEachIndexed
                    }
                }
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
                _rooms.value.forEachIndexed { index, room ->
                    if (room.roomId == roomId) {
                        _rooms.value -= _rooms.value[index]
                        return@forEachIndexed
                    }
                }
            } catch (e: Exception) {
                Log.d("outRoom", e.message.toString())
            }
        }
    }
}