package com.example.resq.network.model

import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfo
import com.google.gson.annotations.SerializedName

data class MedicalInfoResponse (
    @SerializedName("data") val medicalInfo: MedicalInfo,
    @SerializedName("success") val boolean: Boolean
)