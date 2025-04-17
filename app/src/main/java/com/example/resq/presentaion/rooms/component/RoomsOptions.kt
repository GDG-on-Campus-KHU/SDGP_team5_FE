package com.example.resq.presentaion.rooms.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
fun RoomsOptions(onClick: (String) -> Unit) {
    val roomDelete = stringResource(R.string.room_delete)
    val roomOut = stringResource(R.string.room_out)

    Column {
        Card(
            modifier = Modifier
                .widthIn(min = 100.dp)
                .clickable(
                    onClick = { onClick(roomDelete) },
                    interactionSource = null,
                    indication = null
                )
        ) {
            Text(
                text = roomDelete,
                modifier = Modifier.padding(4.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .widthIn(min = 100.dp)
                .clickable(
                    onClick = { onClick(roomOut) },
                    interactionSource = null,
                    indication = null
                )
        ) {
            Text(
                text = roomOut,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}