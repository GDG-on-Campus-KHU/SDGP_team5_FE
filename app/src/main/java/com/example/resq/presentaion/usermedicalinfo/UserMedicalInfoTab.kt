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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.R
import com.example.resq.navigation.user.UserNavigationItem
import com.example.resq.presentaion.user.UserScreen
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
            .verticalScroll(rememberScrollState())
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
                    }
            )
        }

        Divider(
            color = Gray2,
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 18.dp)
        )

        val itemsToDisplay = if (isEditing.value) {
            viewModel.medicalInfoList.value
        } else {
            viewModel.medicalInfoList.value.filter { it.show.value }
        }

        itemsToDisplay.forEach { element ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                Icon(
                    imageVector = element.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(top = 7.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = element.label,
                        fontSize = 12.sp
                    )

                    if (!isEditing.value) {
                        Text(
                            text = element.value.value ?: element.placeholder,
                            fontSize = 12.sp,
                            color = Gray4
                        )
                    } else {
                        if (element.label in listOf("이름", "알레르기", "복용중인 약", "참고사항")) {
                            BasicTextField(
                                value = element.value.value ?: "",
                                onValueChange = { element.value.value = it },
                                textStyle = TextStyle(
                                    fontSize = 12.sp,
                                    color = Gray4
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                decorationBox = { innerTextField ->
                                    if (element.value.value.isNullOrEmpty()) {
                                        Text(
                                            text = element.placeholder,
                                            fontSize = 12.sp,
                                            color = Gray4
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                        } else {
                            Text(
                                text = element.value.value ?: element.placeholder,
                                fontSize = 12.sp,
                                color = Gray4,
                                modifier = Modifier
                                    .clickable {
                                        // AllertDialog 함수 넣기
                                    }
                            )
                        }
                    }
                }
            }
            Divider(modifier = Modifier.padding(vertical = 7.dp, horizontal = 25.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserMedicalInfoPreview() {
    UserMedicalInfoTab(rememberNavController(), PaddingValues(0.dp))
}