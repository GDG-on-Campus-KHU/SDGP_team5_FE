package com.example.resq.presentaion.roomdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.roomdetail.component.TranslationOptions
import com.example.resq.ui.theme.InnerPadding

@Composable
fun RoomDetailScreen(
    padding: PaddingValues,
    roomId: String,
    isExpanded: MutableState<Boolean>,
    viewModel: RoomDetailViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val membersMedicalInfo = viewModel.membersMedicalInfo.collectAsState()
    val translationOptions = viewModel.translationOptions.collectAsState()
    val isHeight = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(roomId) {
        viewModel.getRoomDetail(roomId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = InnerPadding),
        contentAlignment = Alignment.TopEnd
    ) {
        if (isLoading) {
            CenterCircularProgress()
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item { Spacer(Modifier.height(4.dp)) }
                items(membersMedicalInfo.value) { member ->
                    val userName = member.first
                    val userMedicalInfo = member.second
                    val isExtended = isHeight[member.second.userId] ?: true
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(Color.LightGray, RoundedCornerShape(16.dp))
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                            .clickable(
                                onClick = {
                                    isHeight.keys.forEach { isHeight[it] = true }
                                    isHeight[userMedicalInfo.userId] = !isExtended
                                },
                                interactionSource = null,
                                indication = null
                            )
                    ) {
                        if (isExtended)
                            Text(
                                text = userName,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        else
                            Column {
                                Text(text = userName)
                                Text(text = userMedicalInfo.userBloodType)
                                Text(text = userMedicalInfo.userAllergy)
                                Text(text = userMedicalInfo.userMedication)
                                Text(text = userMedicalInfo.userHeight.toString())
                                Text(text = userMedicalInfo.userWeight.toString())
                                Text(text = userMedicalInfo.userBirthdate)
                                Text(text = userMedicalInfo.userNotes)
                            }
                    }
                }
                item { Spacer(Modifier.height(4.dp)) }
            }
        }

        Box(modifier = Modifier.align(Alignment.TopEnd)) {
            TranslationOptions(
                isExpanded = isExpanded.value,
                options = translationOptions.value,
                onDismissRequest = { isExpanded.value = false },
                onClickOption = { viewModel.getRoomDetail(roomId) }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun RoomDetailPreview() {
    RoomDetailScreen(PaddingValues(0.dp), "test", mutableStateOf(true))
}