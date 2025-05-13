package com.example.resq.presentaion.usermedicalinfo

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Accessibility
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.MainActivity.Companion.USER_DISPLAY_NAME
import com.example.resq.MainActivity.Companion.USER_EMAIL
import com.example.resq.R
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfoElement
import com.example.resq.network.model.MedicalInfoRequest
import com.example.resq.network.model.TranslateInfoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserMedicalInfoViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _medicalInfoList = MutableStateFlow<List<MedicalInfoElement>>(emptyList())
    val medicalInfoList: StateFlow<List<MedicalInfoElement>> = _medicalInfoList.asStateFlow()

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized

    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId

    private val _userDisplayName = MutableStateFlow(USER_DISPLAY_NAME)
    val userDisplayName: StateFlow<String> = _userDisplayName
    private var originalDisplayName: String? = null

    fun getInfo(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getInfo()
                Log.d("getInfo", "API 응답 성공 여부: ${response.isSuccessful}")
                Log.e("getInfo", "HTTP 상태 코드: ${response.code()}, 메시지: ${response.message()}")
                Log.d("getInfo", "responsebody: ${response.body()}")

                response.body()?.medicalInfo?.let { data ->
                    updateDisplayName(originalDisplayName ?: USER_DISPLAY_NAME)
                    _medicalInfoList.value = listOf(
                        MedicalInfoElement(
                            context.getString(R.string.info_blood_type),
                            context.getString(R.string.info_blood_type_placeholder),
                            Icons.Outlined.Bloodtype,
                            mutableStateOf(data.userBloodType),
                            mutableStateOf(true)
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_allergies),
                            context.getString(R.string.info_allergies_placeholder),
                            Icons.Outlined.Warning,
                            mutableStateOf(data.userAllergy),
                            mutableStateOf(true)
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_medicine),
                            context.getString(R.string.info_medicine_placeholder),
                            Icons.Outlined.Medication,
                            mutableStateOf(data.userMedication),
                            mutableStateOf(true)
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_height),
                            context.getString(R.string.info_height_placeholder),
                            Icons.Outlined.Accessibility,
                            mutableStateOf(data.userHeight.toString()),
                            mutableStateOf(data.userHeight != 0.0),
                            data.userHeightUnit
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_weight),
                            context.getString(R.string.info_weight_placeholder),
                            Icons.Outlined.MonitorWeight,
                            mutableStateOf(data.userWeight.toString()),
                            mutableStateOf(data.userWeight != 0.0),
                            data.userWeightUnit
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_date_of_birth),
                            context.getString(R.string.info_date_of_birth_placeholder),
                            Icons.Outlined.Today,
                            mutableStateOf(data.userBirthdate),
                            mutableStateOf(data.userBirthdate != "None")
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_additional_notes),
                            context.getString(R.string.info_additional_notes_placeholder),
                            Icons.Outlined.NoteAlt,
                            mutableStateOf(data.userNotes),
                            mutableStateOf(!(data.userNotes.isBlank() || data.userNotes == "없음" || data.userNotes == "None"))
                        )
                    )
                    _isInitialized.value = true
                }
            } catch (e: Exception) {
                Log.d("getInfo", e.message.toString())
            }

            _isLoading.value = false
        }
    }

    fun newInfo(
        request: MedicalInfoRequest,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val response = apiService.newInfo(request)
                Log.d("newInfo", "응답 코드: ${response.code()}, 메시지: ${response.message()}, body: ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("newInfo", "에러 바디: $errorBody")
                Log.d("info_id", "현재 유저 id는 ${response.body()?.medicalInfo?.userId}")
                val success = response.isSuccessful && response.body()?.boolean == true
                if (success) {
                    _isInitialized.value = true
                }
                onResult(success)
            } catch (e: Exception) {
                Log.d("newInfo", e.message.toString())
                onResult(false)
            }
            _isLoading.value = false
        }

    }

    fun editInfo(
        request: MedicalInfoRequest,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val response = apiService.editInfo(request)
                val success = response.isSuccessful && response.body()?.boolean == true
                onResult(success)
            } catch (e: Exception) {
                Log.d("newInfo", e.message.toString())
                onResult(false)
            }
            _isLoading.value = false
        }
    }

    fun getValueByLabel(label: String): String {
        return medicalInfoList.value.find { it.label == label }?.value?.value ?: ""
    }

    fun getValueByLabelAsDouble(label: String): Double {
        return getValueByLabel(label).toDoubleOrNull() ?: 0.0
    }
    fun getUnitByLabel(label: String) : String {
        return medicalInfoList.value.find {it.label == label}?.unit ?: ""
    }
    fun initEmptyMedicalInfo(context: Context) {
        _medicalInfoList.value = listOf(
            MedicalInfoElement(
                context.getString(R.string.info_blood_type),
                context.getString(R.string.info_blood_type_placeholder),
                Icons.Outlined.Bloodtype,
                mutableStateOf(""),
                mutableStateOf(true)
            ),
            MedicalInfoElement(
                context.getString(R.string.info_allergies),
                context.getString(R.string.info_allergies_placeholder),
                Icons.Outlined.Warning,
                mutableStateOf(""),
                mutableStateOf(true)
            ),
            MedicalInfoElement(
                context.getString(R.string.info_medicine),
                context.getString(R.string.info_medicine_placeholder),
                Icons.Outlined.Medication,
                mutableStateOf(""),
                mutableStateOf(true)
            ),
            MedicalInfoElement(
                context.getString(R.string.info_height),
                context.getString(R.string.info_height_placeholder),
                Icons.Outlined.Accessibility,
                mutableStateOf(""),
                mutableStateOf(false),
                "cm"
            ),
            MedicalInfoElement(
                context.getString(R.string.info_weight),
                context.getString(R.string.info_weight_placeholder),
                Icons.Outlined.MonitorWeight,
                mutableStateOf(""),
                mutableStateOf(false),
                "kg"
            ),
            MedicalInfoElement(
                context.getString(R.string.info_date_of_birth),
                context.getString(R.string.info_date_of_birth_placeholder),
                Icons.Outlined.Today,
                mutableStateOf("None"),
                mutableStateOf(false)
            ),
            MedicalInfoElement(
                context.getString(R.string.info_additional_notes),
                context.getString(R.string.info_additional_notes_placeholder),
                Icons.Outlined.NoteAlt,
                mutableStateOf("None"),
                mutableStateOf(false)
            )
        )
    }

    fun getUserId() {
        viewModelScope.launch {
            try {
                val response = apiService.getMyInfo()
                if (response.isSuccessful) {
                    response.body()?.userInfo?.let {
                        _userId.value = it.userId
                        originalDisplayName = it.userName
                        updateDisplayName(it.userName)
                    }
                } else {
                    Log.e("getUserId", "응답 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("getUserId", "예외 발생: ${e.message}")
            }
        }
    }

    fun translateInfo(context: Context, userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.translateInfo(TranslateInfoRequest(userId))
                Log.d("translateInfo", "응답 코드: ${response.code()}, 성공 여부: ${response.isSuccessful}")
                Log.d("translateInfo", "responseBody: ${response.body()}")

                val errorBody = response.errorBody()?.string()
                if (errorBody != null) {
                    Log.e("translateInfo", "에러 바디: $errorBody")
                }
                response.body()?.data?.let { data ->
                    updateDisplayName(data.name)
                    _medicalInfoList.value = listOf(
                        MedicalInfoElement(
                            context.getString(R.string.info_blood_type),
                            context.getString(R.string.info_blood_type_placeholder),
                            Icons.Outlined.Bloodtype,
                            mutableStateOf(data.userBloodType),
                            mutableStateOf(true)
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_allergies),
                            context.getString(R.string.info_allergies_placeholder),
                            Icons.Outlined.Warning,
                            mutableStateOf(data.userAllergy),
                            mutableStateOf(true)
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_medicine),
                            context.getString(R.string.info_medicine_placeholder),
                            Icons.Outlined.Medication,
                            mutableStateOf(data.userMedication),
                            mutableStateOf(true)
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_height),
                            context.getString(R.string.info_height_placeholder),
                            Icons.Outlined.Accessibility,
                            mutableStateOf(data.userHeight.toString()),
                            mutableStateOf(data.userHeight != 0.0),
                            data.userHeightUnit
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_weight),
                            context.getString(R.string.info_weight_placeholder),
                            Icons.Outlined.MonitorWeight,
                            mutableStateOf(data.userWeight.toString()),
                            mutableStateOf(data.userWeight != 0.0),
                            data.userWeightUnit
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_date_of_birth),
                            context.getString(R.string.info_date_of_birth_placeholder),
                            Icons.Outlined.Today,
                            mutableStateOf(data.userBirthdate),
                            mutableStateOf(data.userBirthdate != "None")
                        ),
                        MedicalInfoElement(
                            context.getString(R.string.info_additional_notes),
                            context.getString(R.string.info_additional_notes_placeholder),
                            Icons.Outlined.NoteAlt,
                            mutableStateOf(data.userNotes),
                            mutableStateOf(!(data.userNotes.isBlank() || data.userNotes == "없음" || data.userNotes == "None"))
                        )
                    )
                    _isInitialized.value = true
                }
            } catch (e: Exception) {
                Log.e("translateInfo", "예외 발생: ${e.message}")
            }
            _isLoading.value = false
        }
    }
    fun updateDisplayName(name: String) {
        USER_DISPLAY_NAME = name
        _userDisplayName.value = name
    }
}