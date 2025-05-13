package com.example.resq.presentaion.usermedicalinfoedit.component

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.resq.R

@Composable
fun EditWeight(
    state: MutableState<Double>,
    unitState: MutableState<String>,
    onDismiss: () -> Unit
) {
    val defaultWeight = 60.0
    val floatValue = if (state.value == 0.0) defaultWeight else state.value
    val intPart = remember { mutableIntStateOf(floatValue.toInt()) }
    val decimalPart = remember { mutableIntStateOf(((floatValue - intPart.intValue) * 10).toInt()) }

    val units = arrayOf("kg", "lb")
    val unitIndex = remember { mutableIntStateOf(if (unitState.value == "kg") 0 else 1) }
    var previousUnitIndex by remember { mutableIntStateOf(unitIndex.intValue) }
    val maxInt by remember(unitIndex.intValue) { mutableIntStateOf(if (unitIndex.intValue == 0) 200 else 450) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.set_weight)) },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                key(unitIndex.intValue) {
                    AndroidView(factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = maxInt
                            value = intPart.intValue
                            setOnValueChangedListener { _, _, newVal ->
                                intPart.intValue = newVal
                            }
                        }
                    })
                }

                Text(".", fontSize = 28.sp, modifier = Modifier.padding(horizontal = 4.dp))

                key(unitIndex.intValue) {
                    AndroidView(factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = 9
                            value = decimalPart.intValue
                            setOnValueChangedListener { _, _, newVal ->
                                decimalPart.intValue = newVal
                            }
                        }
                    })
                }

                Text(" ", fontSize = 28.sp, modifier = Modifier.padding(horizontal = 4.dp))

                key(unitIndex.intValue) {
                    AndroidView(factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = 1
                            value = unitIndex.intValue
                            displayedValues = units
                            setOnValueChangedListener { _, _, newVal ->
                                previousUnitIndex = unitIndex.intValue
                                unitIndex.intValue = newVal

                                val current = intPart.intValue + decimalPart.intValue / 10.0
                                val converted = when {
                                    previousUnitIndex == 0 && newVal == 1 -> current * 2.20462
                                    previousUnitIndex == 1 && newVal == 0 -> current / 2.20462
                                    else -> current
                                }
                                val limited = if (newVal == 0 && converted > 200.0) {
                                    200.0
                                } else if (newVal == 1 && converted > 450.0) {
                                    450.0
                                } else {
                                    converted
                                }

                                intPart.intValue = limited.toInt()
                                decimalPart.intValue = ((limited - intPart.intValue) * 10).toInt()
                            }
                        }
                    })
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                state.value = intPart.intValue + decimalPart.intValue / 10.0
                unitState.value = units[unitIndex.intValue]
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
