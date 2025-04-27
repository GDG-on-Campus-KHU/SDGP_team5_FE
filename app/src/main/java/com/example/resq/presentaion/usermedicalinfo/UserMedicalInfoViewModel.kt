package com.example.resq.presentaion.usermedicalinfo

import android.content.Context
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.unit.dp

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
    fun updateMedicalInfo(
        name: String,
        bloodType: String,
        allergy: String,
        medication: String,
        height: Float,
        weight: Float,
        birthDate: String,
        notes: String
    ) {
        medicalInfoList.value.forEach { element ->
            when (element.label) {
                "이름" -> element.value.value = name.ifBlank { null }
                "혈액형" -> element.value.value = bloodType.ifBlank { null }
                "알레르기" -> element.value.value = allergy.ifBlank { null }
                "복용중인 약" -> element.value.value = medication.ifBlank { null }
                "키" -> element.value.value = if (height == 0f) null else height.toString()
                "체중" -> element.value.value = if (weight == 0f) null else weight.toString()
                "생년월일" -> element.value.value = birthDate.ifBlank { null }
                "참고사항" -> element.value.value = notes.ifBlank { null }
            }
            element.show.value = element.value.value != null
        }
    }
}

//이름, 알레르기, 복용중인 약, 참고사항
@Composable
fun EditBasicTextField(state: MutableState<String>, placeholder: String) {
    BasicTextField(
        value = state.value,
        onValueChange = { state.value = it },
        textStyle = TextStyle(
            fontSize = 12.sp,
            color = Gray4
        ),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            if (state.value.isEmpty()) {
                Text(
                    text = placeholder,
                    fontSize = 12.sp,
                    color = Gray4
                )
            }
            innerTextField()
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
        "설정 안 함", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"
    )

    var selectedOption by remember { mutableStateOf(state.value.ifEmpty { "설정 안 함" }) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("혈액형") },
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
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}


//키
@Composable
fun EditHeight(state: MutableState<Float>, onDismiss: () -> Unit) {
    var intPart by remember { mutableStateOf(if (state.value == 0f) 160 else state.value.toInt()) }
    var decimalPart by remember { mutableStateOf(if (state.value == 0f) 0 else ((state.value - intPart) * 10).toInt()) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("키 설정") },
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
                state.value = intPart + decimalPart / 10f
                onDismiss()
            }) {
                Text("완료")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

//체중
@Composable
fun EditWeight(state: MutableState<Float>, onDismiss: () -> Unit){
    var intPart by remember { mutableStateOf(if (state.value == 0f) 60 else state.value.toInt()) }
    var decimalPart by remember { mutableStateOf(if (state.value == 0f) 0 else ((state.value - intPart) * 10).toInt()) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("체중 설정") },
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
                state.value = intPart + decimalPart / 10f
                onDismiss()
            }) {
                Text("완료")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
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
    var year by remember { mutableStateOf(2000) }
    var month by remember { mutableStateOf(1) }
    var day by remember { mutableStateOf(1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("생년월일 설정") },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                AndroidView(factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 1900
                        maxValue = 2100
                        value = year
                        setOnValueChangedListener { _, _, newVal ->
                            year = newVal
                        }
                    }
                })

                Text(text = "년", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 4.dp))

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

                Text(text = "월", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 4.dp))

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

                Text(text = "일", fontSize = 20.sp, modifier = Modifier.padding(start = 4.dp))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // 선택된 값을 "yyyy-MM-dd" 형태로 저장
                state.value = "%04d-%02d-%02d".format(year, month, day)
                onDismiss()
            }) {
                Text("완료")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}