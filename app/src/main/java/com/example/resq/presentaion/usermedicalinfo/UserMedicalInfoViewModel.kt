package com.example.resq.presentaion.usermedicalinfo

import android.content.Context
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Accessibility
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import com.example.resq.R
import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfoElement
import com.example.resq.ui.theme.Gray4
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

