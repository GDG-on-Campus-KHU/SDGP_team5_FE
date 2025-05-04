package com.example.resq.presentaion.roomdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.rooms.model.Member
import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomDetailViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _membersMedicalInfo = MutableStateFlow(emptyList<Pair<String, MedicalInfo>>())
    val membersMedicalInfo: StateFlow<List<Pair<String, MedicalInfo>>> = _membersMedicalInfo

    private val _translationOptions = MutableStateFlow(listOf("한국" to "ko", "미국" to "en"))
    val translationOptions: StateFlow<List<Pair<String, String>>> = _translationOptions

    fun getRoomDetail(roomId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getRoomInfo(roomId)
                val members = response.body()?.roomDetail?.roomMembers
                members?.let { getMembersMedicalInfo(it) }
            } catch (e: Exception) {
                Log.d("getRoomDetail", e.message.toString())
            }
        }
    }

    private fun getMembersMedicalInfo(members: List<Member>) {
        viewModelScope.launch {
            try {
                val medicalList = coroutineScope {
                    members.map { member ->
                        async {
                            val userName =
                                apiService.getUserInfo(member.userId).body()?.userInfo?.userName
                            val userMedicalInfo =
                                apiService.getMedicalInfo(member.userId).body()?.medicalInfo
                            if (userName != null && userMedicalInfo != null)
                                Pair(userName, userMedicalInfo)
                            else null
                        }
                    }.awaitAll().filterNotNull()
                }
                _membersMedicalInfo.value = medicalList
            } catch (e: Exception) {
                Log.d("getMEmbersMedicalInfo", e.message.toString())
            }
            _isLoading.value = false
        }
    }
}