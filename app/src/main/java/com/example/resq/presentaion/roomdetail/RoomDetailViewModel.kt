package com.example.resq.presentaion.roomdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.presentaion.roomdetail.model.RoomDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomDetailViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _roomDetails = MutableStateFlow(emptyList<RoomDetail>())
    val roomDetails: MutableStateFlow<List<RoomDetail>> = _roomDetails

    private val _translationOptions = MutableStateFlow(listOf("한국" to "ko", "미국" to "en"))
    val translationOptions: MutableStateFlow<List<Pair<String, String>>> = _translationOptions

    fun getRoomDetail(roomId: String, language: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 방에 있는 유저들의 의료정보 가져오기
                delay(1000)
                _roomDetails.value = exUserData()
            } catch (e: Exception) {
                Log.d("getRoomDetail", e.message.toString())
            }
            _isLoading.value = false
        }
    }
}

fun exUserData(): List<RoomDetail> {
    return listOf(RoomDetail("a"), RoomDetail("b"), RoomDetail("c"), RoomDetail("d"))
}