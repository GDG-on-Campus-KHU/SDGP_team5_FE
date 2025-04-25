package com.example.resq.presentaion.userrecordlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.resq.R

@Composable
fun UserRecordList(){
    Text(text = stringResource(R.string.record_list))
}

@Preview(showBackground = true)
@Composable
fun UserRecordListPreview(){
    UserRecordList()
}