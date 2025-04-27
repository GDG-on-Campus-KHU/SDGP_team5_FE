package com.example.resq.presentaion.usermedicalinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.resq.R
import com.example.resq.navigation.user.UserNavigationItem
import com.example.resq.ui.theme.Gray2
import com.example.resq.ui.theme.Gray4

@Composable
fun UserMedicalInfoTab(
    navController: NavController,
    padding: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
    ) {
        val context = LocalContext.current
        val viewModel: UserMedicalInfoViewModel = viewModel()

        LaunchedEffect(Unit) {
            viewModel.initializeMedicalInfoList(context)
        }
        val isEditing = remember { mutableStateOf(false) }
        val tempName = remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .padding(top = 10.dp, start = 20.dp)
        ) {
            Text(
                text = stringResource(R.string.medical_info),
                fontSize = 24.sp
            )
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .padding(top = 5.dp, start = 12.dp)
                    .size(28.dp)
                    .clickable {
                        isEditing.value = true
                        tempName.value = viewModel.medicalInfoList.value.find { it.label == context.getString(R.string.info_name) }?.value?.value ?: ""
                    }
            )
        }

        Divider(
            color = Gray2,
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 18.dp)
        )

        viewModel.medicalInfoList.value.filter { it.show.value }.forEach { element ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                Icon(
                    imageVector = element.icon,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp).padding(top = 7.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = element.label, fontSize = 12.sp)
                    Text(text = element.value.value ?: element.placeholder, fontSize = 12.sp, color = Gray4)
                }
            }
            Divider(modifier = Modifier.padding(vertical = 7.dp, horizontal = 25.dp))
        }

        if (isEditing.value) {
            TextField(
                value = tempName.value,
                onValueChange = { tempName.value = it },
                label = { Text("이름 수정") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.medicalInfoList.value.find { it.label == context.getString(R.string.info_name) }?.value?.value = tempName.value
                        isEditing.value = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("저장")
                }
            }
        }
    }
}
