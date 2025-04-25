package com.example.resq.presentaion.usermedicalinfo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resq.R
import com.example.resq.presentaion.userrecordlist.UserRecordList

@Composable
fun UserMedicalInfo(){
    Text(text = stringResource(R.string.medical_info))
}

@Preview(showBackground = true)
@Composable
fun UserMedicalInfoPreview(){
    UserMedicalInfo()
}