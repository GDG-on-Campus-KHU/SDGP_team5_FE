package com.example.resq.presentaion.user

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.MainActivity.Companion.USER_DISPLAY_NAME
import com.example.resq.presentaion.user.component.UserTabBar
import com.example.resq.presentaion.usermedicalinfo.UserMedicalInfoTab
import com.example.resq.presentaion.usermedicalinfo.UserMedicalInfoViewModel
import com.example.resq.presentaion.userrecordlist.UserRecordList
import com.example.resq.ui.theme.Gray4

@Composable
fun UserScreen(
    navController: NavController,
    padding: PaddingValues,
) {
    val googleName = "user"//추후 USER_DISPLAY_NAME
    val googleEmail = "melon@gmail.com"//추후 USER_EMAIL
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val viewModel: UserMedicalInfoViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.getInfo(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 26.dp, start = 14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account",
                modifier = Modifier.size(70.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = googleName,
                        fontSize = 24.sp,
                        modifier = Modifier.wrapContentSize())
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .padding(top = 5.dp, start = 12.dp)
                            .size(28.dp)
                            .clickable(
                                onClick = {
                                    //이름 고치기 기능 구현 예정
                                }

                            )
                    )
                }
                Text(
                    text = googleEmail,
                    fontSize = 14.sp,
                    color = Gray4,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
        }
        HorizontalDivider(
            color = Color.Black,
            modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 26.dp)
        )

        UserTabBar(selectedTab = selectedTab) {
            selectedTab = it
        }
        when (selectedTab) {
            0 -> UserMedicalInfoTab(navController, padding, viewModel)
            1 -> UserRecordList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    UserScreen(rememberNavController(), PaddingValues(0.dp))
}