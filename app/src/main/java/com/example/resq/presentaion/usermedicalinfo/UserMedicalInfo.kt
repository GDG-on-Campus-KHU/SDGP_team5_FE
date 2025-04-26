package com.example.resq.presentaion.usermedicalinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.R
import com.example.resq.ui.theme.Gray2
import com.example.resq.ui.theme.Gray4

@Composable
fun UserMedicalInfo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
    ) {
        val context = LocalContext.current
        val viewModel: UserMedicalInfoViewModel = viewModel()
        val medicalInfoList = remember { viewModel.getMedicalInfoList(context) }
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
                    .clickable(
                        onClick = {
                            //의료 정보 수정
                        }
                    )
            )
            Icon(
                imageVector = Icons.Outlined.Language,
                contentDescription = "Translate",
                modifier = Modifier
                    .padding(top = 5.dp, start = 12.dp)
                    .size(28.dp)
                    .clickable(
                        onClick = {
                            //번역
                        }
                    )
            )
        }
        Text(
            text = stringResource(R.string.info_message_placeholder1),
            color = Gray4,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 60.dp)
        )
        Divider(
            color = Gray2,
            modifier = Modifier
                .padding(vertical = 7.dp, horizontal = 18.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserMedicalInfoPreview() {
    UserMedicalInfo()
}