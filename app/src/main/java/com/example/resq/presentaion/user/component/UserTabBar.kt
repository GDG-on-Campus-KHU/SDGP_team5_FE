package com.example.resq.presentaion.user.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R
import com.example.resq.ui.theme.Gray3
import com.example.resq.ui.theme.InnerPadding
import com.example.resq.ui.theme.MainBlack

@Composable
fun UserTabBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(InnerPadding)
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color.Gray),
            onClick = { onTabSelected(0) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text(
                text = stringResource(R.string.medical_info),
                color = if (selectedTab == 0) MainBlack else Gray3,
                fontSize = 24.sp,
                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
            )
        }
        Spacer(Modifier.width(10.dp))
        Button(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color.Gray),
            onClick = { onTabSelected(1) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
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