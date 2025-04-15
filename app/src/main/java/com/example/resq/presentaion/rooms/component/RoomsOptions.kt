package com.example.resq.presentaion.rooms.component

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedBoxWithConstraintsScope", "NewApi")
@Composable
fun RoomsOptions(onClick: (String) -> Unit) {
    Column {
        Card(
            modifier = Modifier
                .widthIn(min = 100.dp)
                .clickable(
                    onClick = { onClick("방 삭제하기") },
                    interactionSource = null,
                    indication = null
                )
        ) {
            Text(
                text = "방 삭제하기",
                modifier = Modifier.padding(4.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .widthIn(min = 100.dp)
                .clickable(
                    onClick = { onClick("방 나가기") },
                    interactionSource = null,
                    indication = null
                )
        ) {
            Text(
                text = "방 나가기",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}