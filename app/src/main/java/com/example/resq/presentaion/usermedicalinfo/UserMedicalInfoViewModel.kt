package com.example.resq.presentaion.usermedicalinfo

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.resq.R
import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfoElement

class UserMedicalInfoViewModel : ViewModel() {
    var medicalInfoList = mutableStateOf<List<MedicalInfoElement>>(emptyList())
        private set

    fun initializeMedicalInfoList(context: Context) {
        if (medicalInfoList.value.isEmpty()) {
            medicalInfoList.value = listOf(
                MedicalInfoElement(context.getString(R.string.info_name), context.getString(R.string.info_name_placeholder), Icons.Outlined.Person, mutableStateOf(null), mutableStateOf(true)),
                MedicalInfoElement(context.getString(R.string.info_blood_type), context.getString(R.string.info_blood_type_placeholder), Icons.Outlined.Bloodtype, mutableStateOf(null), mutableStateOf(true)),
                MedicalInfoElement(context.getString(R.string.info_allergies), context.getString(R.string.info_allergies_placeholder), Icons.Outlined.Warning, mutableStateOf(null), mutableStateOf(true)),
                MedicalInfoElement(context.getString(R.string.info_medicine), context.getString(R.string.info_medicine_placeholder), Icons.Outlined.Medication, mutableStateOf(null), mutableStateOf(true)),
                MedicalInfoElement(context.getString(R.string.info_height), context.getString(R.string.info_height_placeholder), Icons.Outlined.QuestionMark, mutableStateOf(null), mutableStateOf(false)),
                MedicalInfoElement(context.getString(R.string.info_weight), context.getString(R.string.info_weight_placeholder), Icons.Outlined.QuestionMark, mutableStateOf(null), mutableStateOf(false)),
                MedicalInfoElement(context.getString(R.string.info_date_of_birth), context.getString(R.string.info_date_of_birth_placeholder), Icons.Outlined.Today, mutableStateOf(null), mutableStateOf(false)),
                MedicalInfoElement(context.getString(R.string.info_additional_notes), context.getString(R.string.info_additional_notes_placeholder), Icons.Outlined.NoteAlt, mutableStateOf(null), mutableStateOf(false))
            )
        }
    }
}