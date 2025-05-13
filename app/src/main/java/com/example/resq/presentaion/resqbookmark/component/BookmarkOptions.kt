package com.example.resq.presentaion.resqbookmark.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.resq.R

@Composable
fun BookmarkOptions(onClick: (String) -> Unit) {
    val delete = stringResource(R.string.delete)

    Card(
        modifier = Modifier
            .widthIn(min = 100.dp)
            .clickable(
                onClick = { onClick(delete) },
                interactionSource = null,
                indication = null
            )
    ) {
        Text(
            text = stringResource(R.string.cancel),
            modifier = Modifier.padding(4.dp)
        )
    }
}