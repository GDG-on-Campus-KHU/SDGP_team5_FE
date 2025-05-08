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
import com.example.resq.R
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfoElement
import com.example.resq.network.model.MedicalInfoRequest
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

    fun getInfo(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getInfo()
                Log.d("getInfo", "API 응답 성공 여부: ${response.isSuccessful}")
                Log.e("getInfo", "HTTP 상태 코드: ${response.code()}, 메시지: ${response.message()}")
                Log.d("getInfo", "responsebody: ${response.body()}")

                response.body()?.medicalInfo?.let { data ->
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
                Log.e("getInfo", "예외: ${e.message}")
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
                val success = response.isSuccessful && response.body()?.boolean == true
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
                val errorBody = response.errorBody()?.string()
                Log.e("editInfo", "에러 바디: $errorBody")
                val success = response.isSuccessful && response.body()?.boolean == true
                onResult(success)
            } catch (e: Exception) {
                Log.d("editInfo", e.message.toString())
                onResult(false)
            }
            _isLoading.value = false
        }
    }

    fun getValueByLabel(label: String): String {
        return medicalInfoList.value.find { it.label == label }?.value?.value ?: "None"
    }

    fun getValueByLabelAsDouble(label: String): Double {
        return getValueByLabel(label).toDoubleOrNull() ?: 0.0
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
                mutableStateOf(false)
            ),
            MedicalInfoElement(
                context.getString(R.string.info_weight),
                context.getString(R.string.info_weight_placeholder),
                Icons.Outlined.MonitorWeight,
                mutableStateOf(""),
                mutableStateOf(false)
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
}