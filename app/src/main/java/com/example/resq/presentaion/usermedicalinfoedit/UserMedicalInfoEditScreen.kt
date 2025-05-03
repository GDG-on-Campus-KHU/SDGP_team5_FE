package com.example.resq.presentaion.usermedicalinfoedit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.R
import com.example.resq.presentaion.usermedicalinfo.*
import com.example.resq.ui.theme.Gray2
import com.example.resq.ui.theme.Gray4

@Composable
fun UserMedicalInfoEditScreen(
    navController: NavController,
    padding: PaddingValues,
    viewModel: UserMedicalInfoViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.initializeMedicalInfoList(context)
    }
    val medicalInfoList by viewModel.medicalInfoList.collectAsState()

    val nameInput = remember { mutableStateOf(viewModel.getValueByLabel("이름")) }
    val allergyInput = remember { mutableStateOf(viewModel.getValueByLabel("알레르기")) }
    val medicationInput = remember { mutableStateOf(viewModel.getValueByLabel("복용중인 약")) }
    val notesInput = remember { mutableStateOf(viewModel.getValueByLabel("참고사항")) }
    val bloodTypeInput = remember { mutableStateOf(viewModel.getValueByLabel("혈액형")) }
    val heightInput = remember { mutableStateOf(viewModel.getValueByLabel("키")) }
    val weightInput = remember { mutableStateOf(viewModel.getValueByLabel("체중")) }
    val birthDateInput = remember { mutableStateOf(viewModel.getValueByLabel("생년월일")) }
    val showEditBloodType = remember { mutableStateOf(false) }
    val showEditHeight = remember { mutableStateOf(false) }
    val showEditWeight = remember { mutableStateOf(false) }
    val showEditBirthDate = remember { mutableStateOf(false) }
    val allergyNoneChecked = remember { mutableStateOf(allergyInput.value == "없음") }
    val medicationNoneChecked = remember { mutableStateOf(medicationInput.value == "없음") }
    val essentialInfo = listOf(
        stringResource(R.string.info_name),
        stringResource(R.string.info_blood_type),
        stringResource(R.string.info_allergies),
        stringResource(R.string.info_medicine)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(top = 3.dp, start = 5.dp, end = 5.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.medical_info),
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 10.dp, start = 20.dp)
        )
        Text(
            text = stringResource(R.string.info_message_placeholder1),
            fontSize = 14.sp,
            color = Gray4,
            modifier = Modifier.padding(start = 40.dp)
        )
        Row(modifier = Modifier.padding(start = 40.dp, top = 2.dp)) {
            Text(
                text = "*",
                fontSize = 14.sp,
                color = Color.Red
            )
            Text(
                text = stringResource(R.string.info_message1),
                fontSize = 14.sp,
                color = Gray4
            )
        }

        HorizontalDivider(
            color = Gray2,
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 18.dp)
        )

        medicalInfoList.forEach { element ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
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
                    Row(){
                        Text(text = element.label, fontSize = 12.sp)
                        if (element.label in essentialInfo) {
                            Text(
                                text = " *",
                                fontSize = 12.sp,
                                color = Color.Red
                            )
                        }
                    }
                    when (element.label) {
                        "이름" -> EditBasicTextField(nameInput, element.placeholder)
                        "알레르기" -> EditBasicTextFieldWithCheckbox(allergyInput, element.placeholder, allergyNoneChecked)
                        "복용중인 약" -> EditBasicTextFieldWithCheckbox(medicationInput, element.placeholder, medicationNoneChecked)
                        "참고사항" -> EditBasicTextField(notesInput, element.placeholder)
                        else -> Text(
                            text = when (element.label) {
                                "혈액형" -> bloodTypeInput.value.ifBlank { element.placeholder }
                                "키" -> if (heightInput.value.isBlank()) element.placeholder else "${heightInput.value} cm"
                                "체중" -> if (weightInput.value.isBlank()) element.placeholder else "${weightInput.value} kg"
                                "생년월일" -> birthDateInput.value.ifBlank { element.placeholder }
                                else -> element.placeholder
                            },
                            fontSize = 12.sp,
                            color = Gray4,
                            lineHeight = 16.sp,
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
            HorizontalDivider(modifier = Modifier.padding(vertical = 7.dp, horizontal = 25.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.cancel))
            }

            Button(
                onClick = {
                    if (nameInput.value.isBlank() ||
                        bloodTypeInput.value.isBlank() ||
                        allergyInput.value.isBlank() ||
                        medicationInput.value.isBlank()
                    ) {
                        val toast = Toast.makeText(context, context.getString(R.string.toast_message1), Toast.LENGTH_SHORT)
                        toast.show()
                        return@Button
                    }

                    viewModel.updateMedicalInfo(
                        name = nameInput.value,
                        bloodType = bloodTypeInput.value,
                        allergy = allergyInput.value,
                        medication = medicationInput.value,
                        height = heightInput.value,
                        weight = weightInput.value,
                        birthDate = birthDateInput.value,
                        notes = notesInput.value
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.save))
            }
        }

        if (showEditBloodType.value) {
            EditBloodType(bloodTypeInput) { showEditBloodType.value = false }
        }
        if (showEditHeight.value) {
            EditHeight(heightInput) { showEditHeight.value = false }
        }
        if (showEditWeight.value) {
            EditWeight(weightInput) { showEditWeight.value = false }
        }
        if (showEditBirthDate.value) {
            EditBirthDate(birthDateInput) { showEditBirthDate.value = false }
        }
    }
}
