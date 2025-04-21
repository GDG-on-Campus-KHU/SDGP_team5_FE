package com.example.resq.presentaion.roomdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TranslationOptions(
    isExpanded: Boolean,
    options: List<Pair<String, String>>,
    onDismissRequest: () -> Unit,
    onClickOption: (String) -> Unit
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier
            .shadow(8.dp)
            .background(color = Color.White),
    ) {
        Column(
            modifier = Modifier
                .heightIn(max = 200.dp)
                .verticalScroll(rememberScrollState())
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onClickOption(option.second)
                        onDismissRequest()
                    },
                    text = { Text(text = option.first) },
                )
            }
        }
    }
}