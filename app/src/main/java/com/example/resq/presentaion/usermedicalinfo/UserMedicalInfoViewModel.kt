package com.example.resq.presentaion.usermedicalinfo

import android.content.Context
import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
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
                MedicalInfoElement(context.getString(R.string.info_height), context.getString(R.string.info_height_placeholder), Icons.Outlined.QuestionMark, mutableStateOf(null), mutableStateOf(false)),
                MedicalInfoElement(context.getString(R.string.info_weight), context.getString(R.string.info_weight_placeholder), Icons.Outlined.QuestionMark, mutableStateOf(null), mutableStateOf(false)),
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
}

//이름, 알레르기, 복용중인 약, 참고사항
@Composable
fun EditBasicTextField(state: MutableState<String>, placeholder: String) {
    val textStyle = TextStyle(
        fontSize = 12.sp,
        color = Gray4,
        lineHeight = 16.sp
    )
    BasicTextField(
        value = state.value,
        onValueChange = { state.value = it },
        textStyle = textStyle,
        modifier = Modifier
            .fillMaxWidth(),
        decorationBox = { innerTextField ->
            Box {
                    Text(
                        text = state.value.ifEmpty { placeholder },
                        style = textStyle
                    )
                innerTextField()
            }
        }
    )
}

//혈액형
@Composable
fun EditBloodType(
    state: MutableState<String>,
    onDismiss: () -> Unit
) {
    val bloodTypes = listOf(
        stringResource(R.string.not_set), "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"
    )

    var selectedOption by remember { mutableStateOf(state.value.ifEmpty { "설정 안 함" }) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.info_blood_type)) },
        text = {
            Column {
                bloodTypes.forEach { bloodType ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == bloodType,
                            onClick = { selectedOption = bloodType }
                        )
                        Text(
                            text = bloodType,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                state.value = when {
                    selectedOption == "설정 안 함" -> ""
                    selectedOption.endsWith("+") -> "RH+ ${selectedOption.dropLast(1)}"
                    selectedOption.endsWith("-") -> "RH- ${selectedOption.dropLast(1)}"
                    else -> selectedOption
                }
                onDismiss()
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

//키
@Composable
fun EditHeight(
    state: MutableState<String>,
    onDismiss: () -> Unit
) {
    val defaultFloat = 160.0f
    val floatValue = state.value.toFloatOrNull() ?: defaultFloat
    var intPart = floatValue.toInt()
    var decimalPart = ((floatValue - intPart) * 10).toInt()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.set_height)) },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                AndroidView(factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 0
                        maxValue = 300
                        value = intPart
                        setOnValueChangedListener { _, _, newVal ->
                            intPart = newVal
                        }
                    }
                })

                Text(text = ".", fontSize = 28.sp, modifier = Modifier.padding(horizontal = 4.dp))

                AndroidView(factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 0
                        maxValue = 9
                        value = decimalPart
                        setOnValueChangedListener { _, _, newVal ->
                            decimalPart = newVal
                        }
                    }
                })

                Text(text = "cm", fontSize = 20.sp, modifier = Modifier.padding(start = 8.dp))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                state.value = (intPart + decimalPart / 10f).toString()
                onDismiss()
            }) {
                Text(stringResource(R.string.done))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

//체중
@Composable
fun EditWeight(state: MutableState<String>, onDismiss: () -> Unit) {
    val defaultFloat = 60.0f
    val floatValue = state.value.toFloatOrNull() ?: defaultFloat
    var intPart = floatValue.toInt()
    var decimalPart = ((floatValue - intPart) * 10).toInt()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.set_weight)) },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                AndroidView(factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 0
                        maxValue = 200
                        value = intPart
                        setOnValueChangedListener { _, _, newVal ->
                            intPart = newVal
                        }
                    }
                })

                Text(text = ".", fontSize = 28.sp, modifier = Modifier.padding(horizontal = 4.dp))

                AndroidView(factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 0
                        maxValue = 9
                        value = decimalPart
                        setOnValueChangedListener { _, _, newVal ->
                            decimalPart = newVal
                        }
                    }
                })

                Text(text = "kg", fontSize = 20.sp, modifier = Modifier.padding(start = 8.dp))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                state.value = (intPart + decimalPart / 10f).toString()
                onDismiss()
            }) {
                Text(stringResource(R.string.done))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

//생년월일
@Composable
fun EditBirthDate(
    state: MutableState<String>,
    onDismiss: () -> Unit
) {
    val (defaultYear, defaultMonth, defaultDay) = Triple(2001, 1, 1)
    var (year, month, day) = when (state.value) {
        "" -> Triple(defaultYear, defaultMonth, defaultDay)
        else -> {
            val splitList = state.value.split("-")
            Triple(splitList[0].toInt(), splitList[1].toInt(), splitList[2].toInt())
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.set_date_of_birth)) },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                AndroidView(factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 1900
                        maxValue = 2025
                        value = year
                        setOnValueChangedListener { _, _, newVal ->
                            year = newVal
                        }
                    }
                })

                AndroidView(factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 1
                        maxValue = 12
                        value = month
                        setOnValueChangedListener { _, _, newVal ->
                            month = newVal
                        }
                    }
                })

                AndroidView(factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 1
                        maxValue = 31
                        value = day
                        setOnValueChangedListener { _, _, newVal ->
                            day = newVal
                        }
                    }
                })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                state.value = "%04d-%02d-%02d".format(year, month, day)
                onDismiss()
            }) {
                Text(stringResource(R.string.done))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}