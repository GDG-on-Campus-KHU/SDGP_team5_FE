package com.example.resq.presentaion.resqbookmark.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BookmarkOptions(
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(min = 100.dp)
            .clickable(
                onClick = { onClick("삭제하기") },
                interactionSource = null,
                indication = null
            )
    ) {
        Text(
            text = "삭제하기",
            modifier = Modifier.padding(4.dp)
        )
    }
}