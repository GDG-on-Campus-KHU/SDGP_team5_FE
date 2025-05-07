package com.example.resq.presentaion.usermedicalinfo.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

data class MedicalInfoElement(
    val label: String,
    val placeholder: String,
    val icon: ImageVector,
    val value: MutableState<String?> = mutableStateOf(null),
    val show: MutableState<Boolean> = mutableStateOf(false),
    val unit: String? = null
)