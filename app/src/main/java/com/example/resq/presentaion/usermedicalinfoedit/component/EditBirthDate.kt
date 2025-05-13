package com.example.resq.presentaion.usermedicalinfoedit.component

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext

//생년월일
@Composable
fun EditBirthDate(
    state: MutableState<String>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val (defaultYear, defaultMonth, defaultDay) = if (state.value.isBlank() || state.value == "None") {
        Triple(2001, 0, 1)
    } else {
        val split = state.value.split("-")
        Triple(split[0].toInt(), split[1].toInt() - 1, split[2].toInt())
    }

    DatePickerDialog(
        context,
        { _, year, month, day ->
            state.value = "%04d-%02d-%02d".format(year, month + 1, day)
            onDismiss()
        },
        defaultYear,
        defaultMonth,
        defaultDay
    ).apply {
        setOnCancelListener { onDismiss() }
    }.show()
}