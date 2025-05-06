package com.example.resq.presentaion.roomnotify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.rooms.model.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomNotifyViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _notifications = MutableStateFlow(emptyList<Room>())
    val notifications: StateFlow<List<Room>> = _notifications

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getInvitedRooms().body()
                response?.rooms?.let { _notifications.value = it }
            } catch (e: Exception) {
                Log.d("getNotifications", e.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun acceptNotify(roomId: String) {
        viewModelScope.launch {
            try {
                apiService.acceptNotify(roomId)
                _notifications.value.forEachIndexed { index, notify ->
                    if (notify.roomId == roomId) {
                        _notifications.value -= _notifications.value[index]
                        return@forEachIndexed
                    }
                }
            } catch (e: Exception) {
                Log.d("acceptNotify", e.message.toString())
            }
        }
    }

    fun refuseNotify(roomId: String) {
        viewModelScope.launch {
            try {
                apiService.rejectNotify(roomId)
                _notifications.value.forEachIndexed { index, notify ->
                    if (notify.roomId == roomId) {
                        _notifications.value -= _notifications.value[index]
                        return@forEachIndexed
                    }
                }

            } catch (e: Exception) {
                Log.d("refuseNotify", e.message.toString())
            }
        }
    }
}