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
import androidx.compose.ui.res.stringResource
import com.example.resq.R

@Composable
fun SearchBar(
    searchText: String,
    onValueChange: (String) -> Unit,
    onClickSearch: () -> Unit
) {
    Row {
        OutlinedTextField(
            value = searchText,
            onValueChange = { if (it.length <= 20) onValueChange(it) },
            modifier = Modifier.weight(1f),
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            trailingIcon = {
                IconButton(onClick = { onClickSearch() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
            maxLines = 1
        )
    }
}