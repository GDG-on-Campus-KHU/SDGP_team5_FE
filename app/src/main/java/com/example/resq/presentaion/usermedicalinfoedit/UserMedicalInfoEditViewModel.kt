package com.example.resq.presentaion.usermedicalinfoedit

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.resq.R
import com.example.resq.ui.theme.Gray4

//이름, 참고사항
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
//알레르기, 복용중인 약
@Composable
fun EditBasicTextFieldWithCheckbox(
    state: MutableState<String>,
    placeholder: String,
    checkboxState: MutableState<Boolean>
) {
    val textStyle = TextStyle(
        fontSize = 12.sp,
        color = Gray4,
        lineHeight = 16.sp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = state.value,
            onValueChange = { state.value = it },
            enabled = !checkboxState.value,
            textStyle = textStyle,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (state.value.isEmpty()) {
                        Text(text = placeholder, style = textStyle)
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Checkbox(
            checked = checkboxState.value,
            onCheckedChange = { checked ->
                checkboxState.value = checked
                state.value = if (checked) "없음" else ""
            }
        )
        Text(
            text = "없음",
            fontSize = 12.sp,
        )
    }
}

//혈액형
@Composable
fun EditBloodType(
    state: MutableState<String>,
    onDismiss: () -> Unit
) {
    val bloodTypes = listOf(
        "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"
    )

    var selectedOption by remember { mutableStateOf("") }

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
                if (selectedOption.isNotBlank()) {
                    state.value = when {
                        selectedOption.endsWith("+") -> "RH+ ${selectedOption.dropLast(1)}"
                        selectedOption.endsWith("-") -> "RH- ${selectedOption.dropLast(1)}"
                        else -> selectedOption
                    }
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