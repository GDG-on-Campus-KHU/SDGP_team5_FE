package com.example.resq.presentaion.usermedicalinfo

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Accessibility
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.resq.R
import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfoElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserMedicalInfoViewModel : ViewModel() {
    private val _medicalInfoList = MutableStateFlow<List<MedicalInfoElement>>(emptyList())
    val medicalInfoList: StateFlow<List<MedicalInfoElement>> = _medicalInfoList.asStateFlow()

    fun initializeMedicalInfoList(context: Context) {
        if (_medicalInfoList.value.isEmpty()) {
            _medicalInfoList.value = listOf(
                MedicalInfoElement(context.getString(R.string.info_name), context.getString(R.string.info_name_placeholder), Icons.Outlined.Person, mutableStateOf(null), mutableStateOf(true)),
                MedicalInfoElement(context.getString(R.string.info_blood_type), context.getString(R.string.info_blood_type_placeholder), Icons.Outlined.Bloodtype, mutableStateOf(null), mutableStateOf(true)),
                MedicalInfoElement(context.getString(R.string.info_allergies), context.getString(R.string.info_allergies_placeholder), Icons.Outlined.Warning, mutableStateOf(null), mutableStateOf(true)),
                MedicalInfoElement(context.getString(R.string.info_medicine), context.getString(R.string.info_medicine_placeholder), Icons.Outlined.Medication, mutableStateOf(null), mutableStateOf(true)),
                MedicalInfoElement(context.getString(R.string.info_height), context.getString(R.string.info_height_placeholder), Icons.Outlined.Accessibility, mutableStateOf(null), mutableStateOf(false)),
                MedicalInfoElement(context.getString(R.string.info_weight), context.getString(R.string.info_weight_placeholder), Icons.Outlined.MonitorWeight, mutableStateOf(null), mutableStateOf(false)),
                MedicalInfoElement(context.getString(R.string.info_date_of_birth), context.getString(R.string.info_date_of_birth_placeholder), Icons.Outlined.Today, mutableStateOf(null), mutableStateOf(false)),
                MedicalInfoElement(context.getString(R.string.info_additional_notes), context.getString(R.string.info_additional_notes_placeholder), Icons.Outlined.NoteAlt, mutableStateOf(null), mutableStateOf(false))
            )
        }
    }

    fun updateMedicalInfo(
        name: String,
        bloodType: String,
        allergy: String,
        medication: String,
        height: String,
        weight: String,
        birthDate: String,
        notes: String
    ) {
        medicalInfoList.value.forEach { element ->
            when (element.label) {
                "이름" -> element.value.value = name.ifBlank { null }
                "혈액형" -> element.value.value = bloodType.ifBlank { null }
                "알레르기" -> element.value.value = allergy.ifBlank { null }
                "복용중인 약" -> element.value.value = medication.ifBlank { null }
                "키" -> element.value.value = height.ifBlank {null}
                "체중" -> element.value.value = weight.ifBlank {null}
                "생년월일" -> element.value.value = birthDate.ifBlank { null }
                "참고사항" -> element.value.value = notes.ifBlank { null }
            }
            if (element.label in listOf("키", "체중", "생년월일", "참고사항")) {
                element.show.value = !element.value.value.isNullOrEmpty()
            }
        }
    }
    fun getValueByLabel(label: String): String {
        return medicalInfoList.value.find { it.label == label }?.value?.value ?: ""
    }
}

