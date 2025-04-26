package com.example.resq.presentaion.usermedicalinfo

import android.content.Context
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

    fun getMedicalInfoList(context: Context): List<MedicalInfoElement> {
        return listOf(
            MedicalInfoElement(label = context.getString(R.string.info_name), placeholder = context.getString(R.string.info_name_placeholder), icon = Icons.Outlined.Person, show = mutableStateOf(true)),
            MedicalInfoElement(label = context.getString(R.string.info_blood_type), placeholder = context.getString(R.string.info_blood_type_placeholder), icon = Icons.Outlined.Bloodtype, show = mutableStateOf(true)),
            MedicalInfoElement(label = context.getString(R.string.info_allergies), placeholder = context.getString(R.string.info_allergies_placeholder), icon = Icons.Outlined.Warning, show = mutableStateOf(true)),
            MedicalInfoElement(label = context.getString(R.string.info_medicine), placeholder = context.getString(R.string.info_medicine_placeholder), icon = Icons.Outlined.Medication, show = mutableStateOf(true)),
            MedicalInfoElement(label = context.getString(R.string.info_height), placeholder = context.getString(R.string.info_height_placeholder), icon = Icons.Outlined.QuestionMark),
            MedicalInfoElement(label = context.getString(R.string.info_weight), placeholder = context.getString(R.string.info_weight_placeholder), icon = Icons.Outlined.QuestionMark),
            MedicalInfoElement(label = context.getString(R.string.info_date_of_birth), placeholder = context.getString(R.string.info_date_of_birth_placeholder), icon = Icons.Outlined.Today),
            MedicalInfoElement(label = context.getString(R.string.info_additional_notes), placeholder = context.getString(R.string.info_additional_notes_placeholder), icon = Icons.Outlined.NoteAlt)
        )
    }
}