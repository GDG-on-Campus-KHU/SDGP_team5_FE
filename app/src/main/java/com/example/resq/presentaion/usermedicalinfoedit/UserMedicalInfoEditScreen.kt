package com.example.resq.presentaion.usermedicalinfoedit

import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.resq.R
import com.example.resq.presentaion.usermedicalinfo.*
import com.example.resq.network.model.MedicalInfoRequest
import com.example.resq.presentaion.usermedicalinfoedit.component.EditBasicTextFieldWithCheckbox
import com.example.resq.presentaion.usermedicalinfoedit.component.EditBirthDate
import com.example.resq.presentaion.usermedicalinfoedit.component.EditBloodType
import com.example.resq.presentaion.usermedicalinfoedit.component.EditHeight
import com.example.resq.presentaion.usermedicalinfoedit.component.EditWeight
import com.example.resq.ui.theme.Gray2
import com.example.resq.ui.theme.Gray4

@Composable
fun UserMedicalInfoEditScreen(
    navController: NavController,
    padding: PaddingValues,
    viewModel: UserMedicalInfoViewModel = viewModel()
) {
    val context = LocalContext.current
    val medicalInfoList by viewModel.medicalInfoList.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getInfo(context)
        if (viewModel.medicalInfoList.value.isEmpty()) {
            viewModel.initEmptyMedicalInfo(context)
        }
    }
    val allergyInput = remember(medicalInfoList) { mutableStateOf(viewModel.getValueByLabel(context.getString(R.string.info_allergies))) }
    val medicationInput = remember(medicalInfoList) { mutableStateOf(viewModel.getValueByLabel(context.getString(R.string.info_medicine))) }
    val notesInput = remember(medicalInfoList) { mutableStateOf(viewModel.getValueByLabel(context.getString(R.string.info_additional_notes))) }
    val bloodTypeInput = remember(medicalInfoList) { mutableStateOf(viewModel.getValueByLabel(context.getString(R.string.info_blood_type))) }
    val heightInput = remember(medicalInfoList) { mutableDoubleStateOf(viewModel.getValueByLabelAsDouble(context.getString(R.string.info_height))) }
    val weightInput = remember(medicalInfoList) { mutableDoubleStateOf(viewModel.getValueByLabelAsDouble(context.getString(R.string.info_weight))) }
    val birthDateInput = remember(medicalInfoList) { mutableStateOf(viewModel.getValueByLabel(context.getString(R.string.info_date_of_birth))) }
    val heightUnitInput = remember(medicalInfoList) { mutableStateOf(viewModel.getUnitByLabel(context.getString(R.string.info_height)).ifBlank { "cm" }) }
    val weightUnitInput = remember(medicalInfoList){ mutableStateOf(viewModel.getUnitByLabel(context.getString(R.string.info_weight)).ifBlank { "kg" }) }
    val showEditBloodType = remember { mutableStateOf(false) }
    val showEditHeight = remember { mutableStateOf(false) }
    val showEditWeight = remember { mutableStateOf(false) }
    val showEditBirthDate = remember { mutableStateOf(false) }
    val allergyNoneChecked = remember { mutableStateOf(allergyInput.value == "없음") }
    val medicationNoneChecked = remember { mutableStateOf(medicationInput.value == "없음") }
    val notesNoneChecked = remember { mutableStateOf(notesInput.value == context.getString(R.string.none)) }
    val essentialInfo = listOf(
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

        HorizontalDivider(color = Gray2, modifier = Modifier.padding(vertical = 7.dp, horizontal = 18.dp))

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
                        context.getString(R.string.info_allergies) -> EditBasicTextFieldWithCheckbox(allergyInput, element.placeholder, allergyNoneChecked)
                        context.getString(R.string.info_medicine) -> EditBasicTextFieldWithCheckbox(medicationInput, element.placeholder, medicationNoneChecked)
                        context.getString(R.string.info_additional_notes) -> EditBasicTextFieldWithCheckbox(notesInput, element.placeholder, notesNoneChecked)
                        else -> Text(
                            text = when (element.label) {
                                context.getString(R.string.info_blood_type) -> bloodTypeInput.value.ifBlank { element.placeholder }
                                context.getString(R.string.info_height) -> if (heightInput.doubleValue == 0.0) element.placeholder else "${heightInput.doubleValue} ${heightUnitInput.value}"
                                context.getString(R.string.info_weight) -> if (weightInput.doubleValue == 0.0) element.placeholder else "${weightInput.doubleValue} ${weightUnitInput.value}"
                                context.getString(R.string.info_date_of_birth) -> birthDateInput.value.takeIf { it.isNotBlank() && it != "None" } ?: element.placeholder
                                else -> element.placeholder
                            },
                            fontSize = 12.sp,
                            color = Gray4,
                            lineHeight = 16.sp,
                            modifier = Modifier.clickable {
                                when (element.label) {
                                    context.getString(R.string.info_blood_type) -> showEditBloodType.value = true
                                    context.getString(R.string.info_height) -> showEditHeight.value = true
                                    context.getString(R.string.info_weight) -> showEditWeight.value = true
                                    context.getString(R.string.info_date_of_birth) -> showEditBirthDate.value = true
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
                    if (bloodTypeInput.value.isBlank() ||
                        allergyInput.value.isBlank() ||
                        medicationInput.value.isBlank()
                    ) {
                        Toast.makeText(context, context.getString(R.string.toast_message1), Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }
                    val request = MedicalInfoRequest(
                        bloodType = bloodTypeInput.value,
                        allergy = allergyInput.value,
                        medication = medicationInput.value,
                        height = heightInput.doubleValue,
                        heightUnit = heightUnitInput.value,
                        weight = weightInput.doubleValue,
                        weightUnit = weightUnitInput.value,
                        birthDate = birthDateInput.value,
                        notes = notesInput.value
                    )
                    Log.d("SaveRequest", "Request 데이터: $request")
                    val isNew = !viewModel.isInitialized.value
                    val action = if (isNew) viewModel::newInfo else viewModel::editInfo
                    action(request) { success ->
                        if (success) {
                            Toast.makeText(context, context.getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Log.d("UserMedicalInfo", "저장 실패")
                        }
                    }
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
            EditHeight(heightInput, heightUnitInput) { showEditHeight.value = false }
        }
        if (showEditWeight.value) {
            EditWeight(weightInput, weightUnitInput) { showEditWeight.value = false }
        }
        if (showEditBirthDate.value) {
            EditBirthDate(birthDateInput) { showEditBirthDate.value = false }
        }
    }
}