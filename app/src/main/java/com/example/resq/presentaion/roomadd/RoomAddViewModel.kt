package com.example.resq.presentaion.roomadd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    fun newRoom(roomTitle: String, members: List<String>) {
        viewModelScope.launch {
            _isLoading.value = true
            if (members.isNotEmpty())
                try {
                    val response = apiService.newRoom(roomTitle)
                    val roomId = response.body()?.data?.roomId
                    // 멤버 초대
//                    roomId?.let { inviteMembers(it, members) }
                } catch (e: Exception) {
                    Log.d("addMembers", e.message.toString())
                }
            _isLoading.value = false
        }
    }

    fun inviteMembers(roomId: String, members: List<String>) {
        viewModelScope.launch {
            if (members.isNotEmpty())
                try {
                    members.forEach { apiService.inviteRoomMember(roomId, it) }
                } catch (e: Exception) {
                    Log.d("inviteMembers", e.message.toString())
                }
        }
    }
}