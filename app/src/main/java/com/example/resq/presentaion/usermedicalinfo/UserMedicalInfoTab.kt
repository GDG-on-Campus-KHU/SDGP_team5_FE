package com.example.resq.presentaion.usermedicalinfo

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.R
import com.example.resq.presentaion.component.CenterBlankText
import com.example.resq.presentaion.usermedicalinfo.component.showBloodType
import com.example.resq.presentaion.usermedicalinfo.component.showUnit
import com.example.resq.ui.theme.Gray2
import com.example.resq.ui.theme.Gray4
import com.example.resq.ui.theme.IsTranslated

@Composable
fun UserMedicalInfoTab(
    navController: NavController,
    viewModel: UserMedicalInfoViewModel
) {
    val medicalInfoList by viewModel.medicalInfoList.collectAsState()
    val context = LocalContext.current
    val userId by viewModel.userId.collectAsState()
    val isTranslated = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getUserId()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {
                navController.navigate("user_medical_info_edit")
            }) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit",
                )
            }
            IconButton(onClick = {
                userId?.let {
                    if (isTranslated.value) {
                        viewModel.getInfo(context)
                    } else {
                        viewModel.translateInfo(context, it)
                    }
                    isTranslated.value = !isTranslated.value
                } ?: Log.d("translateInfo", "userId 로드 오류")
            }) {
                Icon(
                    imageVector = Icons.Outlined.Language,
                    contentDescription = "Language",
                    tint = if (isTranslated.value) IsTranslated else Color.Black,
                )
            }
        }

        HorizontalDivider(
            color = Gray2,
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 18.dp)
        )

        if (medicalInfoList.isEmpty())
            CenterBlankText(stringResource(R.string.no_medical_info))
        else
            medicalInfoList.filter { it.show.value }.forEach { element ->
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
                        Text(
                            text = when (element.label) {
                                stringResource(R.string.info_blood_type) -> showBloodType(element)
                                stringResource(R.string.info_height),
                                stringResource(R.string.info_weight) -> showUnit(element)

                                else -> element.value.value?.takeIf { it.isNotBlank() }
                                    ?: element.placeholder
                            },
                            fontSize = 12.sp,
                            color = Gray4,
                            lineHeight = 16.sp
                        )
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 7.dp, horizontal = 25.dp))
            }
    }
}
