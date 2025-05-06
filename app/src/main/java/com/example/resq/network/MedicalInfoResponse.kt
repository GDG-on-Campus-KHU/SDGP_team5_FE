package com.example.resq.network

import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfo
import com.google.gson.annotations.SerializedName

data class MedicalInfoResponse(
    @SerializedName("data") val medicalInfo: MedicalInfo,
    @SerializedName("success") val boolean: Boolean
)