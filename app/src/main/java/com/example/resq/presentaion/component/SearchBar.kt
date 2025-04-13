package com.example.resq.presentaion.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(
    searchText: String,
    onValueChange:(String) -> Unit
) {
    Row {
        OutlinedTextField(
            value = searchText,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = "검색할 병이나 증상을 입력하세요.") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        // 검색 결과 화면으로 이동
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            }
        )
    }
}