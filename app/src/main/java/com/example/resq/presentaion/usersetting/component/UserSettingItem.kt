package com.example.resq.presentaion.usersetting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.resq.R

@Composable
fun SettingItem(
    text: String,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    val modifier = Modifier
        .fillMaxWidth()
        .then(
            if (onClick != null && enabled) Modifier.clickable { onClick() } else Modifier
        )
        .padding(horizontal = 16.dp, vertical = 12.dp)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
        if (onClick != null && enabled) {
            Icon(
                painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null
            )
        }
    }
}
