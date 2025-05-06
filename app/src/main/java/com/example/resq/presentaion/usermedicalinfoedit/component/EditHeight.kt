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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.resq.R

//키
@Composable
fun EditHeight(
    state: MutableState<Double>,
    unitState: MutableState<String>,
    onDismiss: () -> Unit
) {
    val defaultHeight = 160.0
    val floatValue = if (state.value == 0.0) defaultHeight else state.value
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
                state.value = (intPart + decimalPart / 10.0)
                unitState.value = "cm"
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