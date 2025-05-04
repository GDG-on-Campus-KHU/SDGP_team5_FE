package com.example.resq.presentaion.roomdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.rooms.model.Member
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomDetailViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _membersInfo = MutableStateFlow(emptyList<Member>())
    val membersInfo: MutableStateFlow<List<Member>> = _membersInfo

    private val _translationOptions = MutableStateFlow(listOf("한국" to "ko", "미국" to "en"))
    val translationOptions: MutableStateFlow<List<Pair<String, String>>> = _translationOptions

    fun getRoomDetail(roomId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getRoomInfo(roomId)
                response.body()?.let { _membersInfo.value += it.roomDetail.roomMembers }
            } catch (e: Exception) {
                Log.d("getRoomDetail", e.message.toString())
            }
            _isLoading.value = false
        }
    }
}