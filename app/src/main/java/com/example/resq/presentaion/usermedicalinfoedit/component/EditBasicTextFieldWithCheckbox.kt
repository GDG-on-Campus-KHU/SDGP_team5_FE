package com.example.resq.presentaion.usermedicalinfoedit.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R
import com.example.resq.ui.theme.Gray4

// 알레르기, 복용중인 약, 참고사항
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
    val noneText = stringResource(R.string.none)
    LaunchedEffect(state.value) {
        checkboxState.value = state.value == noneText
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = if (state.value == "None") "" else state.value,
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
                    if (state.value.isBlank() || state.value == "None" ) {
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
                state.value = if (checked) noneText else ""
            }
        )
        Text(
            text = noneText,
            fontSize = 12.sp,
        )
    }
}
