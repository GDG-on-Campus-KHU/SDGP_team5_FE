package com.example.resq.presentaion.roomnotify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.roomnotify.model.Notify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomNotifyViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _notifications = MutableStateFlow(emptyList<Notify>())
    val notifications: MutableStateFlow<List<Notify>> = _notifications

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 알림 가져오기
                delay(1000)
                _notifications.value = (1..10).map { Notify(it.toString(), it.toString()) }
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
                    if (notify.id == roomId) {
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
                    if (notify.id == roomId) {
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