package com.example.resq.presentaion.roomadd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.network.model.InviteRoomRequest
import com.example.resq.network.model.NewRoomRequest
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomAddViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _members = MutableStateFlow(emptyList<String>())
    val members: StateFlow<List<String>> = _members

    private var getMembersJop: Job? = null

    // 차후에 사용자를 검색을 통해 찾아서 추가할 수 있게 하기
//    fun getMembers(email: String) {
//        getMembersJop?.cancel()
//        getMembersJop = viewModelScope.launch {
//            _isLoading.value = true
//            if (email.isEmpty())
//                _members.value = emptyList()
//            else
//                try {
//                    // 전체에서 멤버 검색
//                } catch (e: Exception) {
//                    Log.d("getMembers", e.message.toString())
//                }
//            _isLoading.value = false
//        }
//    }

    fun newRoom(roomTitle: String, members: List<String>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val title = NewRoomRequest(roomTitle)
                val response = apiService.newRoom(title)
                val roomId = response.body()?.data?.roomId
                if (members.isNotEmpty()) {
                    roomId?.let { inviteMembers(it, members) }
                }
            } catch (e: Exception) {
                Log.d("addMembers", e.message.toString())
                _isLoading.value = false
            }
        }
    }

    private fun inviteMembers(roomId: String, members: List<String>) {
        viewModelScope.launch {
            if (members.isNotEmpty())
                try {
                    members.forEach {
                        val email = InviteRoomRequest(it)
                        apiService.inviteRoomMember(roomId, email)
                    }
                } catch (e: Exception) {
                    Log.d("inviteMembers", e.message.toString())
                }
        }
    }
}