package com.example.resq.presentaion.usermedicalinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.R
import com.example.resq.ui.theme.Gray2
import com.example.resq.ui.theme.Gray4

@Composable
fun UserMedicalInfoTab(
    navController: NavController,
    padding: PaddingValues,
    viewModel: UserMedicalInfoViewModel
) {
    val context = LocalContext.current
    val medicalInfoList by viewModel.medicalInfoList.collectAsState()
    val isEditing = remember { mutableStateOf(false) }
    val nameInput = remember { mutableStateOf("") }
    val allergyInput = remember { mutableStateOf("") }
    val medicationInput = remember { mutableStateOf("") }
    val notesInput = remember { mutableStateOf("") }
    val bloodTypeInput = remember { mutableStateOf("") }
    val heightInput = remember{ mutableFloatStateOf(0f) }
    val weightInput = remember{ mutableFloatStateOf(0f) }
    val birthDateInput = remember{ mutableStateOf("") }
    val showEditBloodType = remember { mutableStateOf(false) }
    val showEditHeight = remember { mutableStateOf(false) }
    val showEditWeight = remember { mutableStateOf(false) }
    val showEditBirthDate = remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
        viewModel.initializeMedicalInfoList(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.padding(top = 10.dp, start = 20.dp)
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
                    .clickable { isEditing.value = true }
            )
        }

        HorizontalDivider(color = Gray2, modifier = Modifier.padding(vertical = 7.dp, horizontal = 18.dp))

        val itemsToDisplay = if (isEditing.value) {
            medicalInfoList
        } else {
            medicalInfoList.filter { it.show.value }
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
                        when (element.label) {
                            "이름" -> EditBasicTextField(nameInput, element.placeholder)
                            "알레르기" -> EditBasicTextField(allergyInput, element.placeholder)
                            "복용중인 약" -> EditBasicTextField(medicationInput, element.placeholder)
                            "참고사항" -> EditBasicTextField(notesInput, element.placeholder)
                            else -> {
                                Text(
                                    text = element.value.value ?: element.placeholder,
                                    fontSize = 12.sp,
                                    color = Gray4,
                                    modifier = Modifier.clickable {
                                        when (element.label) {
                                            "혈액형" -> showEditBloodType.value = true
                                            "키" -> showEditHeight.value = true
                                            "체중" -> showEditWeight.value = true
                                            "생년월일" -> showEditBirthDate.value = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 7.dp, horizontal = 25.dp))
        }

        if (isEditing.value) {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    viewModel.updateMedicalInfo(
                        name = nameInput.value,
                        bloodType = bloodTypeInput.value,
                        allergy = allergyInput.value,
                        medication = medicationInput.value,
                        height = heightInput.floatValue,
                        weight = weightInput.floatValue,
                        birthDate = birthDateInput.value,
                        notes = notesInput.value
                    )
                    isEditing.value = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text("저장")
            }
        }
        if (showEditBloodType.value) {
            EditBloodType(bloodTypeInput, {showEditBloodType.value = false})
        }
        if (showEditHeight.value) {
            EditHeight(heightInput, { showEditHeight.value = false })
        }
        if (showEditWeight.value) {
            EditWeight(weightInput, {showEditWeight.value = false})
        }
        if (showEditBirthDate.value) {
            EditBirthDate(birthDateInput, {showEditBirthDate.value = false})
        }
    }
}

