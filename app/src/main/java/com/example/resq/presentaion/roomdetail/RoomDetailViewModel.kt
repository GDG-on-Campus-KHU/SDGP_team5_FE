package com.example.resq.presentaion.roomdetail

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.network.model.TranslateInfoRequest
import com.example.resq.presentaion.roomdetail.model.UserMedicalInfo
import com.example.resq.presentaion.rooms.model.Member
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomDetailViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _roomTitle = MutableStateFlow("")
    val roomTitle: StateFlow<String> = _roomTitle

    private val _membersInfo = MutableStateFlow(emptyList<String>())
    val membersInfo: StateFlow<List<String>> = _membersInfo

    private val _membersMedicalInfo = MutableStateFlow(emptyList<UserMedicalInfo>())
    val membersMedicalInfo: StateFlow<List<UserMedicalInfo>> = _membersMedicalInfo

    private val _translationOptions = MutableStateFlow(listOf("한국" to "ko", "미국" to "us"))
    val translationOptions: StateFlow<List<Pair<String, String>>> = _translationOptions

    fun getRoomDetail(roomId: String, language: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getRoomInfo(roomId)
                response.body()?.roomDetail?.let {
                    if (language == Locale.current.language)
                        getMembersMedicalInfo(it.roomMembers, false)
                    else
                        getMembersMedicalInfo(it.roomMembers, true)
                    _roomTitle.value = it.roomTitle
                }
            } catch (e: Exception) {
                Log.d("getRoomDetail", e.message.toString())
            }
        }
    }

    private fun getMembersMedicalInfo(members: List<Member>, translate: Boolean) {
        viewModelScope.launch {
            try {
                val infoList = coroutineScope {
                    members.map { member ->
                        async {
                            apiService.getUserInfo(member.userId).body()?.userInfo?.userName
                        }
                    }.awaitAll().map { it ?: "" }
                }
                val medicalList = coroutineScope {
                    if (translate)
                        members.map { member ->
                            async {
                                val userId = TranslateInfoRequest(member.userId)
                                apiService.translateInfo(userId).body()?.data
                            }
                        }.awaitAll().map {
                            it?.let {
                                UserMedicalInfo(
                                    userBloodType = it.userBloodType,
                                    userAllergy = it.userAllergy,
                                    userMedication = it.userMedication,
                                    userHeight = it.userHeight,
                                    userHeightUnit = it.userHeightUnit,
                                    userWeight = it.userWeight,
                                    userWeightUnit = it.userWeightUnit,
                                    userBirthdate = it.userBirthdate,
                                    userNotes = it.userNotes
                                )
                            } ?: UserMedicalInfo("", "", "", 0.0, "", 0.0, "", "", "")
                        }
                    else
                        members.map { member ->
                            async {
                                apiService.getMedicalInfo(member.userId).body()?.medicalInfo
                            }
                        }.awaitAll().map {
                            it?.let {
                                UserMedicalInfo(
                                    userBloodType = it.userBloodType,
                                    userAllergy = it.userAllergy,
                                    userMedication = it.userMedication,
                                    userHeight = it.userHeight,
                                    userHeightUnit = it.userHeightUnit,
                                    userWeight = it.userWeight,
                                    userWeightUnit = it.userWeightUnit,
                                    userBirthdate = it.userBirthdate,
                                    userNotes = it.userNotes
                                )
                            } ?: UserMedicalInfo("", "", "", 0.0, "", 0.0, "", "", "")
                        }
                }
                _membersInfo.value = infoList
                _membersMedicalInfo.value = medicalList
            } catch (e: Exception) {
                Log.d("getMEmbersMedicalInfo", e.message.toString())
            }
            _isLoading.value = false
        }
    }
}