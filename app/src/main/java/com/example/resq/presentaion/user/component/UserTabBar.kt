package com.example.resq.presentaion.user.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R
import com.example.resq.ui.theme.Gray3
import com.example.resq.ui.theme.MainBlack

@Composable
fun UserTabBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 55.dp)
                .clickable { onTabSelected(0) },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(R.string.medical_info),
                color = if (selectedTab == 0) MainBlack else Gray3,
                fontSize = 24.sp,
                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 55.dp)
                .clickable { onTabSelected(1) },
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = stringResource(R.string.record_list),
                color = if (selectedTab == 1) MainBlack else Gray3,
                fontSize = 24.sp,
                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}