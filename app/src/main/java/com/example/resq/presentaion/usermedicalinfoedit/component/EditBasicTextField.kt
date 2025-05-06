package com.example.resq.presentaion.usermedicalinfoedit.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
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