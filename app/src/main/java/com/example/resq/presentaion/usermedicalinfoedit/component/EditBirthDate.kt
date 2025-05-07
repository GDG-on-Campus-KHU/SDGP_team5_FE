package com.example.resq.presentaion.usermedicalinfoedit.component

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.resq.R

//생년월일
@Composable
fun EditBirthDate(
    state: MutableState<String>,
    onDismiss: () -> Unit
) {
    val (defaultYear, defaultMonth, defaultDay) = Triple(2001, 1, 1)
    var (year, month, day) = if (state.value.isBlank() || state.value == "None") {
        Triple(defaultYear, defaultMonth, defaultDay)
    } else {
        val splitList = state.value.split("-")
        Triple(splitList[0].toInt(), splitList[1].toInt(), splitList[2].toInt())
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